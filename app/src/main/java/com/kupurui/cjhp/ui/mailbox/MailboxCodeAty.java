package com.kupurui.cjhp.ui.mailbox;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
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
 * 验证码
 * Created by Administrator on 2017/4/17.
 */

public class MailboxCodeAty extends BaseActivity implements HttpListener{

    @Bind(R.id.text_title)
    TextView text_title;
    @Bind(R.id.img_back)
    ImageView img_back;
    @Bind(R.id.btn_mailbox_code_next)
    Button btnMailboxCodeNext;
    @Bind(R.id.edit_bind_mailbox_code)
    EditText eTBindMailBoxCode;

    private AlertDialog myDialog;
    private String email = null;

    @Override
    public int getLayoutId() {
        return R.layout.mailbox_code_aty;
    }

    @Override
    public void initData() {
        Bundle bundle = getIntent().getExtras();
        email = bundle.getString("email");
        text_title.setText(this.getText(R.string.bind_mailbox_code));
        btnMailboxCodeNext.setClickable(false);
        EditListener();
    }

    @Override
    public void requestData() {

    }

    @Override
    public void onSuccess(String result, Call call, Response response, int what) {
        startActivity(MailboxBindNewMailboxAty.class,null);
        super.onSuccess(result, call, response, what);
    }

    @Override
    public void onFailure(Call call, int what) {
        showdialog();
        super.onFailure(call, what);
    }

    @OnClick({R.id.img_back,R.id.btn_mailbox_code_next})
    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()){
            case R.id.img_back:
                finish();
                break;
            case R.id.btn_mailbox_code_next:
                String code = eTBindMailBoxCode.getText().toString();
                showLoadingDialog("");
                new User().confirm(email,code,this,1);
                break;
        }
    }

    private void showdialog(){
        myDialog = new AlertDialog.Builder(MailboxCodeAty.this).create();
        myDialog.show();
        myDialog.getWindow().setContentView(R.layout.code_error_dialog);
        myDialog.getWindow().findViewById(R.id.btn_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //确认
                myDialog.dismiss();
            }
        });
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
                    btnMailboxCodeNext.setTextColor(getResources().getColor(R.color.white));
                    btnMailboxCodeNext.setBackgroundResource(R.drawable.bind_mailbox_orange_button_bg);
                    btnMailboxCodeNext.setClickable(true);
                }else{
                    btnMailboxCodeNext.setTextColor(getResources().getColor(R.color.aBDBDBD));
                    btnMailboxCodeNext.setBackgroundResource(R.drawable.bind_mailbox_gray_button_bg);
                    btnMailboxCodeNext.setClickable(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

}
