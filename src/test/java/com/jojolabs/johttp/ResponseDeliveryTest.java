package com.jojolabs.johttp;

import com.jojolabs.johttp.mock.MockRequest;
import com.jojolabs.johttp.utils.CacheTestUtils;
import com.jojolabs.johttp.utils.ImmediateResponseDelivery;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
public class ResponseDeliveryTest {

    private ExecutorDelivery mDelivery;
    private MockRequest mRequest;
    private Response<byte[]> mSuccessResponse;

    @Before public void setUp() throws Exception {
        // Make the delivery just run its posted responses immediately.
        mDelivery = new ImmediateResponseDelivery();
        mRequest = new MockRequest();
        mRequest.setSequence(1);
        byte[] data = new byte[16];
        Cache.Entry cacheEntry = CacheTestUtils.makeRandomCacheEntry(data);
        mSuccessResponse = Response.success(data, cacheEntry);
    }

    @Test public void postResponseCallsDeliverResponse() {
        mDelivery.postResponse(mRequest, mSuccessResponse);
        assertTrue(mRequest.deliverResponse_called);
        assertFalse(mRequest.deliverError_called);
    }

    @Test public void postResponseSuppressesCanceled() {
        mRequest.cancel();
        mDelivery.postResponse(mRequest, mSuccessResponse);
        assertFalse(mRequest.deliverResponse_called);
        assertFalse(mRequest.deliverError_called);
    }

    @Test public void postErrorCallsDeliverError() {
        Response<byte[]> errorResponse = Response.error(new ServerError());

        mDelivery.postResponse(mRequest, errorResponse);
        assertTrue(mRequest.deliverError_called);
        assertFalse(mRequest.deliverResponse_called);
    }
}
