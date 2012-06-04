package com.turbomanage.httpclient;

public class HttpPutRequest extends HttpRequest {

    public HttpPutRequest(String path, String contentType, byte[] data) {
        super();
        this.httpMethod = HttpMethod.PUT;
        this.path = path;
        this.contentType = contentType;
        this.content = data;
    }

}
