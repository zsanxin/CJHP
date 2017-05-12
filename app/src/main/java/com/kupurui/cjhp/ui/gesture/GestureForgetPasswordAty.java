package com.kupurui.cjhp.ui.gesture;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.frame.http.HttpListener;
import com.android.frame.ui.BaseActivity;
import com.android.frame.util.SPUtils;
import com.kupurui.cjhp.R;
import com.kupurui.cjhp.http.User;
import com.kupurui.cjhp.ui.mine.MineGesturePasswordAty;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 忘记手势密码
 * Created by Administrator on 2017/4/19.
 */

public class GestureForgetPasswordAty extends BaseActivity implements HttpListener{

    @Bind(R.id.text_title)
    TextView text_title;
    @Bind(R.id.img_back)
    ImageView img_back;
    @Bind(R.id.btn_gesture_forget_password_next)
    Button btnGestureForgetPasswordNext;
    @Bind(R.id.edit_for_password)
    EditText eTForPassword;

    private String password;
    private String username = null;

    @Override
    public int getLayoutId() {
        return R.layout.gesture_forget_password_aty;
    }

    @Override
    public void initData() {
        text_title.setText("忘记手势密码");
        username = new SPUtils("CJH").get("account","").toString();
        btnGestureForgetPasswordNext.setClickable(false);
        EditListener();
    }

    @Override
    public void requestData() {

    }

    @OnClick({R.id.img_back,R.id.btn_gesture_forget_password_next})
    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()){
            case R.id.img_back:
                finish();
              break;
            case R.id.btn_gesture_forget_password_next:
                //如果匹配正确进行重新绘制手势密码
                //如果不正确则提示
                password = eTForPassword.getText().toString();
                if (TextUtils.isEmpty(password)){
                    showToast("请输入密码");
                    return;
                }
                showLoadingDialog("");
                new User().forgetHand(username,password,this,1);
                break;
        }
    }

    @Override
    public void onSuccess(String result, Call call, Response response, int what) {
        startActivity(MineGesturePasswordAty.class,null);
        super.onSuccess(result, call, response, what);
    }

    /**
     * 监听输入框变化
     */
    private void EditListener(){
        eTForPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(eTForPassword.getText().length()>0){
                    btnGestureForgetPasswordNext.setTextColor(getResources().getColor(R.color.white));
                    btnGestureForgetPasswordNext.setBackgroundResource(R.drawable.bind_mailbox_orange_button_bg);
                    btnGestureForgetPasswordNext.setClickable(true);
                }else{
                    btnGestureForgetPasswordNext.setTextColor(getResources().getColor(R.color.aBDBDBD));
                    btnGestureForgetPasswordNext.setBackgroundResource(R.drawable.bind_mailbox_gray_button_bg);
                    btnGestureForgetPasswordNext.setClickable(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
