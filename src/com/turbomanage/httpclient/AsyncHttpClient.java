
package com.turbomanage.httpclient;

/**
 * An HTTP client that completes all requests asynchronously using
 * {@link DoHttpRequestTask}. All methods take a callback argument which is
 * passed to the task and whose methods are invoked when the request completes
 * or throws on exception.
 * 
 * @author David M. Chandler
 */
public class AsyncHttpClient extends AbstractHttpClient {

    private int maxRetries = 3;
    /*
     * The factory that will be used to obtain an async wrapper for the
     * request. Usually set in a subclass that provides a platform-specific
     * async wrapper such as a new thread or Android AsyncTask. 
     */
    protected final AsyncRequestExecutorFactory execFactory;

    /**
     * Constructs a new client with empty baseUrl. When used this way, the path
     * passed to a request method must be the complete URL.
     */
    public AsyncHttpClient(AsyncRequestExecutorFactory factory) {
        this(factory, "");
    }

    /**
     * Constructs a new client using the default {@link RequestHandler} and
     * {@link RequestLogger}.
     */
    public AsyncHttpClient(AsyncRequestExecutorFactory factory, String baseUrl) {
        super(baseUrl);
        this.execFactory = factory;
        setRequestHandler(new AbstractRequestHandler() {
        });
        setRequestLogger(new ConsoleRequestLogger());
    }

    /**
     * Execute a GET request and invoke the callback on completion. The supplied
     * parameters are URL encoded and sent as the query string.
     * 
     * @param path
     * @param params
     * @param callback
     */
    public void get(String path, ParameterMap params, AsyncCallback callback) {
        HttpRequest req = new HttpGetRequest(path, params);
        executeAsync(req, callback);
    }

    /**
     * Execute a POST request with parameter map and invoke the callback on
     * completion.
     * 
     * @param path
     * @param params
     * @param callback
     */
    public void post(String path, ParameterMap params, AsyncCallback callback) {
        HttpRequest req = new HttpPostRequest(path, params);
        executeAsync(req, callback);
    }

    /**
     * Execute a POST request with a chunk of data and invoke the callback on
     * completion.
     * 
     * @param path
     * @param contentType
     * @param data
     * @param callback
     */
    public void post(String path, String contentType, byte[] data, AsyncCallback callback) {
        HttpPostRequest req = new HttpPostRequest(path, contentType, data);
        executeAsync(req, callback);
    }

    /**
     * Execute a PUT request with the supplied content and invoke the callback
     * on completion.
     * 
     * @param path
     * @param contentType
     * @param data
     * @param callback
     */
    public void put(String path, String contentType, byte[] data, AsyncCallback callback) {
        HttpPutRequest req = new HttpPutRequest(path, contentType, data);
        executeAsync(req, callback);
    }

    /**
     * Execute a DELETE request and invoke the callback on completion. The
     * supplied parameters are URL encoded and sent as the query string.
     * 
     * @param path
     * @param params
     * @param callback
     */
    public void delete(String path, ParameterMap params, AsyncCallback callback) {
        HttpDeleteRequest req = new HttpDeleteRequest(path, params);
        executeAsync(req, callback);
    }

    /**
     * Execute an {@link HttpRequest} asynchronously. Uses a factory to obtain a
     * suitable async wrapper in order to decouple from Android.
     * 
     * @param httpRequest
     * @param callback
     */
    protected void executeAsync(HttpRequest httpRequest, AsyncCallback callback) {
        AsyncRequestExecutor executor = execFactory.getAsyncRequestExecutor(this, callback);
        executor.execute(httpRequest);
    }

    /**
     * Tries several times until successful or maxRetries exhausted.
     * Must throw exception in order for the async process to forward
     * it to the callback's onError method.
     * 
     * @param httpRequest
     * @return
     * @throws HttpRequestException
     */
    public HttpResponse tryMany(HttpRequest httpRequest) throws HttpRequestException {
        int n = getMaxRetries();
        HttpResponse res = null;
        while (n > 0) {
            try {
                System.out.println("n=" + n + " Trying " + httpRequest.getPath());
                res = doHttpMethod(httpRequest.getPath(),
                        httpRequest.getHttpMethod(), httpRequest.getContentType(),
                        httpRequest.getContent());
                n = 0;
                // TODO This should be the only kind we get--can we ensure?
            } catch (HttpRequestException e) {
                if (isTimeoutException(e)) {
                    // try again with exponential backoff
                    setConnectionTimeout(connectionTimeout * 2);
                } else {
                    n = 0;
                    requestHandler.onError(res, e);
                    // rethrow to caller
                    throw e;
                }
            } finally {
                n--;
            }
        }
        return res;
    }

    public int getMaxRetries() {
        return maxRetries;
    }

    public void setMaxRetries(int maxRetries) {
        this.maxRetries = maxRetries;
    }

}