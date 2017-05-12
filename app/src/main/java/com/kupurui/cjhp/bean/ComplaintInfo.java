package com.kupurui.cjhp.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/4/25.
 */

public class ComplaintInfo {


    /**
     * c_id : 5
     * title : 啊
     * content : a啊
     * img : ["http://local.cjh.com/upload/images/20170418\\9e886ab049907033772a0cc85c3f717a.jpg","http://local.cjh.com/upload/images/20170418\\84473a2c27740e88fa45cacd26583432.jpg","http://local.cjh.com/upload/images/20170418\\7ac4be7a0bd4b7b432ef11bf8dabd26e.jpg"]
     * addtime : 2017-04-18 16:43:32
     */

    private int c_id;
    private String title;
    private String content;
    private String addtime;
    private List<String> img;

    public int getC_id() {
        return c_id;
    }

    public void setC_id(int c_id) {
        this.c_id = c_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public List<String> getImg() {
        return img;
    }

    public void setImg(List<String> img) {
        this.img = img;
    }
}
