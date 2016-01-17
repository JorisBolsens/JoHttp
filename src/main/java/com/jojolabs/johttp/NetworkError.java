package com.jojolabs.johttp;

/**
 * Indicates that there was a network error when performing a JoHttp request.
 */
@SuppressWarnings("serial")
public class NetworkError extends HttpError {
    public NetworkError() {
        super();
    }

    public NetworkError(Throwable cause) {
        super(cause);
    }

    public NetworkError(NetworkResponse networkResponse) {
        super(networkResponse);
    }
}
