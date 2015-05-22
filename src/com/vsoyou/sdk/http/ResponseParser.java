package com.vsoyou.sdk.http;

import android.content.Context;

public interface ResponseParser<T> {

    public T getResponse(Context context, String response);

}
