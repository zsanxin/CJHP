package com.kupurui.cjhp.http;

import com.android.frame.http.HttpListener;
import com.android.frame.http.HttpUtils;
import com.android.frame.http.RequestParams;
import com.kupurui.cjhp.config.AppConfig;

/**
 * Created by Administrator on 2017/4/21.
 */

public class User {

    private String module = this.getClass().getSimpleName();

    //账号登陆
    public void login(String username, String password, HttpListener listener, int what){
        RequestParams params = new RequestParams();
        params.addParam("username",username);
        params.addParam("password",password);
        new HttpUtils().doCall(AppConfig.BASE_URL+module+"/login",params,listener,what);
    }
    //发送验证码
    public void sendEmail(String email,HttpListener listener ,int what){
        RequestParams params = new RequestParams();
        params.addParam("email",email);
        new HttpUtils().doCall(AppConfig.BASE_URL+module+"/sendEmail",params,listener,what);
    }
    //验证验证码
    public void confirm(String email,String code,HttpListener listener ,int what){
        RequestParams params = new RequestParams();
        params.addParam("email",email);
        params.addParam("code",code);
        new HttpUtils().doCall(AppConfig.BASE_URL+module+"/confirm",params,listener,what);
    }
    //绑定邮箱
    public void bangding(String u_id,String email,String code,HttpListener listener ,int what){
        RequestParams params = new RequestParams();
        params.addParam("u_id",u_id);
        params.addParam("email",email);
        params.addParam("code",code);
        new HttpUtils().doCall(AppConfig.BASE_URL+module+"/bangding",params,listener,what);
    }
    //手势登陆
    public void handLogin(String username,String hand,HttpListener listener ,int what){
        RequestParams params = new RequestParams();
        params.addParam("username",username);
        params.addParam("hand",hand);
        new HttpUtils().doCall(AppConfig.BASE_URL+module+"/handLogin",params,listener,what);
    }
    //忘记手势密码
    public void forgetHand(String username,String password,HttpListener listener ,int what){
        RequestParams params = new RequestParams();
        params.addParam("username",username);
        params.addParam("password",password);
        new HttpUtils().doCall(AppConfig.BASE_URL+module+"/forgetHand",params,listener,what);
    }
    //修改密码
    public void modifyPassword(String u_id,String old_password,String new_password,String new_password_2,HttpListener listener,int what){
        RequestParams params = new RequestParams();
        params.addParam("u_id",u_id);
        params.addParam("old_password",old_password);
        params.addParam("new_password",new_password);
        params.addParam("new_password_2",new_password_2);
        new HttpUtils().doCall(AppConfig.BASE_URL+module+"/modifyPassword",params,listener,what);
    }
    //密码重置
    public void setPassword(String u_id,String password,String password_2,HttpListener listener,int what){
        RequestParams params = new RequestParams();
        params.addParam("u_id",u_id);
        params.addParam("password",password);
        params.addParam("password_2",password_2);
        new HttpUtils().doCall(AppConfig.BASE_URL+module+"/setPassword",params,listener,what);
    }
    //密保邮箱
    public void myEmail(String u_id,HttpListener listener,int what){
        RequestParams params = new RequestParams();
        params.addParam("u_id",u_id);
        new HttpUtils().doCall(AppConfig.BASE_URL+module+"/myEmail",params,listener,what);
    }
    //设置手势密码
    public void setHand(String u_id,String hand,HttpListener listener,int what){
        RequestParams params = new RequestParams();
        params.addParam("u_id",u_id);
        params.addParam("hand",hand);
        new HttpUtils().doCall(AppConfig.BASE_URL+module+"/setHand",params,listener,what);
    }
    //验证手势密码
    public void yzHand(String u_id,String hand,HttpListener listener,int what){
        RequestParams params = new RequestParams();
        params.addParam("u_id",u_id);
        params.addParam("hand",hand);
        new HttpUtils().doCall(AppConfig.BASE_URL+module+"/yzHand",params,listener,what);
    }
    //忘记密码
    public void wjmm(String username,HttpListener listener,int what){
        RequestParams params = new RequestParams();
        params.addParam("username",username);
        new HttpUtils().doCall(AppConfig.BASE_URL+module+"/wjmm",params,listener,what);
    }

}
