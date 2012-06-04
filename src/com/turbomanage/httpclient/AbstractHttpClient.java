
package com.turbomanage.httpclient;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.Map;
import java.util.TreeMap;

/**
 * Lightweight HTTP client that facilitates GET, POST, PUT, and DELETE requests
 * using {@link HttpURLConnection}. Extend this class to support specialized
 * content and response types (see {@link BasicHttpClient} for an example). To
 * enable streaming, buffering, or other types of readers / writers, set an
 * alternate {@link RequestHandler}.
 * 
 * @author David M. Chandler
 */
public abstract class AbstractHttpClient {

    static {
        ensureCookieManager();
    }

    public static final String URLENCODED = "application/x-www-form-urlencoded;charset=UTF-8";
    public static final String MULTIPART = "multipart/form-data";

    protected String baseUrl = "";

    protected RequestLogger requestLogger;
    protected RequestHandler requestHandler;
    private Map<String, String> requestHeaders = new TreeMap<String, String>();
    protected int connectionTimeout = 1000;
    protected int readTimeout = 5000;

    /**
     * Constructs a new client with base URL that will be appended in the
     * request methods. It may be empty or any part of a URL. Examples:
     * http://turbomanage.com http://turbomanage.com:987
     * http://turbomanage.com:987/resources
     * 
     * @param baseUrl
     */
    public AbstractHttpClient(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    /**
     * This method wraps the call to doHttpMethod and invokes the custom error
     * handler in case of HttpRequestException. It may be overridden by other
     * clients such {@link AsyncHttpClient} in order to wrap the exception
     * handling for purposes of retries, etc.
     * 
     * @param httpRequest
     * @return Response object
     */
    protected HttpResponse execute(HttpRequest httpRequest) {
        HttpResponse httpResponse = null;
        try {
            httpResponse = doHttpMethod(httpRequest.getPath(),
                    httpRequest.getHttpMethod(), httpRequest.getContentType(),
                    httpRequest.getContent());
        } catch (Exception e) {
            requestHandler.onError(httpResponse, e);
        }
        return httpResponse;
    }

    /**
     * This is the method that drives each request. It implements the request
     * lifecycle defined as open, prepare, write, read. Each of these methods in
     * turn delegates to the {@link RequestHandler} associated with this client.
     * 
     * @param path Whole or partial URL string, will be appended to baseUrl
     * @param httpMethod Request method
     * @param contentType MIME type of the request
     * @param content Request data
     * @return Response object
     * @throws HttpRequestException 
     */
    protected HttpResponse doHttpMethod(String path, HttpMethod httpMethod, String contentType,
            byte[] content) throws HttpRequestException {

        HttpURLConnection uc = null;
        HttpResponse httpResponse = null;

        try {
            uc = openConnection(path);
            prepareConnection(uc, httpMethod, contentType);
            appendRequestHeaders(uc);
            if (requestLogger.isLoggingEnabled()) {
                requestLogger.logRequest(uc, content);
            }
            if (uc.getDoOutput() && content != null) {
                int status = writeOutputStream(uc, content);
            }
            httpResponse = readInputStream(uc);
        } catch (Exception e) {
            // Try reading the error stream to populate status code such as 404
            try {
                httpResponse = readErrorStream(uc);
            } catch (Exception ee) {
                // Swallow to show first cause only
            } finally {
                throw new HttpRequestException(e, httpResponse);
            }
        } finally {
            if (requestLogger.isLoggingEnabled()) {
                requestLogger.logResponse(httpResponse);
            }
            if (uc != null) {
                uc.disconnect();
            }
        }
        return httpResponse;
    }

    protected HttpURLConnection openConnection(String path) throws IOException {
        String requestUrl = baseUrl + path;
        try {
            new URL(requestUrl);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(requestUrl + " is not a valid URL", e);
        }
        return requestHandler.openConnection(requestUrl);
    }

    protected void prepareConnection(HttpURLConnection urlConnection, HttpMethod httpMethod,
            String contentType) throws IOException {
        urlConnection.setConnectTimeout(connectionTimeout);
        urlConnection.setReadTimeout(readTimeout);
        requestHandler.prepareConnection(urlConnection, httpMethod, contentType);
    }

    /**
     * Append all headers added with {@link #addHeader(String, String)} to the
     * request.
     * 
     * @param urlConnection
     */
    private void appendRequestHeaders(HttpURLConnection urlConnection) {
        for (String name : requestHeaders.keySet()) {
            String value = requestHeaders.get(name);
            urlConnection.setRequestProperty(name, value);
        }
    }

    /**
     * @param uc
     * @param content
     * @return request status
     * @throws IOException
     */
    protected int writeOutputStream(HttpURLConnection uc, byte[] content) throws IOException {
        OutputStream out = null;
        try {
            out = uc.getOutputStream();
            if (out != null) {
                requestHandler.writeStream(out, content);
            }
            return uc.getResponseCode();
        } finally {
            // TODO nested try-catch not necessary since method throws
            // IOException
            // May want to wrap & swallow to avoid dup exceptions
            // Without a catch cause above, the close exception occurs before
            // orig bubbles up
            if (out != null) {
                try {
                    out.close();
                } catch (Exception e) {
                    // Swallow it to avoid dups
                }
            }
        }
    }

    protected HttpResponse readInputStream(HttpURLConnection uc) throws IOException {
        InputStream in = null;
        byte[] responseBody = null;
        try {
            in = uc.getInputStream();
            if (in != null) {
                responseBody = requestHandler.readStream(in);
            }
            return new HttpResponse(uc, responseBody);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (Exception e) {
                    // Swallow to avoid dups
                }
            }
        }
    }

    protected HttpResponse readErrorStream(HttpURLConnection uc) throws IOException {
        InputStream err = null;
        byte[] responseBody = null;
        try {
            err = uc.getErrorStream();
            if (err != null) {
                responseBody = requestHandler.readStream(err);
            }
            return new HttpResponse(uc, responseBody);
        } finally {
            if (err != null) {
                try {
                    err.close();
                } catch (Exception e) {
                    // Swallow to avoid dups
                }
            }
        }
    }

    /**
     * Convenience method creates a new ParameterMap to hold query params
     * 
     * @return Parameter map
     */
    public ParameterMap newParams() {
        return new ParameterMap();
    }

    /**
     * Adds to the headers that will be sent with each request from this client
     * instance. The request headers added with this method are applied by
     * calling {@link HttpURLConnection#setRequestProperty(String, String)}
     * after {@link #prepareConnection(HttpURLConnection, HttpMethod, String)},
     * so they may supplement or replace headers which have already been set.
     * Calls to {@link #addHeader(String, String)} may be chained. To clear all
     * headers added with this method, call {@link #clearHeaders()}.
     * 
     * @param name
     * @param value
     * @return
     */
    public AbstractHttpClient addHeader(String name, String value) {
        requestHeaders.put(name, value);
        return this;
    }

    /**
     * Clears all request headers that have been added using
     * {@link #addHeader(String, String)}. This method has no effect on headers
     * which result from request properties set by this class or its associated
     * {@link RequestHandler} when preparing the {@link HttpURLConnection}.
     */
    public void clearHeaders() {
        requestHeaders.clear();
    }

    /**
     * Returns the {@link CookieManager} associated with this client.
     * 
     * @return CookieManager
     */
    public static CookieManager getCookieManager() {
        return (CookieManager) CookieHandler.getDefault();
    }

    public void setRequestLogger(RequestLogger logger) {
        this.requestLogger = logger;
    }

    public void setRequestHandler(RequestHandler requestHandler) {
        this.requestHandler = requestHandler;
    }

    /**
     * Initialize the app-wide {@link CookieManager}. This is all that's
     * necessary to enable all Web requests within the app to automatically send
     * and receive cookies.
     */
    protected static void ensureCookieManager() {
        if (CookieHandler.getDefault() == null) {
            CookieHandler.setDefault(new CookieManager());
        }
    }
    
    protected boolean isTimeoutException(Throwable t) {
        if (t instanceof SocketTimeoutException || t instanceof ConnectException) {
            // This one happens right away, probably also EHOSTUNREACH
            // Should we measure elapsed time and return false if it's immediate?
            if (t.getMessage().contains("ECONNREFUSED")) {
                return false;
            }
            return true;
        } else if (t.getCause() == null) {
            return false;
        } else {
            return isTimeoutException(t.getCause());
        }
    }

    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public int getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }

}
