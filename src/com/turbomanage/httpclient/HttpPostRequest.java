package com.turbomanage.httpclient;

/**
 * An HTTP POST request.
 * 
 * @author David M. Chandler
 */
public class HttpPostRequest extends HttpRequest {

    /**
     * Constructs an HTTP POST request with name-value pairs to
     * be sent in URL encoded format.
     * 
     * @param path Partial URL
     * @param params Name-value pairs to be sent in the request body
     */
    public HttpPostRequest(String path, ParameterMap params) {
        super();
        this.httpMethod = HttpMethod.POST;
        this.path = path;
        this.contentType = URLENCODED;
        if (params != null) {
            this.content = params.urlEncodedBytes();
        }
    }
    
    /**
     * Constructs an HTTP POST request with arbitrary content.
     * 
     * @param path Partial URL
     * @param contentType MIME type
     * @param data Content to be sent in the request body
     */
    public HttpPostRequest(String path, String contentType, byte[] data) {
        super();
        this.httpMethod = HttpMethod.POST;
        this.path = path;
        this.contentType = contentType;
        this.content = data;
    }

}
