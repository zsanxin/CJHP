package com.kupurui.cjhp.ui.login;

import android.content.Intent;
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
import com.android.frame.util.SPUtils;
import com.kupurui.cjhp.R;
import com.kupurui.cjhp.http.User;
import com.kupurui.cjhp.ui.index.IndexAty;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

import static android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP;

/**
 * Created by Administrator on 2017/4/17.
 */

public class LoginBindMailboxCodeAty extends BaseActivity implements HttpListener{

    @Bind(R.id.text_title)
    TextView text_title;
    @Bind(R.id.img_back)
    ImageView img_back;
    @Bind(R.id.btn_bind_mailbox_sure)
    Button btn_bind_mailbox_sure;
    @Bind(R.id.edit_bind_mailbox_code)
    EditText eTBindMailBoxCode;

    private String email = null;
    private String code = null;
    private String u_id = null;

    @Override
    public int getLayoutId() {
        return R.layout.login_bind_mailbox_code_aty;
    }

    @Override
    public void initData() {
        Bundle bundle = getIntent().getExtras();
        email = bundle.getString("email");
        u_id = new SPUtils("CJH").get("u_id","").toString();
        text_title.setText(this.getString(R.string.bind_mailbox_code));
        btn_bind_mailbox_sure.setClickable(false);
        EditListener();
    }

    @Override
    public void requestData() {

    }


    @OnClick({R.id.btn_bind_mailbox_sure,R.id.img_back})
    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()){
            case R.id.btn_bind_mailbox_sure:
                code = eTBindMailBoxCode.getText().toString();
                if (TextUtils.isEmpty(code)){
                    showToast("请输入验证码");
                    return;
                }
                showLoadingDialog("");
                new User().bangding(u_id,email,code,this,1);
                break;
            case R.id.img_back:
                finish();
                break;
        }
    }

    /**
     * 监听输入框的变化
     */
    private void EditListener(){
        eTBindMailBoxCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (eTBindMailBoxCode.getText().length() > 0){
                    btn_bind_mailbox_sure.setTextColor(getResources().getColor(R.color.white));
                    btn_bind_mailbox_sure.setBackgroundResource(R.drawable.bind_mailbox_orange_button_bg);
                    btn_bind_mailbox_sure.setClickable(true);
                }else{
                    btn_bind_mailbox_sure.setTextColor(getResources().getColor(R.color.aBDBDBD));
                    btn_bind_mailbox_sure.setBackgroundResource(R.drawable.bind_mailbox_gray_button_bg);
                    btn_bind_mailbox_sure.setClickable(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onSuccess(String result, Call call, Response response, int what) {
        startActivity(IndexAty.class,null);
        Intent intent = new Intent(getApplicationContext(),IndexAty.class);
        intent.addFlags(FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
        super.onSuccess(result, call, response, what);
    }
}
