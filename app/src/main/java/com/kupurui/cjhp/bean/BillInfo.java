package com.kupurui.cjhp.bean;

/**
 * Created by Administrator on 2017/5/2.
 */

public class BillInfo {


    /**
     * month : 201705
     * member : 3-38(谢伟)
     * detail : CJH000006
     * total : 5067.5
     * groupid : 002
     */

    private String month;
    private String member;
    private String detail;
    private String total;
    private String groupid;

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }
}
