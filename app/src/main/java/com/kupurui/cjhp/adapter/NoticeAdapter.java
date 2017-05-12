package com.kupurui.cjhp.adapter;

import android.content.Context;
import android.net.Uri;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.android.frame.adapter.CommonAdapter;
import com.android.frame.adapter.ViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.kupurui.cjhp.R;
import com.kupurui.cjhp.bean.NoticeInfo;

import java.util.List;

/**
 * Created by Administrator on 2017/4/18.
 */

public class NoticeAdapter extends CommonAdapter<NoticeInfo> {
    public NoticeAdapter(Context context, List<NoticeInfo> mList, int itemLayoutId) {
        super(context, mList, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder holder, NoticeInfo item, int positon) {
        holder.setTextViewText(R.id.text_notice_title,item.getTitle());
        if (TextUtils.isEmpty(item.getFengmiantu())){
            holder.setViewVisibility(R.id.img_notice_icon, View.GONE);
        }else{
            holder.setViewVisibility(R.id.img_notice_icon, View.VISIBLE);
            SimpleDraweeView imgvIcon = holder.getView(R.id.img_notice_icon);
            imgvIcon.setImageURI(Uri.parse(item.getFengmiantu()));
        }

        TextView tvMessage = holder.getView(R.id.text_notice_message);
        tvMessage.setText(Html.fromHtml(item.getContent()));
        holder.setTextViewText(R.id.text_notice_data,item.getAddtime());
    }
}
