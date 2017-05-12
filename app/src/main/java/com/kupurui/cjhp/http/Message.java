package com.kupurui.cjhp.http;

import com.android.frame.http.HttpListener;
import com.android.frame.http.HttpUtils;
import com.android.frame.http.RequestParams;
import com.kupurui.cjhp.config.AppConfig;

/**
 * Created by Administrator on 2017/4/24.
 */

public class Message {

    private String module = this.getClass().getSimpleName();

    //公告通知
    public void index(String u_id, HttpListener listener, int what){
        RequestParams params = new RequestParams();
        params.addParam("u_id",u_id);
        new HttpUtils().doCall(AppConfig.BASE_URL+module+"/index",params,listener,what);
    }
    //公告
    public void gonggao(String u_id,HttpListener listener, int what){
        RequestParams params = new RequestParams();
        params.addParam("u_id",u_id);
        new HttpUtils().doCall(AppConfig.BASE_URL+module+"/gonggao",params,listener,what);
    }
    //通知
    public void tongzhi(String u_id,HttpListener listener, int what){
        RequestParams params = new RequestParams();
        params.addParam("u_id",u_id);
        new HttpUtils().doCall(AppConfig.BASE_URL+module+"/tongzhi",params,listener,what);
    }
    //公告通知详情
    public void detail(String id, HttpListener listener, int what){
        RequestParams params = new RequestParams();
        params.addParam("id",id);
        new HttpUtils().doCall(AppConfig.BASE_URL+module+"/detail",params,listener,what);
    }
}
