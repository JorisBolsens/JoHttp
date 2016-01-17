package com.jojolabs.johttp;

/**
 * Error indicating that no connection could be established when performing a JoHttp request.
 */
@SuppressWarnings("serial")
public class NoConnectionError extends NetworkError {
    public NoConnectionError() {
        super();
    }

    public NoConnectionError(Throwable reason) {
        super(reason);
    }
}
