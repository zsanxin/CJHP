package com.kupurui.cjhp.ui.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.frame.ui.BaseActivity;
import com.kupurui.cjhp.R;
import com.kupurui.cjhp.ui.gesture.GestureAty;
import com.kupurui.cjhp.view.LocusPassWordView;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 手势密码
 * Created by Administrator on 2017/4/17.
 */

public class MineGesturePasswordAty extends BaseActivity {

    @Bind(R.id.text_title)
    TextView text_title;
    @Bind(R.id.img_back)
    ImageView img_back;
    @Bind(R.id.locus_password)
    LocusPassWordView locusPassword;

    @Override
    public int getLayoutId() {
        return R.layout.mine_gesture_password_aty;
    }

    @Override
    public void initData() {
        text_title.setText("设置手势密码");
        //监听绘制完成
        locusPassword.setOnCompleteListener(new LocusPassWordView.OnCompleteListener() {
            @Override
            public void onComplete(String password) {
                Bundle bundle = new Bundle();
                bundle.putString("password",password);
                startActivity(GestureAty.class,bundle);
                locusPassword.clearPassword();
            }
        });
    }

    @Override
    public void requestData() {

    }

    @OnClick({R.id.img_back})
    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()){
            case R.id.img_back:
                finish();
                break;
        }
    }
}
