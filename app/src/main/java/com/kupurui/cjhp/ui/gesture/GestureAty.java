package com.kupurui.cjhp.ui.gesture;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.frame.http.HttpListener;
import com.android.frame.ui.BaseActivity;
import com.android.frame.util.SPUtils;
import com.kupurui.cjhp.R;
import com.kupurui.cjhp.http.User;
import com.kupurui.cjhp.ui.index.IndexAty;
import com.kupurui.cjhp.view.LocusPassWordView;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

import static android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP;

/**
 * Created by Administrator on 2017/4/19.
 */

public class GestureAty extends BaseActivity implements HttpListener{

    @Bind(R.id.text_title)
    TextView text_title;
    @Bind(R.id.img_back)
    ImageView img_back;
    @Bind(R.id.text_prompt)
    TextView text_prompt;
    @Bind(R.id.text_confirm)
    TextView text_confirm;
    @Bind(R.id.text_repaint)
    TextView text_repaint;
    @Bind(R.id.locus_password)
    LocusPassWordView locusPassWordView;

    String oldPassword = null;
    String u_id = null;
    String repassword = null;

    @Override
    public int getLayoutId() {
        return R.layout.gesture_password_aty;
    }

    @Override
    public void initData() {
        Bundle bundle = getIntent().getExtras();
        oldPassword = bundle.getString("password");
        u_id = new SPUtils("CJH").get("u_id","").toString();

        text_title.setText("设置手势密码");
        text_confirm.setClickable(false);

        locusPassWordView.setOnCompleteListener(new LocusPassWordView.OnCompleteListener() {
            @Override
            public void onComplete(String password) {
                repassword = password;
                if (password.length()<=3){
                    showToast("密码长度不够");
                    locusPassWordView.clearPassword();
                    return;
                }
                if (oldPassword.equals(password)){
                    text_prompt.setText("你的新解锁图案");
                    text_confirm.setTextColor(getResources().getColor(R.color.black));
                    text_confirm.setClickable(true);
                }else{
                    text_prompt.setText("两次密码不一致");
                    locusPassWordView.markError();
                }

            }
        });
    }

    @Override
    public void requestData() {

    }

    @OnClick({R.id.img_back,R.id.text_repaint,R.id.text_confirm})
    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()){
            case R.id.img_back:
                finish();
                break;
            case R.id.text_repaint:
                finish();
                break;
            case R.id.text_confirm:
                //确认
                showLoadingDialog("");
                new User().setHand(u_id,repassword,this,1);
                break;
        }
    }

    @Override
    public void onSuccess(String result, Call call, Response response, int what) {
        switch (what){
            case 1:
                showToast("设置成功");
                Intent intent = new Intent(getApplicationContext(), IndexAty.class);
                intent.addFlags(FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
                break;
        }
        super.onSuccess(result, call, response, what);
    }
}
