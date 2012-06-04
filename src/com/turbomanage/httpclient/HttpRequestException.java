package com.turbomanage.httpclient;

public class HttpRequestException extends Exception {

    private HttpResponse httpResponse;
    
    public HttpRequestException(Exception e, HttpResponse httpResponse) {
        super(e);
        this.httpResponse = httpResponse;
    }

    public HttpResponse getHttpResponse() {
        return httpResponse;
    }
}
