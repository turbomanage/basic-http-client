package com.turbomanage.httpclient.rest;

import com.turbomanage.httpclient.AbstractHttpClient;
import com.turbomanage.httpclient.AsyncHttpClient;
import com.turbomanage.httpclient.HttpResponse;
import com.turbomanage.httpclient.ParameterMap;

public abstract class AsyncRestClient<M extends MediaType> {

	protected AsyncHttpClient httpClient;
	protected ObjectFactory<M> objFactory;
	protected ResultHandler resultHandler;
	
	public AsyncRestClient(AsyncHttpClient httpClient, ObjectFactory<M> factory, ResultHandler handler) {
		this.httpClient = httpClient;
		this.objFactory = factory;
		this.resultHandler = handler;
	}

	public ObjectResponse<M> get(String path, ParameterMap params) {
		HttpResponse httpResponse = httpClient.get(path, params);
		if (resultHandler.onResult(httpResponse)) {
			return objFactory.wrapResponse(httpResponse);
		}
		return null;
	}

	public <T> T get(String path, ParameterMap params, Class<T> responseType) {
		HttpResponse httpResponse = httpClient.get(path, params);
		if (resultHandler.onResult(httpResponse)) {
	    	if (httpResponse != null) {
	    		ObjectResponse<M> objResponse = objFactory.wrapResponse(httpResponse);
	    		T result = objResponse.toObj(responseType);
	    		return result;
	    	}
		}
		return null;
	}

	public <T> ObjectResponse<M> post(String path, T obj) {
		HttpResponse httpResponse = httpClient.post(path, AbstractHttpClient.JSON, toBytes(obj));
		if (resultHandler.onResult(httpResponse)) {
			return objFactory.wrapResponse(httpResponse);
		}
		return null;
	}

	public <T> ObjectResponse<M> put(String path, T obj) {
		HttpResponse httpResponse = httpClient.put(path, AbstractHttpClient.JSON, toBytes(obj));
		if (resultHandler.onResult(httpResponse)) {
			return objFactory.wrapResponse(httpResponse);
		}
		return null;
	}

	public ObjectResponse<M> delete(String path, ParameterMap params) {
		HttpResponse httpResponse = httpClient.delete(path, params);
		if (resultHandler.onResult(httpResponse)) {
			return objFactory.wrapResponse(httpResponse);
		}
		return null;
	}

	public <T> byte[] toBytes(T obj) {
		return objFactory.toBytes(obj);
	}

}