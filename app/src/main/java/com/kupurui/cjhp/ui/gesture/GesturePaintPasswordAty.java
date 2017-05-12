package com.kupurui.cjhp.ui.gesture;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.frame.http.HttpListener;
import com.android.frame.ui.BaseActivity;
import com.kupurui.cjhp.R;
import com.kupurui.cjhp.config.UserManager;
import com.kupurui.cjhp.http.User;
import com.kupurui.cjhp.ui.mine.MineGesturePasswordAty;
import com.kupurui.cjhp.view.LocusPassWordView;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 绘制解锁图案
 * Created by Administrator on 2017/4/19.
 */

public class GesturePaintPasswordAty extends BaseActivity implements HttpListener{

    @Bind(R.id.text_title)
    TextView text_title;
    @Bind(R.id.img_back)
    ImageView img_back;
    @Bind(R.id.text_paint_gesture_pasword)
    TextView textViewPaintGesturePassword;
    @Bind(R.id.text_forget_gesture_password)
    TextView textViewForgetGesturePassword;
    @Bind(R.id.locus_password)
    LocusPassWordView locusPassWordView;

    private String u_id = null;

    @Override
    public int getLayoutId() {
        return R.layout.gesture_paint_password_aty;
    }

    @Override
    public void initData() {
        u_id = UserManager.getUserInfo().getU_id();
        text_title.setText(this.getString(R.string.gesture_password));
        locusPassWordView.setOnCompleteListener(new LocusPassWordView.OnCompleteListener() {
            @Override
            public void onComplete(String password) {
                locusPassWordView.getPasswordMinLength();
                if (password.length()<=3){
                    showToast("密码长度不够");
                    locusPassWordView.clearPassword();
                    return;
                }
                yzHand(password);
            }
        });
    }

    @Override
    public void requestData() {

    }


    @Override
    public void onError(String result, Call call, Response response, int what) {
        textViewPaintGesturePassword.setText("解锁图案绘制错误");
        textViewForgetGesturePassword.setVisibility(View.VISIBLE);
        locusPassWordView.markError();
        new TaskThread().start();
        super.onError(result, call, response, what);
    }

    @Override
    public void onSuccess(String result, Call call, Response response, int what) {
        startActivity(MineGesturePasswordAty.class,null);
        finish();
        super.onSuccess(result, call, response, what);
    }

    @OnClick({R.id.img_back,R.id.text_forget_gesture_password})
    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()){
            case R.id.img_back:
                finish();
                break;
            case R.id.text_forget_gesture_password:
                startActivity(GestureForgetPasswordAty.class,null);
                finish();
                break;
        }
    }

    private void yzHand(String password){
        showLoadingDialog("");
        new User().yzHand(u_id,password,this,1);
    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                locusPassWordView.clearPassword();
                break;
            }
        };
    };

    class TaskThread extends Thread {
        public void run() {
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            handler.sendEmptyMessage(0);
        };
    };
}
