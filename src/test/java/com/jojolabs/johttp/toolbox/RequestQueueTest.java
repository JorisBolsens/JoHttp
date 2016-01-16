package com.jojolabs.johttp.toolbox;

import com.jojolabs.johttp.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.assertNotNull;

@RunWith(RobolectricTestRunner.class)
public class RequestQueueTest {

    @Test
    public void publicMethods() throws Exception {
        // Catch-all test to find API-breaking changes.
        assertNotNull(RequestQueue.class.getConstructor(Cache.class, Network.class, int.class,
                ResponseDelivery.class));
        assertNotNull(RequestQueue.class.getConstructor(Cache.class, Network.class, int.class));
        assertNotNull(RequestQueue.class.getConstructor(Cache.class, Network.class));

        assertNotNull(RequestQueue.class.getMethod("start"));
        assertNotNull(RequestQueue.class.getMethod("stop"));
        assertNotNull(RequestQueue.class.getMethod("getSequenceNumber"));
        assertNotNull(RequestQueue.class.getMethod("getCache"));
        assertNotNull(RequestQueue.class.getMethod("cancelAll", RequestQueue.RequestFilter.class));
        assertNotNull(RequestQueue.class.getMethod("cancelAll", Object.class));
        assertNotNull(RequestQueue.class.getMethod("add", Request.class));
        assertNotNull(RequestQueue.class.getDeclaredMethod("finish", Request.class));
    }
}
