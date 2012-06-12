package com.turbomanage.httpclient;

public class HttpRequestException extends Exception {

    private static final long serialVersionUID = -2413629666163901633L;
    
    private HttpResponse httpResponse;
    
    public HttpRequestException(Exception e, HttpResponse httpResponse) {
        super(e);
        this.httpResponse = httpResponse;
    }

    public HttpResponse getHttpResponse() {
        return httpResponse;
    }
}
