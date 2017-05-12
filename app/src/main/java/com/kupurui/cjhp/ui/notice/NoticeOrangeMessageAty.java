package com.kupurui.cjhp.ui.notice;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.frame.http.HttpListener;
import com.android.frame.ui.BaseActivity;
import com.android.frame.util.AppJsonUtil;
import com.kupurui.cjhp.R;
import com.kupurui.cjhp.http.Message;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 公告通知内容
 * Created by Administrator on 2017/4/18.
 */

public class NoticeOrangeMessageAty extends BaseActivity implements HttpListener{

    @Bind(R.id.text_title)
    TextView text_title;
    @Bind(R.id.img_back)
    ImageView img_back;
    @Bind(R.id.text_notice_message_title)
    TextView tVNoticeMessageTitle;
    @Bind(R.id.text_notice_message_data)
    TextView tVNoticeMessageData;
    @Bind(R.id.text_notice_message_content)
    TextView tVNoticeMessageContent;

    private String id = null;
    private String title = null;
    private String data = null;
    private String content = null;

    @Override
    public int getLayoutId() {
        return R.layout.notice_orange_message_aty;
    }

    @Override
    public void initData() {
        Bundle bundle = getIntent().getExtras();
        id = bundle.getString("id");
        text_title.setText(this.getString(R.string.notice_orange));
    }

    @Override
    public void requestData() {
        showLoadingContent();
        new Message().detail(id,this,1);
    }

    @Override
    public void onSuccess(String result, Call call, Response response, int what) {
        title  = AppJsonUtil.getString(result,"title");
        data = AppJsonUtil.getString(result,"addtime");
        content = AppJsonUtil.getString(result,"content");
        tVNoticeMessageTitle.setText(title);
        tVNoticeMessageData.setText(data);
        tVNoticeMessageContent.setText("\t\t\t\t"+Html.fromHtml(content));
        super.onSuccess(result, call, response, what);
    }

    @OnClick({R.id.img_back})
    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()){
            case R.id.img_back:
                finish();
                break;
        }
    }
}
