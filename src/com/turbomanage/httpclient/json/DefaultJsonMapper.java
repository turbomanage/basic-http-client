package com.turbomanage.httpclient.json;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.turbomanage.httpclient.HttpResponse;
import com.turbomanage.httpclient.rest.ObjectFactory;

public class DefaultJsonMapper extends ObjectFactory<JsonType> {

	private ObjectMapper om = new ObjectMapper();
	
	@Override
	public <T> byte[] toBytes(T obj) {
		try {
			return om.writeValueAsBytes(obj);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public <T> T toObj(byte[] bytes, Class<T> responseType) {
		try {
			T obj = om.readValue(bytes, responseType);
			return obj;
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public JsonResponse wrapResponse(HttpResponse res) {
		return new JsonResponse(res, this);
	}

}
