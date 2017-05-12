package com.kupurui.cjhp.adapter;

import android.content.Context;

import com.android.frame.adapter.CommonAdapter;
import com.android.frame.adapter.ViewHolder;
import com.kupurui.cjhp.R;
import com.kupurui.cjhp.bean.AlreadyComplaint;

import java.util.List;

/**
 * Created by Administrator on 2017/4/25.
 */

public class AlreadyComplaintAdapter extends CommonAdapter<AlreadyComplaint> {

    public AlreadyComplaintAdapter(Context context, List<AlreadyComplaint> mList, int itemLayoutId){
        super(context, mList, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder holder, AlreadyComplaint item, int positon) {
        holder.setTextViewText(R.id.text_title,item.getTitle());
        holder.setTextViewText(R.id.text_data,item.getAddtime());
    }

}
