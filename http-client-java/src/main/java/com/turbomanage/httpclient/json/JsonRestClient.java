package com.turbomanage.httpclient.json;

import com.turbomanage.httpclient.AsyncHttpClient;
import com.turbomanage.httpclient.rest.AsyncRestClient;
import com.turbomanage.httpclient.rest.ObjectFactory;
import com.turbomanage.httpclient.rest.ResultHandler;

public class JsonRestClient extends AsyncRestClient<JsonType> {

	public JsonRestClient(AsyncHttpClient httpClient, ObjectFactory<JsonType> factory, ResultHandler handler) {
		super(httpClient, factory, handler);
	}

}