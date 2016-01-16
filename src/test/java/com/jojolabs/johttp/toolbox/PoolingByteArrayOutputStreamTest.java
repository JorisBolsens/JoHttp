package com.jojolabs.johttp.toolbox;

import java.io.IOException;
import java.util.Arrays;

import org.junit.Test;

import static org.junit.Assert.*;

public class PoolingByteArrayOutputStreamTest {
    @Test public void pooledOneBuffer() throws IOException {
        ByteArrayPool pool = new ByteArrayPool(32768);
        writeOneBuffer(pool);
        writeOneBuffer(pool);
        writeOneBuffer(pool);
    }

    @Test public void pooledIndividualWrites() throws IOException {
        ByteArrayPool pool = new ByteArrayPool(32768);
        writeBytesIndividually(pool);
        writeBytesIndividually(pool);
        writeBytesIndividually(pool);
    }

    @Test public void unpooled() throws IOException {
        ByteArrayPool pool = new ByteArrayPool(0);
        writeOneBuffer(pool);
        writeOneBuffer(pool);
        writeOneBuffer(pool);
    }

    @Test public void unpooledIndividualWrites() throws IOException {
        ByteArrayPool pool = new ByteArrayPool(0);
        writeBytesIndividually(pool);
        writeBytesIndividually(pool);
        writeBytesIndividually(pool);
    }

    private void writeOneBuffer(ByteArrayPool pool) throws IOException {
        byte[] data = new byte[16384];
        for (int i = 0; i < data.length; i++) {
            data[i] = (byte) (i & 0xff);
        }
        PoolingByteArrayOutputStream os = new PoolingByteArrayOutputStream(pool);
        os.write(data);

        assertTrue(Arrays.equals(data, os.toByteArray()));
    }

    private void writeBytesIndividually(ByteArrayPool pool) {
        byte[] data = new byte[16384];
        for (int i = 0; i < data.length; i++) {
            data[i] = (byte) (i & 0xff);
        }
        PoolingByteArrayOutputStream os = new PoolingByteArrayOutputStream(pool);
        for (int i = 0; i < data.length; i++) {
            os.write(data[i]);
        }

        assertTrue(Arrays.equals(data, os.toByteArray()));
    }
}
