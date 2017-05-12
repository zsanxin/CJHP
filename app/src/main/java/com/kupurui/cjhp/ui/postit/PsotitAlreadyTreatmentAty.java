package com.kupurui.cjhp.ui.postit;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.frame.http.HttpListener;
import com.android.frame.ui.BaseActivity;
import com.facebook.drawee.view.SimpleDraweeView;
import com.kupurui.cjhp.R;
import com.kupurui.cjhp.http.Complaint;
import com.kupurui.cjhp.http.Repair;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 已处理详情
 * Created by Administrator on 2017/4/20.
 */

public class PsotitAlreadyTreatmentAty extends BaseActivity implements HttpListener{

    @Bind(R.id.text_title)
    TextView text_title;
    @Bind(R.id.img_back)
    ImageView img_back;
    @Bind(R.id.layout_comments)
    LinearLayout layoutComments;

    @Bind(R.id.text_data)
    TextView tVData;
    @Bind(R.id.text_already_title)
    TextView tVTitle;
    @Bind(R.id.text_content)
    TextView tVContent;
    @Bind(R.id.layout_remarks)
    LinearLayout layoutRemarks;
    @Bind(R.id.text_beizhu)
    TextView tVBeizhu;
    @Bind(R.id.layout_my_comments)
    LinearLayout layoutMyComments;
    @Bind(R.id.text_comment)
    TextView tVComment;
    @Bind(R.id.text_comment_data)
    TextView tVCommentData;
    @Bind(R.id.ratingbar)
    RatingBar ratingBar;
    @Bind(R.id.img_complet)
    ImageView imgComplet;

    @Bind(R.id.img_comment_icon1)
    SimpleDraweeView simpleDraweeView1;
    @Bind(R.id.img_comment_icon2)
    SimpleDraweeView simpleDraweeView2;
    @Bind(R.id.img_comment_icon3)
    SimpleDraweeView simpleDraweeView3;

    private String id;
    private String aa = null;//判断类型是投诉建议还是报事报修

    private List<String> img = new ArrayList<>();


    @Override
    public int getLayoutId() {
        return R.layout.postit_already_treatment_aty;
    }

    @Override
    public void initData() {
        Bundle bundle = getIntent().getExtras();
        id = bundle.getString("id");
        aa = bundle.getString("aa");
        text_title.setText("已处理");
    }

    @Override
    public void requestData() {
        showLoadingContent();
        if (aa.equals("c")){
            new Complaint().detail(String.valueOf(id),this,1);
        }else if(aa.equals("r")){
            new Repair().detail(String.valueOf(id),this,1);
        }
    }

    @OnClick({R.id.img_back,R.id.layout_comments})
    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()){
            case R.id.img_back:
                finish();
                break;
            case R.id.layout_comments:
                Bundle bundle = new Bundle();
                bundle.putString("id",id);
                bundle.putString("aa",aa);
                startActivity(PostitWriteCommentsAty.class,bundle);
                finish();
                break;
        }
    }

    @Override
    public void onSuccess(String result, Call call, Response response, int what) {
        switch (what){
            case 1:
                try {
                    JSONObject jsonObject = new JSONObject(result).getJSONObject("show_data");
                    tVData.setText(jsonObject.getString("addtime"));
                    tVTitle.setText(jsonObject.getString("title"));
                    tVContent.setText(jsonObject.getString("content"));
                    String remarks = jsonObject.getString("beizhu");
                    if (!TextUtils.isEmpty(remarks)){
                        layoutRemarks.setVisibility(View.VISIBLE);
                        tVBeizhu.setText(remarks);
                    }
                    String comment = jsonObject.getString("comment");
                    if (!TextUtils.isEmpty(comment)){
                        imgComplet.setVisibility(View.VISIBLE);
                        layoutComments.setVisibility(View.GONE);
                        layoutMyComments.setVisibility(View.VISIBLE);
                        tVComment.setText(comment);
                        tVCommentData.setText(jsonObject.getString("addtime"));
                        ratingBar.setRating(jsonObject.getInt("star"));
                    }

                    String jsonimg = jsonObject.getString("img");
                    if (jsonimg.equals("") || jsonimg == null){

                    }else{
                        JSONArray jsonArray = new JSONArray(jsonimg);
                        for (int i = 0;i<jsonArray.length();i++){
                            img.add(jsonArray.getString(i));
                        }
                    }

                    if (img.size() == 1){
                        simpleDraweeView1.setVisibility(View.VISIBLE);
                        simpleDraweeView1.setImageURI(img.get(0));
                        return;
                    }
                    if (img.size() == 2){
                        simpleDraweeView1.setVisibility(View.VISIBLE);
                        simpleDraweeView1.setImageURI(img.get(0));
                        simpleDraweeView2.setVisibility(View.VISIBLE);
                        simpleDraweeView2.setImageURI(img.get(1));
                        return;
                    }
                    if (img.size() == 3){
                        simpleDraweeView1.setVisibility(View.VISIBLE);
                        simpleDraweeView1.setImageURI(img.get(0));
                        simpleDraweeView2.setVisibility(View.VISIBLE);
                        simpleDraweeView2.setImageURI(img.get(1));
                        simpleDraweeView3.setVisibility(View.VISIBLE);
                        simpleDraweeView3.setImageURI(img.get(2));
                        return;
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
                break;
        }
        super.onSuccess(result, call, response, what);
    }

    @Override
    protected void onResume() {
        if (aa.equals("c")){
            new Complaint().detail(String.valueOf(id),this,1);
        }else if(aa.equals("r")){
            new Repair().detail(String.valueOf(id),this,1);
        }
        super.onResume();
    }
}
