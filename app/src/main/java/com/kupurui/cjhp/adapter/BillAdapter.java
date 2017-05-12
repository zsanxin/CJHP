package com.kupurui.cjhp.adapter;

import android.content.Context;

import com.android.frame.adapter.CommonAdapter;
import com.android.frame.adapter.ViewHolder;
import com.kupurui.cjhp.R;
import com.kupurui.cjhp.bean.BillInfo;

import java.util.List;

/**
 * Created by Administrator on 2017/5/2.
 */

public class BillAdapter extends CommonAdapter<BillInfo> {

    public BillAdapter(Context context, List<BillInfo> mList, int itemLayoutId){
        super(context, mList, itemLayoutId);
    }
    @Override
    public void convert(ViewHolder holder, BillInfo item, int positon) {
        holder.setTextViewText(R.id.text_name,item.getMember());
        holder.setTextViewText(R.id.text_price,item.getTotal());
        holder.setTextViewText(R.id.text_data,item.getMonth());
    }
}
