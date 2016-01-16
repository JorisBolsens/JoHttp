package com.jojolabs.johttp.toolbox;

import com.jojolabs.johttp.Request;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.assertNotNull;

@RunWith(RobolectricTestRunner.class)
public class RequestFutureTest {

    @Test
    public void publicMethods() throws Exception {
        // Catch-all test to find API-breaking changes.
        assertNotNull(RequestFuture.class.getMethod("newFuture"));
        assertNotNull(RequestFuture.class.getMethod("setRequest", Request.class));
    }
}
