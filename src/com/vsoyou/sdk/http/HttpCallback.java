package com.vsoyou.sdk.http;

public interface HttpCallback<T> {

    public void onSuccess(T object);

    public void onFailure(int errorCode, String errorMessage);

}
