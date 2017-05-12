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
import com.kupurui.cjhp.config.UserManager;
import com.kupurui.cjhp.http.User;
import com.kupurui.cjhp.ui.index.IndexAty;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 绑定新邮箱的验证码
 * Created by Administrator on 2017/4/20.
 */

public class MailboxBindNewMailboxCodeAty extends BaseActivity implements HttpListener{

    @Bind(R.id.text_title)
    TextView text_title;
    @Bind(R.id.img_back)
    ImageView img_back;
    @Bind(R.id.btn_bind_new_mailbox_sure)
    Button btnBindNewMailboxsure;
    @Bind(R.id.edit_bind_mailbox_code)
    EditText eTBindMailBoxCode;

    private String email = null;
    private String u_id = null;

    @Override
    public int getLayoutId() {
        return R.layout.mailbox_bind_new_mailbox_code_aty;
    }

    @Override
    public void initData() {
        Bundle bundle = getIntent().getExtras();
        email = bundle.getString("email");
        u_id = UserManager.getUserInfo().getU_id();

        text_title.setText(this.getString(R.string.bind_mailbox_code));
        btnBindNewMailboxsure.setClickable(false);
        EditListener();
    }

    @Override
    public void requestData() {

    }

    @Override
    public void onSuccess(String result, Call call, Response response, int what) {
        startActivity(IndexAty.class,null);
        super.onSuccess(result, call, response, what);
    }

    @OnClick({R.id.img_back,R.id.btn_bind_new_mailbox_sure})
    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()){
            case R.id.img_back:
                finish();
                break;
            case R.id.btn_bind_new_mailbox_sure:
                String code = eTBindMailBoxCode.getText().toString();
                if (TextUtils.isEmpty(code)){
                    showToast("请输入验证码");
                    return;
                }
                showLoadingDialog("");
                new User().bangding(u_id,email,code,this,1);
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
                    btnBindNewMailboxsure.setTextColor(getResources().getColor(R.color.white));
                    btnBindNewMailboxsure.setBackgroundResource(R.drawable.bind_mailbox_orange_button_bg);
                    btnBindNewMailboxsure.setClickable(true);
                }else{
                    btnBindNewMailboxsure.setTextColor(getResources().getColor(R.color.aBDBDBD));
                    btnBindNewMailboxsure.setBackgroundResource(R.drawable.bind_mailbox_gray_button_bg);
                    btnBindNewMailboxsure.setClickable(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

}
