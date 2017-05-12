package com.kupurui.cjhp.ui.login;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.frame.http.HttpListener;
import com.android.frame.ui.BaseActivity;
import com.android.frame.util.AppJsonUtil;
import com.android.frame.util.SPUtils;
import com.kupurui.cjhp.R;
import com.kupurui.cjhp.bean.UserInfoBean;
import com.kupurui.cjhp.config.UserManager;
import com.kupurui.cjhp.http.User;
import com.kupurui.cjhp.ui.index.IndexAty;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 账号登录
 * Created by Administrator on 2017/4/17.
 */

public class LoginAccountLoginAty extends BaseActivity implements HttpListener {

    @Bind(R.id.btn_login)
    Button btn_login;
    @Bind(R.id.text_forget_password)
    TextView tVForgetPassword;
    @Bind(R.id.text_gesture_login)
    TextView tVGestureLogin;
    @Bind(R.id.edit_account)
    EditText eTAccount;
    @Bind(R.id.edit_password)
    EditText eTPassword;
    @Bind(R.id.ll_longin_bottom)
    LinearLayout layoutbottom;

    private String account = null;

    @Override
    public int getLayoutId() {
        return R.layout.login_account_login_aty;
    }

    @Override
    public void initData() {
        account = new SPUtils("CJH").get("account", "").toString();
        if (TextUtils.isEmpty(account)) {
            layoutbottom.setVisibility(View.GONE);
        } else {
            layoutbottom.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void requestData() {

    }

    @OnClick({R.id.btn_login, R.id.text_forget_password, R.id.text_gesture_login})
    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.btn_login:
                account = eTAccount.getText().toString().trim();
                String password = eTPassword.getText().toString().trim();
                if (TextUtils.isEmpty(account)) {
                    showToast("请输入账号");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    showToast("请输入密码");
                    return;
                }
                showLoadingDialog("");
                new User().login(account, password, this, 1);
                break;
            case R.id.text_forget_password:
                startActivity(LoginForgetPasswordAty.class, null);
                break;
            case R.id.text_gesture_login:
                startActivity(LoginGestureLoginAty.class, null);
                finish();
                break;
        }
    }

    @Override
    public void onSuccess(String result, Call call, Response response, int what) {
        if (what == 1) {
            UserInfoBean userInfoBean = AppJsonUtil.getObject(result, UserInfoBean.class);
            UserManager.setUserInfo(userInfoBean);
            String u_id = AppJsonUtil.getString(result, "u_id");

            //保存数据
            new SPUtils("CJH").put("u_id",u_id);
            new SPUtils("CJH").put("account", account);
            String shoushi = AppJsonUtil.getString(result, "shoushi");
            new SPUtils("CJH").put("shoushi", shoushi);
            String status = AppJsonUtil.getString(result, "status");
            if (status.equals("0")) {
                //未绑定邮箱
                startActivity(LoginBindMailboxAty.class, null);
            } else if (status.equals("1")) {
                //已绑定
                startActivity(IndexAty.class, null);
            }
            finish();
        }
        super.onSuccess(result, call, response, what);
    }
}
