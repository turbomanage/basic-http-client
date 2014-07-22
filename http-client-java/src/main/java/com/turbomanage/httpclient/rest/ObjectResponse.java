package com.turbomanage.httpclient.rest;

import com.turbomanage.httpclient.HttpResponse;

public abstract class ObjectResponse<M extends MediaType> {

	protected HttpResponse httpResponse;
	protected ObjectFactory<M> objFactory;
	
	public ObjectResponse(HttpResponse res, ObjectFactory<M> factory) {
		this.httpResponse = res;
		this.objFactory = factory;
	}
	
	public <T> T toObj(Class<T> responseType) {
		return (T) objFactory.toObj(httpResponse.getBody(), responseType);
	}

	public HttpResponse getHttpResponse() {
		return httpResponse;
	}

}
