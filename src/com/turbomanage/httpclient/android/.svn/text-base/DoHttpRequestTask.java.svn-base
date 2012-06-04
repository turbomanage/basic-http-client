
package com.turbomanage.android.http;

import android.os.AsyncTask;

/**
 * AsyncTask that wraps an HttpRequest. Designed to be used in conjunction with
 * {@link AsyncHttpClient} so that requests can be made off the UI thread. 
 * 
 * @author David M. Chandler
 */
public class DoHttpRequestTask extends AsyncTask<HttpRequest, Void, HttpResponse> {

    private AsyncHttpClient client;
    private AsyncCallback callback;
    private Exception savedException;

    /**
     * Construct a new task with a callback to invoke when the request completes
     * or fails.
     * 
     * @param callback
     */
    public DoHttpRequestTask(AsyncHttpClient httpClient, AsyncCallback callback) {
        this.client = httpClient;
        this.callback = callback;
    }

    @Override
    protected HttpResponse doInBackground(HttpRequest... params) {
        try {
            if (params != null && params.length > 0) {
                HttpRequest httpRequest = params[0];
                return client.tryMany(httpRequest);
            }
            else {
                throw new IllegalArgumentException(
                        "DoHttpRequestTask takes exactly one argument of type HttpRequest");
            }
        } catch (Exception e) {
            this.savedException = e;
            cancel(true);
        }

        return null;
    }

    @Override
    protected void onPostExecute(HttpResponse result) {
        callback.onSuccess(result);
    }

    @Override
    protected void onCancelled() {
        callback.onError(this.savedException);
    }

}
