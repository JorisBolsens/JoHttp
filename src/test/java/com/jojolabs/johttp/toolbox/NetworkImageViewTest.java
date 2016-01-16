package com.jojolabs.johttp.toolbox;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView.ScaleType;
import org.junit.Before;
import org.junit.Test;
import org.robolectric.RuntimeEnvironment;
import static org.junit.Assert.*;

public class NetworkImageViewTest {
    private NetworkImageView mNIV;
    private MockImageLoader mMockImageLoader;

    @Before
    public void setUp() throws Exception {
        mMockImageLoader = new MockImageLoader();
        mNIV = new NetworkImageView(RuntimeEnvironment.application);
    }

//    TODO uncomment once android is more mocked
//    @Test
//    public void setImageUrl_requestsImage() {
//        mNIV.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
//        mNIV.setImageUrl("http://foo", mMockImageLoader);
//        assertEquals("http://foo", mMockImageLoader.lastRequestUrl);
//        assertEquals(0, mMockImageLoader.lastMaxWidth);
//        assertEquals(0, mMockImageLoader.lastMaxHeight);
//    }


    private class MockImageLoader extends ImageLoader {
        public MockImageLoader() {
            super(null, null);
        }

        public String lastRequestUrl;
        public int lastMaxWidth;
        public int lastMaxHeight;

        public ImageContainer get(String requestUrl, ImageListener imageListener, int maxWidth,
                int maxHeight, ScaleType scaleType) {
            lastRequestUrl = requestUrl;
            lastMaxWidth = maxWidth;
            lastMaxHeight = maxHeight;
            return null;
        }
    }

    @Test
    public void publicMethods() throws Exception {
        // Catch-all test to find API-breaking changes.
        assertNotNull(NetworkImageView.class.getConstructor(Context.class));
        assertNotNull(NetworkImageView.class.getConstructor(Context.class, AttributeSet.class));
        assertNotNull(NetworkImageView.class.getConstructor(Context.class, AttributeSet.class,
                int.class));

        assertNotNull(NetworkImageView.class.getMethod("setImageUrl", String.class, ImageLoader.class));
        assertNotNull(NetworkImageView.class.getMethod("setDefaultImageResId", int.class));
        assertNotNull(NetworkImageView.class.getMethod("setErrorImageResId", int.class));
    }
}