package com.jojolabs.johttp;

/**
 * Exception style class encapsulating JoHttp errors
 */
@SuppressWarnings("serial")
public class HttpError extends Exception {
    public final NetworkResponse networkResponse;
    private long networkTimeMs;

    public HttpError() {
        networkResponse = null;
    }

    public HttpError(NetworkResponse response) {
        networkResponse = response;
    }

    public HttpError(String exceptionMessage) {
       super(exceptionMessage);
       networkResponse = null;
    }

    public HttpError(String exceptionMessage, Throwable reason) {
        super(exceptionMessage, reason);
        networkResponse = null;
    }

    public HttpError(Throwable cause) {
        super(cause);
        networkResponse = null;
    }

    /* package */ void setNetworkTimeMs(long networkTimeMs) {
       this.networkTimeMs = networkTimeMs;
    }

    public long getNetworkTimeMs() {
       return networkTimeMs;
    }
}
