package com.kupurui.cjhp.http;

import com.android.frame.http.HttpListener;
import com.android.frame.http.HttpUtils;
import com.android.frame.http.RequestParams;
import com.kupurui.cjhp.config.AppConfig;

/**
 * Created by Administrator on 2017/5/3.
 */

public class Order {
    private String module = this.getClass().getSimpleName();

    //账单列表
    public void index(String u_id, HttpListener listener,int what){
        RequestParams params = new RequestParams();
        params.addParam("u_id",u_id);
        new HttpUtils().doCall(AppConfig.BASE_URL+module+"/index",params,listener,what);
    }

    //本期费用
    public void detail(String detail,String groupid,String month ,HttpListener listener,int what){
        RequestParams params = new RequestParams();
        params.addParam("detail",detail);
        params.addParam("groupid",groupid);
        params.addParam("month",month);
        new HttpUtils().doCall(AppConfig.BASE_URL+module+"/detail",params,listener,what);
    }

    //往期欠费
    public void qianfei(String u_id, HttpListener listener,int what){
        RequestParams params = new RequestParams();
        params.addParam("u_id",u_id);
        new HttpUtils().doCall(AppConfig.BASE_URL+module+"/qianfei",params,listener,what);
    }

    //应付款
    public void fukuan(String detail,String groupid,String month,HttpListener listener,int what){
        RequestParams params = new RequestParams();
        params.addParam("detail",detail);
        params.addParam("groupid",groupid);
        params.addParam("month",month);
        new HttpUtils().doCall(AppConfig.BASE_URL+module+"/fukuan",params,listener,what);
    }
}
