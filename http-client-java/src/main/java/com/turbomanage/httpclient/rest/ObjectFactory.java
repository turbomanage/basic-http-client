package com.turbomanage.httpclient.rest;

import com.turbomanage.httpclient.HttpResponse;


public abstract class ObjectFactory<M extends MediaType> {

	public abstract <T> byte[] toBytes(T obj);
	
	public abstract <T> T toObj(byte[] bytes, Class<T> responseType);
	
	public abstract ObjectResponse<M> wrapResponse(HttpResponse res);

}
