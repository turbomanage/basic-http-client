
package com.turbomanage.httpclient;


/**
 * Minimal HTTP client that facilitates simple GET, POST, PUT, and DELETE
 * requests. To implement buffering, streaming, or set other request properties, 
 * set an alternate {@link RequestHandler}.
 * 
 * <p>Sample usage:</p>
 * <pre>
 *    BasicHttpClient httpClient = new BasicHttpClient("http://www.google.com");
 *    ParameterMap params = httpClient.newParams().add("q", "GOOG");
 *    HttpResponse httpResponse = httpClient.get("/finance", params);
 *    System.out.println(httpResponse.getBodyAsString());
 * </pre>
 * 
 * @author David M. Chandler
 */
public class BasicHttpClient extends AbstractHttpClient {

    /**
     * Constructs the default client with empty baseUrl. 
     */
    public BasicHttpClient() {
        this("");
    }
    
    /**
     * Constructs the default client with baseUrl.
     * 
     * @param baseUrl
     */
    public BasicHttpClient(String baseUrl) {
        this(baseUrl, new BasicRequestHandler() {
        });
    }

    /**
     * Constructs a client with baseUrl and custom {@link RequestHandler}.
     * 
     * @param baseUrl
     * @param requestHandler
     */
    public BasicHttpClient(String baseUrl, RequestHandler requestHandler) {
        super(baseUrl, requestHandler);
    }

    /**
     * Execute a GET request and return the response.
     * 
     * The supplied parameters are URL encoded and sent as the query string.
     * 
     * @param path
     * @param params
     * @return Response object
     */
    public HttpResponse get(String path, ParameterMap params) {
        return execute(new HttpGetRequest(path, params));
    }

    /**
     * Execute a POST request with parameter map and return the response.
     * 
     * @param path
     * @param params
     * @return Response object
     */
    public HttpResponse post(String path, ParameterMap params) {
        return execute(new HttpPostRequest(path, params));
    }

    /**
     * Execute a POST request with a chunk of data.
     * 
     * The supplied parameters are URL encoded and sent as the request content.
     * 
     * @param path
     * @param contentType
     * @param data
     * @return Response object
     */
    public HttpResponse post(String path, String contentType, byte[] data) {
        return execute(new HttpPostRequest(path, contentType, data));
    }

    /**
     * Execute a PUT request with the supplied content and return the response.
     * 
     * @param path
     * @param contentType
     * @param data
     * @return Response object
     */
    public HttpResponse put(String path, String contentType, byte[] data) {
        return execute(new HttpPutRequest(path, contentType, data));
    }

    /**
     * Execute a DELETE request and return the response.
     * 
     * The supplied parameters are URL encoded and sent as the query string.
     * 
     * @param path
     * @param params
     * @return Response object
     */
    public HttpResponse delete(String path, ParameterMap params) {
        return execute(new HttpDeleteRequest(path, params));
    }

}
