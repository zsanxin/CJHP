package com.kupurui.cjhp.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.android.frame.adapter.CommonAdapter;
import com.android.frame.adapter.ViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.kupurui.cjhp.R;
import com.kupurui.cjhp.bean.PostitInfo;
import com.kupurui.cjhp.ui.PhotoViewAty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/25.
 */

public class PostitAdapter extends CommonAdapter<PostitInfo>{

    Context context;
    public PostitAdapter(Context context, List<PostitInfo> mList, int itemLayoutId){
        super(context, mList, itemLayoutId);
        this.context=context;
    }

    @Override
    public void convert(ViewHolder holder, final PostitInfo item, int positon) {
        holder.setTextViewText(R.id.text_data,item.getAddtime());
        holder.setTextViewText(R.id.text_title,item.getTitle());
        holder.setTextViewText(R.id.text_content,item.getContent());

        SimpleDraweeView simpleDraweeView1 = holder.getView(R.id.img_icon1);
        SimpleDraweeView simpleDraweeView2 = holder.getView(R.id.img_icon2);
        SimpleDraweeView simpleDraweeView3 = holder.getView(R.id.img_icon3);

        List<SimpleDraweeView> imgviews = new ArrayList<>();
        imgviews.add(simpleDraweeView1);
        imgviews.add(simpleDraweeView2);
        imgviews.add(simpleDraweeView3);

        for (SimpleDraweeView imgview : imgviews) {
            imgview.setVisibility(View.GONE);
        }

        for (int i = 0; i < item.getImg().size(); i++) {
            imgviews.get(i).setVisibility(View.VISIBLE);
            imgviews.get(i).setImageURI(Uri.parse(item.getImg().get(i)));
            showPic(imgviews.get(i),item.getImg().get(i));
        }

    }

    public void showPic(View view, final String ss){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PhotoViewAty.class);
                Bundle bundle = new Bundle();
                bundle.putString("uri",ss);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

}
