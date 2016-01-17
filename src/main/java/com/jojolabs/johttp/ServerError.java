package com.jojolabs.johttp;

/**
 * Indicates that the server responded with an error response.
 */
@SuppressWarnings("serial")
public class ServerError extends HttpError {
    public ServerError(NetworkResponse networkResponse) {
        super(networkResponse);
    }

    public ServerError() {
        super();
    }
}

