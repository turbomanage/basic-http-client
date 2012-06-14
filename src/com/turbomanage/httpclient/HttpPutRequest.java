package com.turbomanage.httpclient;

/**
 * An HTTP PUT request.
 * 
 * @author David M. Chandler
 */
public class HttpPutRequest extends HttpRequest {

    /**
     * Constructs an HTTP PUT request with arbitrary content.
     * 
     * @param path Partial URL
     * @param contentType MIME type
     * @param data Content to be sent in the request body
     */
    public HttpPutRequest(String path, String contentType, byte[] data) {
        super();
        this.httpMethod = HttpMethod.PUT;
        this.path = path;
        this.contentType = contentType;
        this.content = data;
    }

}
