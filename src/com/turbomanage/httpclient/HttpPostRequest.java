package com.turbomanage.httpclient;

public class HttpPostRequest extends HttpRequest {

    public HttpPostRequest(String path, ParameterMap params) {
        super();
        this.httpMethod = HttpMethod.POST;
        this.path = path;
        this.contentType = URLENCODED;
        if (params != null) {
            this.content = params.urlEncodedBytes();
        }
    }
    
    public HttpPostRequest(String path, String contentType, byte[] data) {
        super();
        this.httpMethod = HttpMethod.POST;
        this.path = path;
        this.contentType = contentType;
        this.content = data;
    }

}
