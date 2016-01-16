package com.jojolabs.johttp.mock;

import com.jojolabs.johttp.Network;
import com.jojolabs.johttp.NetworkResponse;
import com.jojolabs.johttp.Request;
import com.jojolabs.johttp.ServerError;
import com.jojolabs.johttp.VolleyError;

public class MockNetwork implements Network {
    public final static int ALWAYS_THROW_EXCEPTIONS = -1;

    private int mNumExceptionsToThrow = 0;
    private byte[] mDataToReturn = null;

    /**
     * @param numExceptionsToThrow number of times to throw an exception or
     * {@link #ALWAYS_THROW_EXCEPTIONS}
     */
    public void setNumExceptionsToThrow(int numExceptionsToThrow) {
        mNumExceptionsToThrow = numExceptionsToThrow;
    }

    public void setDataToReturn(byte[] data) {
        mDataToReturn = data;
    }

    public Request<?> requestHandled = null;

    @Override
    public NetworkResponse performRequest(Request<?> request) throws VolleyError {
        if (mNumExceptionsToThrow > 0 || mNumExceptionsToThrow == ALWAYS_THROW_EXCEPTIONS) {
            if (mNumExceptionsToThrow != ALWAYS_THROW_EXCEPTIONS) {
                mNumExceptionsToThrow--;
            }
            throw new ServerError();
        }

        requestHandled = request;
        return new NetworkResponse(mDataToReturn);
    }

}
