package com.turbomanage.httpclient.android;

import android.os.AsyncTask;
import android.os.Build;

import com.turbomanage.httpclient.AsyncHttpClient;
import com.turbomanage.httpclient.RequestHandler;
import com.turbomanage.httpclient.RequestLogger;

import java.net.HttpURLConnection;

/**
 * HTTP client that can safely be used by Android code on or off
 * the UI thread. Wraps each request in an {@link AsyncTask}.
 * 
 * @author David M. Chandler
 */
public class AndroidHttpClient extends AsyncHttpClient {

    static {
        disableConnectionReuseIfNecessary();
    }
    
    /**
     * Constructs a new client with empty baseUrl. When used this way, the path
     * passed to a request method must be the complete URL.
     */
    public AndroidHttpClient() {
        this("");
    }

    /**
     * Constructs a new client using the default {@link RequestHandler} and
     * {@link RequestLogger}.
     */
    public AndroidHttpClient(String baseUrl) {
        super(new AsyncTaskFactory(), baseUrl);
    }
    
    /**
     * Work around bug in {@link HttpURLConnection} on older versions of
     * Android.
     * http://android-developers.blogspot.com/2011/09/androids-http-clients.html
     */
    private static void disableConnectionReuseIfNecessary() {
        // HTTP connection reuse which was buggy pre-froyo
        if (Integer.parseInt(Build.VERSION.SDK) < Build.VERSION_CODES.FROYO) {
            System.setProperty("http.keepAlive", "false");
        }
    }

}
