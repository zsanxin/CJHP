package com.kupurui.cjhp.ui.index;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.frame.http.HttpListener;
import com.android.frame.ui.BaseActivity;
import com.kupurui.cjhp.R;
import com.kupurui.cjhp.config.UserManager;
import com.kupurui.cjhp.http.Message;
import com.kupurui.cjhp.ui.notice.NoticeOrangeAty;
import com.kupurui.cjhp.ui.notice.NoticeRedAty;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 公告通知
 * Created by Administrator on 2017/4/17.
 */

public class IndexNoticeAty extends BaseActivity implements HttpListener{

    @Bind(R.id.text_title)
    TextView text_title;
    @Bind(R.id.img_back)
    ImageView img_back;
    @Bind(R.id.rlayout_notice_orange)
    RelativeLayout relativeLayoutOrange;
    @Bind(R.id.rlayout_notice_red)
    RelativeLayout relativeLayoutRed;
    @Bind(R.id.text_gonggao_data)
    TextView tVgonggaoData;
    @Bind(R.id.text_gonggao_content)
    TextView tVgonggaoContent;
    @Bind(R.id.text_tongzhi_data)
    TextView tVtongzhiData;
    @Bind(R.id.text_tongzhi_content)
    TextView tVtongzhiContent;

    private String gonggaoData = null;
    private String tongzhiData = null;
    private String gonggaoTitle = null;
    private String tongzhiTitle = null;

    @Override
    public int getLayoutId() {
        return R.layout.index_notice_aty;
    }

    @Override
    public void initData() {
        text_title.setText(this.getString(R.string.index_notice));
    }

    @Override
    public void requestData() {
        showLoadingContent();
        new Message().index(UserManager.getUserInfo().getU_id(),this,1);
    }

    @Override
    public void onSuccess(String result, Call call, Response response, int what) {
        super.onSuccess(result, call, response, what);
        try {
            JSONObject jsonObject = new JSONObject(result).getJSONObject("show_data");
            JSONObject gonggao = jsonObject.getJSONObject("gonggao");
            gonggaoData = gonggao.getString("addtime");
            gonggaoTitle = gonggao.getString("title");
            JSONObject tongzhi = jsonObject.getJSONObject("tongzhi");
            tongzhiData = tongzhi.getString("addtime");
            tongzhiTitle = tongzhi.getString("title");
            tVgonggaoData.setText(gonggaoData);
            tVgonggaoContent.setText(gonggaoTitle);
            tVtongzhiData.setText(tongzhiData);
            tVtongzhiContent.setText(tongzhiTitle);
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    @OnClick({R.id.rlayout_notice_orange,R.id.rlayout_notice_red,R.id.img_back})
    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()){
            case R.id.rlayout_notice_orange:
                //公告
                startActivity(NoticeOrangeAty.class,null);
                break;
            case R.id.rlayout_notice_red:
                //通知
                startActivity(NoticeRedAty.class,null);
                break;
            case R.id.img_back:
                finish();
                break;
        }
    }
}
