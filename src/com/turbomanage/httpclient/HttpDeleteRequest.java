package com.turbomanage.httpclient;

public class HttpDeleteRequest extends HttpRequest {

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
