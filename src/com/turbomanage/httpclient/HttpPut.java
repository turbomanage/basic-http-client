package com.turbomanage.httpclient;

/**
 * An HTTP PUT request.
 * 
 * @author David M. Chandler
 */
public class HttpPut extends HttpRequest {

    /**
     * Constructs an HTTP PUT request with name-value pairs to
     * be sent in URL encoded format.
     * 
     * @param path Partial URL
     * @param params Name-value pairs to be sent in the request body
     */
    public HttpPut(String path, ParameterMap params) {
        super();
        this.httpMethod = HttpMethod.PUT;
        this.path = path;
        this.contentType = URLENCODED;
        if (params != null) {
            this.content = params.urlEncodedBytes();
        }
    }

    /**
     * Constructs an HTTP PUT request with arbitrary content.
     * 
     * @param path Partial URL
     * @param contentType MIME type
     * @param data Content to be sent in the request body
     */
    public HttpPut(String path, String contentType, byte[] data) {
        super();
        this.httpMethod = HttpMethod.PUT;
        this.path = path;
        this.contentType = contentType;
        this.content = data;
    }

}
