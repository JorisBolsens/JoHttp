package com.jojolabs.johttp.toolbox;

import com.jojolabs.johttp.Cache;
import com.jojolabs.johttp.NetworkResponse;
import com.jojolabs.johttp.Response;
import com.jojolabs.johttp.VolleyError;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.Map;

import static org.junit.Assert.assertNotNull;

@RunWith(RobolectricTestRunner.class)
public class ResponseTest {

    @Test
    public void publicMethods() throws Exception {
        // Catch-all test to find API-breaking changes.
        assertNotNull(Response.class.getMethod("success", Object.class, Cache.Entry.class));
        assertNotNull(Response.class.getMethod("error", VolleyError.class));
        assertNotNull(Response.class.getMethod("isSuccess"));

        assertNotNull(Response.Listener.class.getDeclaredMethod("onResponse", Object.class));

        assertNotNull(Response.ErrorListener.class.getDeclaredMethod("onErrorResponse",
                VolleyError.class));

        assertNotNull(NetworkResponse.class.getConstructor(int.class, byte[].class, Map.class,
                boolean.class, long.class));
        assertNotNull(NetworkResponse.class.getConstructor(int.class, byte[].class, Map.class,
                boolean.class));
        assertNotNull(NetworkResponse.class.getConstructor(byte[].class));
        assertNotNull(NetworkResponse.class.getConstructor(byte[].class, Map.class));
    }
}
