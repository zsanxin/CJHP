package com.kupurui.cjhp.ui.login;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.frame.ui.BaseActivity;
import com.android.frame.util.SPUtils;
import com.kupurui.cjhp.R;
import com.kupurui.cjhp.http.User;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 密码重置
 * Created by Administrator on 2017/4/19.
 */

public class LoginPasswordResetAty extends BaseActivity{

    @Bind(R.id.text_title)
    TextView text_title;
    @Bind(R.id.img_back)
    ImageView img_back;

    @Bind(R.id.edit_pwd)
    EditText eTPassword;
    @Bind(R.id.edit_pwd_b)
    EditText eTPasswordb;

    private String u_id = null;

    @Override
    public int getLayoutId() {
        return R.layout.login_password_reset_aty;
    }

    @Override
    public void initData() {
        text_title.setText("密码重置");
        u_id = new SPUtils("CJH").get("u_id","").toString();
    }

    @Override
    public void requestData() {

    }

    @OnClick({R.id.img_back,R.id.btn_next})
    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()){
            case R.id.img_back:
                finish();
                break;
            case R.id.btn_next:
                String password = eTPassword.getText().toString();
                String repassword = eTPasswordb.getText().toString();
                if (TextUtils.isEmpty(password)){
                    showToast("请输入密码");
                    return;
                }
                if (TextUtils.isEmpty(repassword)){
                    showToast("请输入确认密码");
                }
                showLoadingDialog("");
                new User().setPassword(u_id,password,repassword,this,1);
                break;
        }
    }

    @Override
    public void onSuccess(String result, Call call, Response response, int what) {
        super.onSuccess(result, call, response, what);
    }
}
