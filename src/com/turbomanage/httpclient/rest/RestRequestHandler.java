package com.turbomanage.httpclient.rest;

import com.turbomanage.httpclient.HttpResponse;

/**
 * Handler passed to an {@link AsyncRestClient} so that the
 * calling code can be notified when a request is complete or
 * has thrown an exception. Make your own subclass to handle
 * things like login on authentication failure.
 * 
 * @author David M. Chandler
 */
public abstract class RestRequestHandler {

    /**
     * Called when response is available 
     * 
     * @param httpResponse may be null!
     */
    public abstract void onSuccess(HttpResponse httpResponse);
    
    /**
     * Called when a non-recoverable exception has occurred.
     * Timeout exceptions are considered recoverable and won't
     * trigger this call.
     * 
     * @param e
     */
    public void onError(Exception e) {
        e.printStackTrace();
    }

}
