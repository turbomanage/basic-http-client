package com.turbomanage.httpclient.android;

import android.os.AsyncTask;

import com.turbomanage.httpclient.AsyncHttpClient;
import com.turbomanage.httpclient.RequestHandler;
import com.turbomanage.httpclient.RequestLogger;

/**
 * HTTP client that can safely be used by Android code on or off
 * the UI thread. Wraps each request in an {@link AsyncTask}.
 * 
 * @author David M. Chandler
 */
public class AsyncTaskHttpClient extends AsyncHttpClient {

    /**
     * Constructs a new client with empty baseUrl. When used this way, the path
     * passed to a request method must be the complete URL.
     */
    public AsyncTaskHttpClient() {
        this("");
    }

    /**
     * Constructs a new client using the default {@link RequestHandler} and
     * {@link RequestLogger}.
     */
    public AsyncTaskHttpClient(String baseUrl) {
        super(new AsyncTaskFactory(), baseUrl);
    }
    
}
