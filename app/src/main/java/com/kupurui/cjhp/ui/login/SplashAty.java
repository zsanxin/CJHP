package com.kupurui.cjhp.ui.login;

import com.android.frame.ui.BaseActivity;
import com.android.frame.util.SPUtils;

/**
 * Created by Administrator on 2017/4/26.
 */

public class SplashAty extends BaseActivity{
    @Override
    public int getLayoutId() {
        return 0;
    }

    @Override
    public void initData() {
       String shoushi = new SPUtils("CJH").get("shoushi","").toString();
        if(shoushi.equals("1")){
            startActivity(LoginGestureLoginAty.class,null);
        }else {
            startActivity(LoginAccountLoginAty.class,null);
        }
        finish();
    }

    @Override
    public void requestData() {

    }
}
