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
import com.android.frame.util.AppJsonUtil;
import com.android.frame.util.SPUtils;
import com.kupurui.cjhp.R;
import com.kupurui.cjhp.http.User;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 忘记密码
 * Created by Administrator on 2017/4/19.
 */

public class LoginForgetPasswordAty extends BaseActivity implements HttpListener{

    @Bind(R.id.text_title)
    TextView text_title;
    @Bind(R.id.img_back)
    ImageView img_back;
    @Bind(R.id.btn_next)
    Button btn_next;
    @Bind(R.id.edit_username)
    EditText eTUserName;

    @Override
    public int getLayoutId() {
        return R.layout.login_forget_password_aty;
    }

    @Override
    public void initData() {
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
                String username = eTUserName.getText().toString();
                if (TextUtils.isEmpty(username)){
                    showToast("请输入账号");
                    return;
                }
                showLoadingDialog("");
                new User().wjmm(username,this,1);
                break;
        }
    }

    @Override
    public void onSuccess(String result, Call call, Response response, int what) {
        switch (what){
            case 1:
                Bundle bundle = new Bundle();
                bundle.putString("email", AppJsonUtil.getString(result,"email"));
                new SPUtils("CJH").put("u_id",AppJsonUtil.getString(result,"u_id"));
                startActivity(LoginForgetPasswordCodeAty.class,bundle);
                break;
        }
        super.onSuccess(result, call, response, what);
    }
}
