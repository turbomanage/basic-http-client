
package com.turbomanage.httpclient.test;

import static org.junit.Assert.*;

import com.turbomanage.httpclient.HttpGet;
import com.turbomanage.httpclient.ParameterMap;

import org.junit.Test;

/**
 * Tests the HttpGet constructor with various permutations of path
 * and params.
 * 
 * @author David M. Chandler
 */
public class HttpGetTest {

    @Test
    public void testHttpGet() {
        ParameterMap params = new ParameterMap()
        .add("param1", "value1")
        .add("param2", "value2");
        HttpGet httpGet = new HttpGet("/nowhere", params);
        assertGetMethod(httpGet);
        assertEquals("/nowhere?param1=value1&param2=value2", httpGet.getPath());
    }

    @Test
    public void testHttpGetWithNullPath() {
        ParameterMap params = new ParameterMap()
        .add("param1", "value1")
        .add("param2", "value2");
        HttpGet httpGet = new HttpGet(null, params);
        assertGetMethod(httpGet);
        assertEquals("?param1=value1&param2=value2", httpGet.getPath());
    }

    @Test
    public void testHttpGetWithNullParams() {
        HttpGet httpGet = new HttpGet("/nowhere", null);
        assertGetMethod(httpGet);
        assertEquals("/nowhere", httpGet.getPath());
    }
    
    @Test
    public void testHttpGetWithNullPathAndParams() {
        HttpGet httpGet = new HttpGet(null, null);
        assertGetMethod(httpGet);
        assertEquals("", httpGet.getPath());
    }

    private void assertGetMethod(HttpGet httpGet) {
        assertEquals("GET", httpGet.getHttpMethod().getMethodName());
        assertNull(httpGet.getContent());
        assertNull(httpGet.getContentType());
    }

}
