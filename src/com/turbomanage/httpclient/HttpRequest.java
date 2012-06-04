package com.turbomanage.httpclient;

/**
 * Holds data for an HTTP request to be made with the attached HTTP client.
 * 
 * @author David M. Chandler
 */
public abstract class HttpRequest {
    
    public static final String URLENCODED = "application/x-www-form-urlencoded;charset=UTF-8";
    public static final String MULTIPART = "multipart/form-data";
    
    protected String path;
    protected HttpMethod httpMethod;
    protected String contentType;
    protected byte[] content;
    
    public String getPath() {
        return path;
    }
    
    public HttpMethod getHttpMethod() {
        return httpMethod;
    }
    
    public String getContentType() {
        return contentType;
    }
    
    public byte[] getContent() {
        return content;
    }

}
