package com.turbomanage.httpclient;

public abstract class AsyncCallback {

    public abstract void onSuccess(HttpResponse httpResponse);
    
    public void onError(Exception e) {
        e.printStackTrace();
    }
}
