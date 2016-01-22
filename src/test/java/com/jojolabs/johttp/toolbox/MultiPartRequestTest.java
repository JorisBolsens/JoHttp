package com.jojolabs.johttp.toolbox;

import android.os.Environment;

import com.jojolabs.johttp.BuildConfig;
import com.jojolabs.johttp.ExecutorDelivery;
import com.jojolabs.johttp.HttpError;
import com.jojolabs.johttp.Network;
import com.jojolabs.johttp.NetworkResponse;
import com.jojolabs.johttp.Request;
import com.jojolabs.johttp.RequestQueue;
import com.jojolabs.johttp.Response;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLooper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import okio.Buffer;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by joris on 1/20/16.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class MultiPartRequestTest {
    private MockWebServer server;

    @Before
    public void setup() {
        server = new MockWebServer();
        try {
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        }
    }

    @After
    public void tearDown() {
        try {
            server.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void multipartStringBody() {
        MockResponse response = new MockResponse();
        response.addHeader("Content-Type", "application/json; encoding=\"utf-8\"");
        response.setResponseCode(201);
        response.setBody("{\"super\":\"cool\"}");

        server.enqueue(response);

        final CountDownLatch countDownLatch = new CountDownLatch(1);
        MockMultiPart request = new MockMultiPart(Request.Method.POST, server.url("/files").toString(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        countDownLatch.countDown();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(HttpError error) {
                error.printStackTrace();
                if (error.networkResponse != null)
                    System.out.print(new String(error.networkResponse.data));
                fail();
            }
        });

        final File file = new File(Environment.getExternalStorageDirectory(), "TEST.txt");
        if (file.exists())
            file.delete();
        try {
            FileOutputStream fout = new FileOutputStream(file);
            fout.write("cool multiparts".getBytes());
            fout.flush();
            fout.close();
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        }

        File cacheDir = new File(RuntimeEnvironment.application.getCacheDir(), "JoHttp");
        HttpStack stack = new HurlStack();
        Network network = new BasicNetwork(stack);
        RequestQueue joHttp = new RequestQueue(new DiskBasedCache(cacheDir), network, 4,
                new ExecutorDelivery(Executors.newSingleThreadExecutor()));

        request.addPart(new MultiPartRequest.Part("fileName", "file"));
        request.addPart(new MultiPartRequest.Part("fileSize", String.valueOf(file.length())));
        request.addPart(new MultiPartRequest.Part("file", file));
        joHttp.start();
        joHttp.add(request);

        try {
            RecordedRequest recordedRequest = server.takeRequest();
            StringBuilder body = new StringBuilder();
            Buffer buf = recordedRequest.getBody();
            InputStream in = buf.inputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            String line = bufferedReader.readLine();
            while (line != null) {
                body.append(line);
                body.append('\n');
                line = bufferedReader.readLine();
            }
            assertTrue(body.toString() != null && !body.toString().equalsIgnoreCase(""));
            countDownLatch.await(30, TimeUnit.SECONDS);
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
            fail();
        }
    }


    /**
     * temp, move to mock
     */
    private class MockMultiPart extends MultiPartRequest<String> {

        public MockMultiPart(int method, String url, Response.Listener<String> listener,
                             Response.ErrorListener errorListener) {
            super(method, url, listener, errorListener);
        }

        @Override
        protected Response<String> parseNetworkResponse(NetworkResponse response) {
            String parsed;
            try {
                parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            } catch (UnsupportedEncodingException e) {
                parsed = new String(response.data);
            }
            return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
        }
    }
}
