package com.jojolabs.johttp.toolbox;

import org.junit.Test;

import static org.junit.Assert.*;

public class ByteArrayPoolTest {
    @Test public void reusesBuffer() {
        ByteArrayPool pool = new ByteArrayPool(32);

        byte[] buf1 = pool.getBuf(16);
        byte[] buf2 = pool.getBuf(16);

        pool.returnBuf(buf1);
        pool.returnBuf(buf2);

        byte[] buf3 = pool.getBuf(16);
        byte[] buf4 = pool.getBuf(16);
        assertTrue(buf3 == buf1 || buf3 == buf2);
        assertTrue(buf4 == buf1 || buf4 == buf2);
        assertTrue(buf3 != buf4);
    }

    @Test public void obeysSizeLimit() {
        ByteArrayPool pool = new ByteArrayPool(32);

        byte[] buf1 = pool.getBuf(16);
        byte[] buf2 = pool.getBuf(16);
        byte[] buf3 = pool.getBuf(16);

        pool.returnBuf(buf1);
        pool.returnBuf(buf2);
        pool.returnBuf(buf3);

        byte[] buf4 = pool.getBuf(16);
        byte[] buf5 = pool.getBuf(16);
        byte[] buf6 = pool.getBuf(16);

        assertTrue(buf4 == buf2 || buf4 == buf3);
        assertTrue(buf5 == buf2 || buf5 == buf3);
        assertTrue(buf4 != buf5);
        assertTrue(buf6 != buf1 && buf6 != buf2 && buf6 != buf3);
    }

    @Test public void returnsBufferWithRightSize() {
        ByteArrayPool pool = new ByteArrayPool(32);

        byte[] buf1 = pool.getBuf(16);
        pool.returnBuf(buf1);

        byte[] buf2 = pool.getBuf(17);
        assertNotSame(buf2, buf1);

        byte[] buf3 = pool.getBuf(15);
        assertSame(buf3, buf1);
    }
}
