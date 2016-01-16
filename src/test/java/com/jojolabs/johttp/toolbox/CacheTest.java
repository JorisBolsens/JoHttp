package com.jojolabs.johttp.toolbox;

import com.jojolabs.johttp.Cache;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.assertNotNull;

@RunWith(RobolectricTestRunner.class)
public class CacheTest {

    @Test
    public void publicMethods() throws Exception {
        // Catch-all test to find API-breaking changes.
        assertNotNull(Cache.class.getMethod("get", String.class));
        assertNotNull(Cache.class.getMethod("put", String.class, Cache.Entry.class));
        assertNotNull(Cache.class.getMethod("initialize"));
        assertNotNull(Cache.class.getMethod("invalidate", String.class, boolean.class));
        assertNotNull(Cache.class.getMethod("remove", String.class));
        assertNotNull(Cache.class.getMethod("clear"));
    }
}
