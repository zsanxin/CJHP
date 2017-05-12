package com.android.frame.http;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by admin on 2016/5/9.
 * 网络请求监听
 */
public interface HttpListener {

    public void onSuccess(String result, Call call, Response response, int what);

    public void onError(String result, Call call, Response response, int what);

    public void onParseFail();

    public void onFailure(Call call, int what);
}
