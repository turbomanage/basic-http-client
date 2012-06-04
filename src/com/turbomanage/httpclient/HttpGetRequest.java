package com.turbomanage.httpclient;

public class HttpGetRequest extends HttpRequest {

    public HttpGetRequest(String path, ParameterMap params) {
        super();
        this.httpMethod = HttpMethod.GET;
        String queryString = null;
        if (params != null) {
            queryString = params.urlEncode();
        }
        this.path = path + "?" + queryString;
    }

}
