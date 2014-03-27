package com.turbomanage.httpclient.android;

import com.turbomanage.httpclient.AsyncHttpClient;
import com.turbomanage.httpclient.json.DefaultJsonMapper;
import com.turbomanage.httpclient.json.JsonRestClient;
import com.turbomanage.httpclient.json.JsonType;
import com.turbomanage.httpclient.rest.ObjectFactory;

public class AndroidJsonRestClient extends JsonRestClient {

	public AndroidJsonRestClient() {
		this("");
	}

	public AndroidJsonRestClient(String baseUrl) {
		this(new AndroidHttpClient(baseUrl), new DefaultJsonMapper());
	}
	
	public AndroidJsonRestClient(AsyncHttpClient httpClient, ObjectFactory<JsonType> factory) {
		super(httpClient, factory);
	}
	
}