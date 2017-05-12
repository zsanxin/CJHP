package com.kupurui.cjhp.ui.mailbox;

import android.os.Bundle;
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
import com.kupurui.cjhp.R;
import com.kupurui.cjhp.http.User;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 密保邮箱更改
 * Created by Administrator on 2017/4/17.
 */

public class MailboxSecurityMailboxAty extends BaseActivity implements HttpListener{

    @Bind(R.id.text_title)
    TextView text_title;
    @Bind(R.id.img_back)
    ImageView img_back;
    @Bind(R.id.btn_mailbox_next)
    Button btnMailboxNext;
    @Bind(R.id.edit_bind_mailbox)
    EditText eTBindMailBox;

    private String email = null;

    @Override
    public int getLayoutId() {
        return R.layout.mailbox_sercurity_mailbox_aty;
    }

    @Override
    public void initData() {
        text_title.setText(this.getString(R.string.security_mailbox));
        btnMailboxNext.setClickable(false);
        EditListener();
    }

    @Override
    public void requestData() {

    }

    @Override
    public void onSuccess(String result, Call call, Response response, int what) {
        Bundle bundle = new Bundle();
        bundle.putString("email",email);
        startActivity(MailboxCodeAty.class,bundle);
        super.onSuccess(result, call, response, what);
    }

    @OnClick({R.id.img_back,R.id.btn_mailbox_next})
    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()){
            case R.id.img_back:
                finish();
                break;
            case R.id.btn_mailbox_next:
                 email = eTBindMailBox.getText().toString();
                if (TextUtils.isEmpty(email)){
                    showToast("请输入邮箱");
                    return;
                }
                new User().sendEmail(email,this,1);
                break;
        }
    }

    /**
     * 监听输入框的变化
     */
    private void EditListener(){
        eTBindMailBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (eTBindMailBox.getText().length() > 0){
                    btnMailboxNext.setTextColor(getResources().getColor(R.color.white));
                    btnMailboxNext.setBackgroundResource(R.drawable.bind_mailbox_orange_button_bg);
                    btnMailboxNext.setClickable(true);
                }else{
                    btnMailboxNext.setTextColor(getResources().getColor(R.color.aBDBDBD));
                    btnMailboxNext.setBackgroundResource(R.drawable.bind_mailbox_gray_button_bg);
                    btnMailboxNext.setClickable(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
