package com.jojolabs.johttp.mock;

import com.jojolabs.johttp.NetworkResponse;
import com.jojolabs.johttp.Request;
import com.jojolabs.johttp.Response;
import com.jojolabs.johttp.Response.ErrorListener;
import com.jojolabs.johttp.HttpError;
import com.jojolabs.johttp.utils.CacheTestUtils;

import java.util.HashMap;
import java.util.Map;

public class MockRequest extends Request<byte[]> {
    public MockRequest() {
        super(Request.Method.GET, "http://foo.com", null);
    }

    public MockRequest(String url, ErrorListener listener) {
        super(Request.Method.GET, url, listener);
    }

    private Map<String, String> mPostParams = new HashMap<String, String>();

    public void setPostParams(Map<String, String> postParams) {
        mPostParams = postParams;
    }

    private String mCacheKey = super.getCacheKey();

    public void setCacheKey(String cacheKey) {
        mCacheKey = cacheKey;
    }

    @Override
    public String getCacheKey() {
        return mCacheKey;
    }

    public boolean deliverResponse_called = false;
    public boolean parseResponse_called = false;

    @Override
    protected void deliverResponse(byte[] response) {
        deliverResponse_called = true;
    }

    public boolean deliverError_called = false;

    @Override
    public void deliverError(HttpError error) {
        super.deliverError(error);
        deliverError_called = true;
    }

    public boolean cancel_called = false;

    @Override
    public void cancel() {
        cancel_called = true;
        super.cancel();
    }

    private Priority mPriority = super.getPriority();

    public void setPriority(Priority priority) {
        mPriority = priority;
    }

    @Override
    public Priority getPriority() {
        return mPriority;
    }

    @Override
    protected Response<byte[]> parseNetworkResponse(NetworkResponse response) {
        parseResponse_called = true;
        return Response.success(response.data, CacheTestUtils.makeRandomCacheEntry(response.data));
    }

}
