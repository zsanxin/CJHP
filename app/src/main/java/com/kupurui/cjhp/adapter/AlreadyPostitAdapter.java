package com.kupurui.cjhp.adapter;

import android.content.Context;

import com.android.frame.adapter.CommonAdapter;
import com.android.frame.adapter.ViewHolder;
import com.kupurui.cjhp.R;
import com.kupurui.cjhp.bean.AlreadyPostit;

import java.util.List;

/**
 * Created by Administrator on 2017/4/25.
 */

public class AlreadyPostitAdapter extends CommonAdapter<AlreadyPostit> {

    public AlreadyPostitAdapter(Context context, List<AlreadyPostit> mList, int itemLayoutId){
        super(context, mList, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder holder, AlreadyPostit item, int positon) {
        holder.setTextViewText(R.id.text_title,item.getTitle());
        holder.setTextViewText(R.id.text_data,item.getAddtime());
    }

}
