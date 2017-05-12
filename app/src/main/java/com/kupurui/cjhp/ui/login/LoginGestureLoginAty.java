package com.kupurui.cjhp.ui.login;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.android.frame.http.HttpListener;
import com.android.frame.ui.BaseActivity;
import com.android.frame.util.AppJsonUtil;
import com.android.frame.util.SPUtils;
import com.kupurui.cjhp.R;
import com.kupurui.cjhp.bean.UserInfoBean;
import com.kupurui.cjhp.config.UserManager;
import com.kupurui.cjhp.http.User;
import com.kupurui.cjhp.ui.gesture.GestureForgetPasswordAty;
import com.kupurui.cjhp.ui.index.IndexAty;
import com.kupurui.cjhp.view.LocusPassWordView;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 手势登录
 * Created by Administrator on 2017/4/19.
 */

public class LoginGestureLoginAty extends BaseActivity implements HttpListener{

    @Bind(R.id.text_title)
    TextView text_title;
    @Bind(R.id.text_forget_gesture_password)
    TextView tVForgetGesturePassword;
    @Bind(R.id.text_change_login)
    TextView tVChangeLogin;
    @Bind(R.id.v_lpwd)
    LocusPassWordView locusPassWordView;
    @Bind(R.id.tv_id_num)
    TextView tVIdNum;

    private String username = null;
    private String id = null;
    private String hand = null;

    @Override
    public int getLayoutId() {
        return R.layout.login_gesture_login_aty;
    }

    @Override
    public void initData() {
        username = new SPUtils("CJH").get("account","").toString();
        id = new SPUtils("CJH").get("u_id","").toString();
        if (!TextUtils.isEmpty(id)){
            tVIdNum.setText(id);
        }
        text_title.setText("手势登录");
        locusPassWordView.setOnCompleteListener(new LocusPassWordView.OnCompleteListener() {
            @Override
            public void onComplete(String password) {
                hand = password;
                if (password.length()<=3){
                    showToast("密码长度不够");
                    locusPassWordView.clearPassword();
                    return;
                }
                handLogin();
            }
        });
    }

    @Override
    public void requestData() {

    }

    @OnClick({R.id.text_forget_gesture_password,R.id.text_change_login})
    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()){
            case R.id.text_forget_gesture_password:
                startActivity(GestureForgetPasswordAty.class,null);
                break;
            case R.id.text_change_login:
                startActivity(LoginAccountLoginAty.class,null);
                finish();
                break;
        }
    }

    private void handLogin(){
        showLoadingDialog("");
        new User().handLogin(username,hand,this,1);
    }

    @Override
    public void onSuccess(String result, Call call, Response response, int what) {
        UserInfoBean userInfoBean = AppJsonUtil.getObject(result, UserInfoBean.class);
        UserManager.setUserInfo(userInfoBean);
        startActivity(IndexAty.class,null);
        finish();
        super.onSuccess(result, call, response, what);
    }

    @Override
    public void onError(String result, Call call, Response response, int what) {
        super.onError(result, call, response, what);
        locusPassWordView.clearPassword();
    }
}
