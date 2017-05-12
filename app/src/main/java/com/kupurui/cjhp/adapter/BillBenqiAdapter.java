package com.kupurui.cjhp.adapter;

import android.content.Context;

import com.android.frame.adapter.CommonAdapter;
import com.android.frame.adapter.ViewHolder;
import com.kupurui.cjhp.R;
import com.kupurui.cjhp.bean.BillBenqiInfo;

import java.util.List;

/**
 * Created by Administrator on 2017/5/2.
 */

public class BillBenqiAdapter extends CommonAdapter<BillBenqiInfo> {

    public BillBenqiAdapter(Context context, List<BillBenqiInfo> mList, int itemLayoutId){
        super(context, mList, itemLayoutId);
    }
    @Override
    public void convert(ViewHolder holder, BillBenqiInfo item, int positon) {
        holder.setTextViewText(R.id.tv_feiyong_name,item.getYongchu());
        holder.setTextViewText(R.id.tv_jifei_zhouqi,item.getJifeizhouqi());
        holder.setTextViewText(R.id.tv_buhanzengzhi,item.getBuhanshui());
        holder.setTextViewText(R.id.tv_zengzhi,item.getZengzhi());
        holder.setTextViewText(R.id.tv_hansui_total,item.getHanshui());
        holder.setTextViewText(R.id.tv_zuichi,item.getZuichi());
    }
}
