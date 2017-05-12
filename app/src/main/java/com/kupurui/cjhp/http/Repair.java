package com.kupurui.cjhp.http;

import com.android.frame.http.HttpListener;
import com.android.frame.http.HttpUtils;
import com.android.frame.http.RequestParams;
import com.kupurui.cjhp.config.AppConfig;

import java.io.File;
import java.util.List;

/**
 * Created by Administrator on 2017/4/25.
 */

public class Repair {

    private String module = this.getClass().getSimpleName();

    //报事报修
    public void index(String u_id, String title, String content, String tel, List<File> image, HttpListener listener , int what){
        RequestParams params = new RequestParams();
        params.addParam("u_id",u_id);
        params.addParam("title",title);
        params.addParam("content",content);
        params.addParam("tel",tel);
        for (int i = 0; i < image.size(); i++) {
            params.addParam("image[]",image.get(i));
        }
        new HttpUtils().doCall(AppConfig.BASE_URL+module+"/index",params,listener,what);
    }
    //待处理
    public void waitcl(String u_id,String page,HttpListener listener ,int what){
        RequestParams params = new RequestParams();
        params.addParam("u_id",u_id);
        params.addParam("page",page);
        new HttpUtils().doCall(AppConfig.BASE_URL+module+"/waitcl",params,listener,what);
    }
    //处理中
    public void clz(String u_id,String page,HttpListener listener ,int what){
        RequestParams params = new RequestParams();
        params.addParam("u_id",u_id);
        params.addParam("page",page);
        new HttpUtils().doCall(AppConfig.BASE_URL+module+"/clz",params,listener,what);
    }
    //已处理
    public void ycl(String u_id,String page,HttpListener listener ,int what){
        RequestParams params = new RequestParams();
        params.addParam("u_id",u_id);
        params.addParam("page",page);
        new HttpUtils().doCall(AppConfig.BASE_URL+module+"/ycl",params,listener,what);
    }
    //已处理详情
    public void detail(String r_id,HttpListener listener ,int what){
        RequestParams params = new RequestParams();
        params.addParam("r_id",r_id);
        new HttpUtils().doCall(AppConfig.BASE_URL+module+"/detail",params,listener,what);
    }
    //评论
    public void comment(String r_id,String star,String comment,HttpListener listener,int what){
        RequestParams params = new RequestParams();
        params.addParam("r_id",r_id);
        params.addParam("star",star);
        params.addParam("comment",comment);
        new HttpUtils().doCall(AppConfig.BASE_URL+module+"/comment",params,listener,what);
    }
}
