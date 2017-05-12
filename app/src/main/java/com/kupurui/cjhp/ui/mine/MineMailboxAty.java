package com.kupurui.cjhp.ui.mine;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.frame.http.HttpListener;
import com.android.frame.ui.BaseActivity;
import com.android.frame.util.AppJsonUtil;
import com.kupurui.cjhp.R;
import com.kupurui.cjhp.config.UserManager;
import com.kupurui.cjhp.http.User;
import com.kupurui.cjhp.ui.mailbox.MailboxSecurityMailboxAty;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 密保邮箱
 * Created by Administrator on 2017/4/17.
 */

public class MineMailboxAty extends BaseActivity implements HttpListener{

    @Bind(R.id.text_title)
    TextView text_title;
    @Bind(R.id.img_back)
    ImageView img_back;
    @Bind(R.id.btn_next)
    Button btnNext;
    @Bind(R.id.text_email)
    TextView tVEmail;

    @Override
    public int getLayoutId() {
        return R.layout.mine_mailbox_aty;
    }

    @Override
    public void initData() {
        text_title.setText(this.getString(R.string.security_mailbox));

    }

    @Override
    public void requestData() {
        showLoadingContent();
        new User().myEmail(UserManager.getUserInfo().getU_id(),this,1);
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
                startActivity(MailboxSecurityMailboxAty.class,null);
                break;
        }
    }

    @Override
    public void onSuccess(String result, Call call, Response response, int what) {
        String email = AppJsonUtil.getString(result,"email");
        tVEmail.setText(email.replace(email.substring(3,email.indexOf("@")),"******"));
        super.onSuccess(result, call, response, what);
    }
}
