package com.jojolabs.johttp.mock;

import com.jojolabs.johttp.AuthFailureError;
import com.jojolabs.johttp.Request;
import com.jojolabs.johttp.toolbox.HttpStack;

import org.apache.http.HttpResponse;

import java.util.HashMap;
import java.util.Map;

public class MockHttpStack implements HttpStack {

    private HttpResponse mResponseToReturn;

    private String mLastUrl;

    private Map<String, String> mLastHeaders;

    private byte[] mLastPostBody;

    public String getLastUrl() {
        return mLastUrl;
    }

    public Map<String, String> getLastHeaders() {
        return mLastHeaders;
    }

    public byte[] getLastPostBody() {
        return mLastPostBody;
    }

    public void setResponseToReturn(HttpResponse response) {
        mResponseToReturn = response;
    }

    @Override
    public HttpResponse performRequest(Request<?> request, Map<String, String> additionalHeaders)
            throws AuthFailureError {
        mLastUrl = request.getUrl();
        mLastHeaders = new HashMap<String, String>();
        if (request.getHeaders() != null) {
            mLastHeaders.putAll(request.getHeaders());
        }
        if (additionalHeaders != null) {
            mLastHeaders.putAll(additionalHeaders);
        }
        try {
            mLastPostBody = request.getBody();
        } catch (AuthFailureError e) {
            mLastPostBody = null;
        }
        return mResponseToReturn;
    }
}
