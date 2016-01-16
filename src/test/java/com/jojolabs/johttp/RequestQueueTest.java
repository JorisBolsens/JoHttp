package com.jojolabs.johttp;

import com.jojolabs.johttp.mock.ShadowSystemClock;
import com.jojolabs.johttp.toolbox.NoCache;
import com.jojolabs.johttp.utils.ImmediateResponseDelivery;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Unit tests for RequestQueue, with all dependencies mocked out
 */
@RunWith(RobolectricTestRunner.class)
@Config(shadows = {ShadowSystemClock.class})
public class RequestQueueTest {

    private ResponseDelivery mDelivery;
    @Mock private Network mMockNetwork;

    @Before public void setUp() throws Exception {
        mDelivery = new ImmediateResponseDelivery();
        initMocks(this);
    }

    @Test public void cancelAll_onlyCorrectTag() throws Exception {
        RequestQueue queue = new RequestQueue(new NoCache(), mMockNetwork, 0, mDelivery);
        Object tagA = new Object();
        Object tagB = new Object();
        Request req1 = mock(Request.class);
        when(req1.getTag()).thenReturn(tagA);
        Request req2 = mock(Request.class);
        when(req2.getTag()).thenReturn(tagB);
        Request req3 = mock(Request.class);
        when(req3.getTag()).thenReturn(tagA);
        Request req4 = mock(Request.class);
        when(req4.getTag()).thenReturn(tagA);

        queue.add(req1); // A
        queue.add(req2); // B
        queue.add(req3); // A
        queue.cancelAll(tagA);
        queue.add(req4); // A

        verify(req1).cancel(); // A cancelled
        verify(req3).cancel(); // A cancelled
        verify(req2, never()).cancel(); // B not cancelled
        verify(req4, never()).cancel(); // A added after cancel not cancelled
    }
}
