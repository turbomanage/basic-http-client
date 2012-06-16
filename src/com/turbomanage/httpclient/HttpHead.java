package com.turbomanage.httpclient;

/**
 * An HTTP HEAD request.
 * 
 * @author David M. Chandler
 */
public class HttpHead extends HttpRequest {

    /**
     * Constructs an HTTP HEAD request.
     * 
     * @param path Partial URL
     * @param params Name-value pairs to be appended to the URL
     */
    public HttpHead(String path, ParameterMap params) {
        super();
        this.httpMethod = HttpMethod.HEAD;
        String queryString = null;
        if (params != null) {
            queryString = params.urlEncode();
        }
        this.path = path + "?" + queryString;
    }

}
