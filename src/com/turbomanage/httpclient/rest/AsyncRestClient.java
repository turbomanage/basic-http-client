package com.turbomanage.httpclient.rest;

import com.turbomanage.httpclient.AbstractHttpClient;
import com.turbomanage.httpclient.AsyncHttpClient;
import com.turbomanage.httpclient.HttpResponse;
import com.turbomanage.httpclient.ParameterMap;

public abstract class AsyncRestClient<M extends MediaType> {

	protected AsyncHttpClient httpClient;
	protected ObjectFactory<M> objFactory;
	
	public AsyncRestClient(AsyncHttpClient httpClient, ObjectFactory<M> factory) {
		this.httpClient = httpClient;
		this.objFactory = factory;
	}

//	public abstract R get(String path, ParameterMap params);
//
//	public abstract <T> R post(String path, T obj);
//
//	public abstract <T> R put(String path, T obj);
//
//	public abstract R delete(String path, ParameterMap params);
	
	public <T> byte[] toBytes(T obj) {
		return objFactory.toBytes(obj);
	}

	public ObjectResponse<M> get(String path, ParameterMap params) {
		HttpResponse httpResponse = httpClient.get(path, params);
		return objFactory.wrapResponse(httpResponse);
	}

	public <T> ObjectResponse<M> post(String path, T obj) {
		HttpResponse httpResponse = httpClient.post(path, AbstractHttpClient.JSON, toBytes(obj));
		return objFactory.wrapResponse(httpResponse);
	}

	public <T> ObjectResponse<M> put(String path, T obj) {
		HttpResponse httpResponse = httpClient.put(path, AbstractHttpClient.JSON, toBytes(obj));
		return objFactory.wrapResponse(httpResponse);
	}

	public ObjectResponse<M> delete(String path, ParameterMap params) {
		HttpResponse httpResponse = httpClient.delete(path, params);
		return objFactory.wrapResponse(httpResponse);
	}

}