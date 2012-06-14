package com.turbomanage.httpclient;

/**
 * An HTTP PUT request.
 * 
 * @author David M. Chandler
 */
public class HttpPut extends HttpRequest {

    /**
     * Constructs an HTTP PUT request with arbitrary content.
     * 
     * @param path Partial URL
     * @param params Optional, appended to query string
     * @param contentType MIME type
     * @param data Content to be sent in the request body
     */
    public HttpPut(String path, ParameterMap params, String contentType, byte[] data) {
        super();
        this.httpMethod = HttpMethod.PUT;
        String queryString = null;
        if (params != null) {
            queryString = params.urlEncode();
        }
        this.path = path + "?" + queryString;
        this.contentType = contentType;
        this.content = data;
    }

}
