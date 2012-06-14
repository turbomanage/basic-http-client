package com.turbomanage.httpclient;

/**
 * An HTTP GET request.
 * 
 * @author David M. Chandler
 */
public class HttpGet extends HttpRequest {

    /**
     * Constructs an HTTP GET request.
     * 
     * @param path Partial URL
     * @param params Name-value pairs to be appended to the URL
     */
    public HttpGet(String path, ParameterMap params) {
        super();
        this.httpMethod = HttpMethod.GET;
        String queryString = null;
        if (params != null) {
            queryString = params.urlEncode();
        }
        this.path = path + "?" + queryString;
    }

}
