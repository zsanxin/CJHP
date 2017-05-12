package com.kupurui.cjhp.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/4/25.
 */

public class PostitInfo {


    /**
     * r_id : 34
     * title : 啊
     * content : a啊
     * img : ["http://local.cjh.com/upload/images/20170418\\e0981602c07d5c72197f87e82bdf083e.jpg","http://local.cjh.com/upload/images/20170418\\6eb920464691c4ab08138a847d18d39d.jpg","http://local.cjh.com/upload/images/20170418\\983ac846d8e64cf29e3fdba5e459246a.jpg"]
     * addtime : 2017-04-18 16:38:10
     */

    private int r_id;
    private String title;
    private String content;
    private String addtime;
    private List<String> img;

    public int getR_id() {
        return r_id;
    }

    public void setR_id(int r_id) {
        this.r_id = r_id;
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
