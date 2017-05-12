package com.kupurui.cjhp.ui.postit;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.frame.http.HttpListener;
import com.android.frame.ui.BaseActivity;
import com.kupurui.cjhp.R;
import com.kupurui.cjhp.http.Complaint;
import com.kupurui.cjhp.http.Repair;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 写评论
 * Created by Administrator on 2017/4/20.
 */

public class PostitWriteCommentsAty extends BaseActivity implements HttpListener{

    @Bind(R.id.text_cancle)
    TextView tVCancle;
    @Bind(R.id.text_release)
    TextView tVReslease;
    @Bind(R.id.edit_comment)
    EditText eTCommont;
    @Bind(R.id.ratingbar)
    RatingBar ratingBar;

    private String id;
    private String aa = null;//判断类型是投诉建议还是报事报修

    @Override
    public int getLayoutId() {
        return R.layout.postit_write_comments_aty;
    }

    @Override
    public void initData() {
        Bundle bundle = getIntent().getExtras();
        id = bundle.getString("id");
        aa = bundle.getString("aa");
    }

    @Override
    public void requestData() {

    }

    @OnClick({R.id.text_cancle,R.id.text_release})
    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()){
            case R.id.text_cancle:
                //取消
                finish();
                break;
            case R.id.text_release:
                //发布
                String comment = eTCommont.getText().toString();
                if (TextUtils.isEmpty(comment)){
                    showToast("请输入内容");
                    return;
                }
                String star = String.valueOf(ratingBar.getRating());
                if (TextUtils.isEmpty(star)){
                    showToast("请选择星级");
                    return;
                }
                showLoadingDialog("");
                if (aa.equals("c")){
                    new Complaint().comment(id,star,comment,this,1);
                }else if(aa.equals("r")){
                    new Repair().comment(id,star,comment,this,1);
                }
                break;
        }
    }

    @Override
    public void onSuccess(String result, Call call, Response response, int what) {
        finish();
        super.onSuccess(result, call, response, what);
    }
}
