package com.kupurui.cjhp.ui;

import android.os.Build;
import android.view.WindowManager;

import com.android.frame.ui.BaseActivity;
import com.kupurui.cjhp.R;

/**
 * 欢迎页
 * Created by Administrator on 2017/4/17.
 */

public class WelcomeActivity extends BaseActivity{


    @Override
    public int getLayoutId() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        return R.layout.welcome_aty;
    }

    @Override
    public void initData() {

    }

    @Override
    public void requestData() {

    }

}
