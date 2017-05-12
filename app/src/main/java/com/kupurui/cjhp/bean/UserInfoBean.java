package com.kupurui.cjhp.bean;

/**
 * Created by Administrator on 2017/4/26.
 */

public class UserInfoBean {


    /**
     * status : 1
     * shoushi : 1
     * u_id : 3
     * app_id
     */

    private String status;
    private String shoushi;
    private String u_id;
    private String app_id;
    private String c_id;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getShoushi() {
        return shoushi;
    }

    public void setShoushi(String shoushi) {
        this.shoushi = shoushi;
    }

    public String getU_id() {
        return u_id;
    }

    public void setU_id(String u_id) {
        this.u_id = u_id;
    }

    public String getApp_id() {
        return app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public String getC_id(){
        return c_id;
    }

    public void setC_id(String c_id){
        this.c_id = c_id;
    }

}
