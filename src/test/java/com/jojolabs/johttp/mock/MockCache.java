package com.jojolabs.johttp.mock;

import com.jojolabs.johttp.Cache;

public class MockCache implements Cache {

    public boolean clearCalled = false;
    @Override
    public void clear() {
        clearCalled = true;
    }

    public boolean getCalled = false;
    private Entry mFakeEntry = null;

    public void setEntryToReturn(Entry entry) {
        mFakeEntry = entry;
    }

    @Override
    public Entry get(String key) {
        getCalled = true;
        return mFakeEntry;
    }

    public boolean putCalled = false;
    public String keyPut = null;
    public Entry entryPut = null;

    @Override
    public void put(String key, Entry entry) {
        putCalled = true;
        keyPut = key;
        entryPut = entry;
    }

    @Override
    public void invalidate(String key, boolean fullExpire) {
    }

    @Override
    public void remove(String key) {
    }

	@Override
	public void initialize() {
	}

}
