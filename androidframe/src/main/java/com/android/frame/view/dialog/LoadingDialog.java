package com.android.frame.view.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.android.frame.R;


/**
 * Created by Administrator on 2016/4/14.
 */
public class LoadingDialog extends BaseDialog {

    TextView tv_message;
    public LoadingDialog(Context context) {
        this(context, 0);


    }

    public LoadingDialog(Context context, int theme) {
        super(context, theme);
        View loadingView= LayoutInflater.from(context).inflate(R.layout.frame_loading_dialog,null,false);
        tv_message= (TextView) loadingView.findViewById(R.id.frame_tv_message);
        setContentView(loadingView);
    }

    public void showLoadingDialog(String message) {
        if (!TextUtils.isEmpty(message)) {
            tv_message.setText(message);
            Log.e("message",message);
        }else {
//            tv_message.setText("正在请求");
            tv_message.setText("正在请求");
            Log.e("message","空的");
        }
        show();
    }
}
