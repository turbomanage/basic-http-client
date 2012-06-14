package com.turbomanage.httpclient;

/**
 * An HTTP DELETE request.
 * 
 * @author David M. Chandler
 */
public class HttpDeleteRequest extends HttpRequest {

    /**
     * Constructs an HTTP DELETE request.
     * 
     * @param path Partial URL
     * @param params Name-value pairs to be appended to the URL
     */
    public HttpDeleteRequest(String path, ParameterMap params) {
        super();
        this.httpMethod = HttpMethod.DELETE;
        String queryString = null;
        if (params != null) {
            queryString = params.urlEncode();
        }
        this.path = path + "?" + queryString;
    }

}
