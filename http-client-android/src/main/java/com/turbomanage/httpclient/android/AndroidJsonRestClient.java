package com.turbomanage.httpclient.android;

import com.turbomanage.httpclient.AsyncHttpClient;
import com.turbomanage.httpclient.json.DefaultJsonMapper;
import com.turbomanage.httpclient.json.JsonRestClient;
import com.turbomanage.httpclient.json.JsonType;
import com.turbomanage.httpclient.rest.ObjectFactory;
import com.turbomanage.httpclient.rest.ResultHandler;

public class AndroidJsonRestClient extends JsonRestClient {

	public AndroidJsonRestClient(String baseUrl, ResultHandler handler) {
		this(new AndroidHttpClient(baseUrl), new DefaultJsonMapper(), handler);
	}
	
	public AndroidJsonRestClient(AsyncHttpClient httpClient, ObjectFactory<JsonType> factory, ResultHandler handler) {
		super(httpClient, factory, handler);
	}
	
}