package com.jojolabs.johttp.mock;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MockHttpURLConnection extends HttpURLConnection {

    private boolean mDoOutput;
    private String mRequestMethod;
    private OutputStream mOutputStream;

    public MockHttpURLConnection() throws MalformedURLException {
        super(new URL("http://foo.com"));
        mDoOutput = false;
        mRequestMethod = "GET";
        mOutputStream = new ByteArrayOutputStream();
    }

    @Override
    public void setDoOutput(boolean flag) {
        mDoOutput = flag;
    }

    @Override
    public boolean getDoOutput() {
        return mDoOutput;
    }

    @Override
    public void setRequestMethod(String method) {
        mRequestMethod = method;
    }

    @Override
    public String getRequestMethod() {
        return mRequestMethod;
    }

    @Override
    public OutputStream getOutputStream() {
        return mOutputStream;
    }

    @Override
    public void disconnect() {
    }

    @Override
    public boolean usingProxy() {
        return false;
    }

    @Override
    public void connect() throws IOException {
    }

}
