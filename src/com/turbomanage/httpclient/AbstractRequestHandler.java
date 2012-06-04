
package com.turbomanage.httpclient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Default {@link RequestHandler} used by {@link BasicHttpClient}. It is
 * intended to be used for simple requests with small amounts of data only (a
 * few kB), as it does no buffering, chunking, streaming, etc. Only character
 * set supported is UTF-8. Only {@link String} content is supported. All
 * responses are treated as {@link String}s.
 * 
 * @author David M. Chandler
 */
public abstract class AbstractRequestHandler implements RequestHandler {

    /**
     * Opens the connection.
     * 
     * @see com.turbomanage.httpclient.RequestHandler#openConnection(java.lang.String)
     */
    @Override
    public HttpURLConnection openConnection(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection uc = (HttpURLConnection) url.openConnection();
        return uc;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.turbomanage.httpclient.RequestHandler#prepareConnection(java.net
     * .HttpURLConnection, com.turbomanage.httpclient.HttpMethod,
     * java.lang.String)
     */
    @Override
    public void prepareConnection(HttpURLConnection urlConnection, HttpMethod httpMethod,
            String contentType) throws IOException {
        // Configure connection for request method
        urlConnection.setRequestMethod(httpMethod.getMethodName());
        urlConnection.setDoOutput(httpMethod.getDoOutput());
        if (contentType != null) {
            urlConnection.setRequestProperty("Content-Type", contentType);
        }
        // Set additional properties
        urlConnection.setRequestProperty("Accept-Charset", UTF8);
    }

    /**
     * Writes a {@link String}. Override this method to handle other content
     * types.
     * 
     * @see com.turbomanage.httpclient.RequestHandler#writeStream(java.io.OutputStream,
     *      byte[])
     */
    @Override
    public void writeStream(OutputStream out, byte[] content) throws IOException {
        out.write(content);
    }

    /**
     * Reads the input into a {@link String}. Override this method to handle
     * other content types.
     * 
     * @see com.turbomanage.httpclient.RequestHandler#readStream(java.io.InputStream)
     */
    @Override
    public byte[] readStream(InputStream in) throws IOException {
        int nRead;
        byte[] data = new byte[16384];
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        while ((nRead = in.read(data)) != -1) {
            buffer.write(data, 0, nRead);
        }
        buffer.flush();
        return buffer.toByteArray();
    }

    /**
     * Just prints a stack trace.
     * 
     * @see com.turbomanage.httpclient.RequestHandler#onError(java.net.HttpURLConnection,
     *      java.lang.Exception)
     */
    @Override
    public void onError(HttpResponse res, Exception e) {
        System.out.println("AbstractRequestHandler#onError");
        e.printStackTrace();
    }

}
