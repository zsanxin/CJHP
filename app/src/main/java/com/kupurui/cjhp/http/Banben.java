package com.kupurui.cjhp.http;

import com.android.frame.http.HttpListener;
import com.android.frame.http.HttpUtils;
import com.android.frame.http.RequestParams;
import com.kupurui.cjhp.config.AppConfig;

/**
 * Created by Administrator on 2017/5/5.
 */

public class Banben {

    private String module = this.getClass().getSimpleName();

    public void index(int version, HttpListener listener,int what){
        RequestParams params = new RequestParams();
        params.addParam("version",String.valueOf(version));
        new HttpUtils().doCall(AppConfig.BASE_URL+module+"/index",params,listener,what);
    }
}
