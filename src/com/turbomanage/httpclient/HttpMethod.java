package com.turbomanage.httpclient;

/**
 * Enumerated type that represents an HTTP request method.
 * Besides the method name, it determines whether the client
 * should do the output phase of the connection.
 *  
 * @author David M. Chandler
 */
public enum HttpMethod {
    GET(false),
    POST(true),
    PUT(true),
    DELETE(false);
    
    private boolean doOutput;
    
    private HttpMethod(boolean doOutput) {
        this.doOutput = doOutput;
    }

    /**
     * Whether the client should do the write phase, or just read
     * 
     * @return doOutput
     */
    public boolean getDoOutput() {
        return this.doOutput;
    }
    
    /**
     * Accessor method.
     * 
     * @return HTTP method name (GET, PUT, POST, DELETE)
     */
    public String getMethodName() {
        return this.toString();
    }
}
