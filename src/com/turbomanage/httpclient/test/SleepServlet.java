package com.turbomanage.httpclient.test;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Test servlet simply waits for a number of seconds in order to 
 * test read timeouts.
 * 
 * @author David M. Chandler
 */
public class SleepServlet extends HttpServlet {

    private static final long serialVersionUID = 3505149106330098310L;

    public SleepServlet() {
        // Nothing to see here
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
            IOException {
        try {
            Thread.sleep(9000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            Thread.sleep(9000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    

}
