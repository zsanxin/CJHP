package com.kupurui.cjhp.ui.gesture;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.frame.ui.BaseActivity;
import com.kupurui.cjhp.R;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 修改手势密码
 * Created by Administrator on 2017/4/19.
 */

public class GestureModifyPasswordAty extends BaseActivity{

    @Bind(R.id.text_title)
    TextView text_title;
    @Bind(R.id.img_back)
    ImageView img_back;
    @Bind(R.id.rlayout_modify_password)
    RelativeLayout relativeLayoutModifyPassword;

    @Override
    public int getLayoutId() {
        return R.layout.geture_modify_password_aty;
    }

    @Override
    public void initData() {
        text_title.setText(this.getString(R.string.gesture_password));
    }

    @Override
    public void requestData() {

    }

    @OnClick({R.id.img_back,R.id.rlayout_modify_password})
    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()){
            case R.id.img_back:
                finish();
                break;
            case R.id.rlayout_modify_password:
                startActivity(GesturePaintPasswordAty.class,null);
                break;
        }
    }
}
