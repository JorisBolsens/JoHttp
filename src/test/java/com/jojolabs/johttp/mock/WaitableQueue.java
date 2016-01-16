package com.jojolabs.johttp.mock;

import com.jojolabs.johttp.NetworkResponse;
import com.jojolabs.johttp.Request;
import com.jojolabs.johttp.Response;

import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

// TODO: the name of this class sucks
@SuppressWarnings("serial")
public class WaitableQueue extends PriorityBlockingQueue<Request<?>> {
    private final Request<?> mStopRequest = new MagicStopRequest();
    private final Semaphore mStopEvent = new Semaphore(0);

    // TODO: this isn't really "until empty" it's "until next call to take() after empty"
    public void waitUntilEmpty(long timeoutMillis)
            throws TimeoutException, InterruptedException {
        add(mStopRequest);
        if (!mStopEvent.tryAcquire(timeoutMillis, TimeUnit.MILLISECONDS)) {
            throw new TimeoutException();
        }
    }

    @Override
    public Request<?> take() throws InterruptedException {
        Request<?> item = super.take();
        if (item == mStopRequest) {
            mStopEvent.release();
            return take();
        }
        return item;
    }

    private static class MagicStopRequest extends Request<Object> {
        public MagicStopRequest() {
            super(Request.Method.GET, "", null);
        }

        @Override
        public Priority getPriority() {
            return Priority.LOW;
        }

        @Override
        protected Response<Object> parseNetworkResponse(NetworkResponse response) {
            return null;
        }

        @Override
        protected void deliverResponse(Object response) {
        }
    }
}
