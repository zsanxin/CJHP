package com.kupurui.cjhp.config;

import com.alibaba.fastjson.JSON;
import com.android.frame.config.BaseUserManger;
import com.android.frame.util.SPUtils;
import com.kupurui.cjhp.bean.UserInfoBean;

/**
 * Created by Administrator on 2017/4/26.
 */

public class UserManager extends BaseUserManger{

    public static void setUserInfo(UserInfoBean userInfo) {
        SPUtils spUtils = new SPUtils("userConfig");
        spUtils.put("userInfo", JSON.toJSONString(userInfo));
    }

    public static UserInfoBean getUserInfo() {
        SPUtils spUtils = new SPUtils("userConfig");
        String userdata = (String) spUtils.get("userInfo", "{}");
        return JSON.parseObject(userdata, UserInfoBean.class);
    }


    /**
     * 设置更新的appid
     *
     * @param
     * @param app_id
     */
    public static void setAppid(long app_id) {
        SPUtils spUtils = new SPUtils("userConfig");
        spUtils.put("app_id", app_id + "");
    }

    /**
     * 获得版本下载的id
     *
     * @return
     */
    public static long getAppid() {
        SPUtils spUtils = new SPUtils("userConfig");
        String id = (String) spUtils.get("app_id", "");
        return Long.valueOf(id);
    }



    /**
     * 设置是否第一次打开app
     *
     * @param isFirstOpen
     */
    public static void setFirstOpen(boolean isFirstOpen) {
        SPUtils spUtils = new SPUtils("userConfig");
        spUtils.put("isFirstOpen", isFirstOpen);
    }

    /**
     * 是否第一次打开app
     *
     * @return
     */
    public static boolean isFirstOpen() {
        SPUtils spUtils = new SPUtils("userConfig");
        boolean isFirstOpen = (boolean) spUtils.get("isFirstOpen", true);
        return isFirstOpen;
    }


    /**
     * 设置忽略的版本号
     *
     * @param
     * @return
     */
    public static void setIgnoreVersion(String version) {
        SPUtils spUtils = new SPUtils("userConfig");
        spUtils.put("ignoreVersion", version);
    }


    /**
     * 获得忽略的版本号
     *
     * @param
     * @return
     */
    public static String getIgnoreVersion() {
        SPUtils spUtils = new SPUtils("userConfig");
        String versionCode = (String) spUtils.get("ignoreVersion", "0");
        return versionCode;
    }
}
