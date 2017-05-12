package com.kupurui.cjhp.ui.login;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.frame.http.HttpListener;
import com.android.frame.ui.BaseActivity;
import com.android.frame.util.Toolkit;
import com.kupurui.cjhp.R;
import com.kupurui.cjhp.http.User;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/4/17.
 */

public class LoginBindMailboxAty extends BaseActivity implements HttpListener{

    @Bind(R.id.text_title)
    TextView text_title;
    @Bind(R.id.img_back)
    ImageView img_back;
    @Bind(R.id.btn_bind_mailbox_next)
    Button btn_bind_mailbox_next;
    @Bind(R.id.edit_bind_mailbox)
    EditText eTBindMailBox;

    private String email = null;

    @Override
    public int getLayoutId() {
        return R.layout.login_bind_mailbox_aty;
    }

    @Override
    public void initData() {
        text_title.setText(this.getString(R.string.bind_mailbox));
        btn_bind_mailbox_next.setClickable(false);
        EditListener();
    }

    @Override
    public void requestData() {

    }

    @OnClick({R.id.btn_bind_mailbox_next,R.id.img_back})
    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()){
            case R.id.btn_bind_mailbox_next:
                email = eTBindMailBox.getText().toString();
                if (!Toolkit.isEmail(email)){
                    showToast("请输入正确的邮箱");
                    return;
                }
                showLoadingDialog("");
                new User().sendEmail(email,this,1);
                break;
            case R.id.img_back:
                finish();
                break;
        }
    }

    /**
     * 监听输入框变化
     */
    private void EditListener(){
        eTBindMailBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(eTBindMailBox.getText().length()>0){
                    btn_bind_mailbox_next.setTextColor(getResources().getColor(R.color.white));
                    btn_bind_mailbox_next.setBackgroundResource(R.drawable.bind_mailbox_orange_button_bg);
                    btn_bind_mailbox_next.setClickable(true);
                }else{
                    btn_bind_mailbox_next.setTextColor(getResources().getColor(R.color.aBDBDBD));
                    btn_bind_mailbox_next.setBackgroundResource(R.drawable.bind_mailbox_gray_button_bg);
                    btn_bind_mailbox_next.setClickable(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onSuccess(String result, Call call, Response response, int what) {
        Bundle bundle = new Bundle();
        bundle.putString("email",email);
        startActivity(LoginBindMailboxCodeAty.class,bundle);
        super.onSuccess(result, call, response, what);
    }
}
