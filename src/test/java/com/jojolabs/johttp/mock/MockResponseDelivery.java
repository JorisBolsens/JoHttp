package com.jojolabs.johttp.mock;

import com.jojolabs.johttp.Request;
import com.jojolabs.johttp.Response;
import com.jojolabs.johttp.ResponseDelivery;
import com.jojolabs.johttp.VolleyError;

public class MockResponseDelivery implements ResponseDelivery {

    public boolean postResponse_called = false;
    public boolean postError_called = false;

    public boolean wasEitherResponseCalled() {
        return postResponse_called || postError_called;
    }

    public Response<?> responsePosted = null;
    @Override
    public void postResponse(Request<?> request, Response<?> response) {
        postResponse_called = true;
        responsePosted = response;
    }

    @Override
    public void postResponse(Request<?> request, Response<?> response, Runnable runnable) {
        postResponse_called = true;
        responsePosted = response;
        runnable.run();
    }

    @Override
    public void postError(Request<?> request, VolleyError error) {
        postError_called = true;
    }
}
