package com.kupurui.cjhp.ui.login;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.frame.http.HttpListener;
import com.android.frame.ui.BaseActivity;
import com.kupurui.cjhp.R;
import com.kupurui.cjhp.http.User;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/4/19.
 */

public class LoginForgetPasswordCodeAty extends BaseActivity implements HttpListener{

    @Bind(R.id.text_title)
    TextView text_title;
    @Bind(R.id.img_back)
    ImageView img_back;
    @Bind(R.id.btn_next)
    Button btnNext;
    @Bind(R.id.edit_code)
    EditText eTCode;

    private String eamil = null;

    @Override
    public int getLayoutId() {
        return R.layout.login_forget_password_code_aty;
    }

    @Override
    public void initData() {
        Bundle bundle = getIntent().getExtras();
        eamil = bundle.getString("email");
        text_title.setText("忘记密码");
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
                String code = eTCode.getText().toString();
                if (TextUtils.isEmpty(code)){
                    showToast("请输入验证码");
                    return;
                }
                showLoadingDialog("");
                new User().confirm(eamil,code,this,1);
                break;
        }
    }

    @Override
    public void onSuccess(String result, Call call, Response response, int what) {
        switch (what){
            case 1:
                startActivity(LoginPasswordResetAty.class,null);
                break;
        }
        super.onSuccess(result, call, response, what);
    }
}
