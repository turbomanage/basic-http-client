package com.turbomanage.httpclient;

public enum HttpMethod {
    GET(false),
    POST(true),
    PUT(true),
    DELETE(false);
    
    private boolean doOutput;
    
    HttpMethod(boolean doOutput) {
        this.doOutput = doOutput;
    }
    
    public boolean getDoOutput() {
        return this.doOutput;
    }
    
    public String getMethodName() {
        return this.toString();
    }
}
