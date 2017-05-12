package com.kupurui.cjhp.ui.bill;

import android.text.TextUtils;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.android.frame.http.HttpListener;
import com.android.frame.ui.BaseFragment;
import com.android.frame.util.AppJsonUtil;
import com.kupurui.cjhp.R;
import com.kupurui.cjhp.adapter.BillBenqiAdapter;
import com.kupurui.cjhp.bean.BillBenqiInfo;
import com.kupurui.cjhp.config.UserManager;
import com.kupurui.cjhp.http.Order;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 往期欠费
 * Created by Administrator on 2017/4/20.
 */

public class BillPastArrearsFgt extends BaseFragment implements HttpListener{

    @Bind(R.id.tv_name)
    TextView tVName;
    @Bind(R.id.tv_order_data)
    TextView tVOrderData;
    @Bind(R.id.tv_buhanzengzhi)
    TextView tVBuhanzengzhi;
    @Bind(R.id.tv_zengzhi)
    TextView tVZengzhi;
    @Bind(R.id.tv_hansui_total)
    TextView tVHansuiTotal;
    @Bind(R.id.listview)
    ListView listView;
    @Bind(R.id.scrollview)
    ScrollView scrollView;

    List<BillBenqiInfo> list = new ArrayList<>();
    BillBenqiAdapter adapter ;
    BillDetailedAty aty;


    @Override
    public int getLayoutId() {
        return R.layout.bill_past_arrears_fgt;
    }

    @Override
    public void initData() {
        adapter = new BillBenqiAdapter(getActivity(),list,R.layout.bill_benqi_listview_item);
        listView.setAdapter(adapter);
        aty = (BillDetailedAty)getActivity();
        scrollView.smoothScrollTo(0,0);
    }

    @Override
    public void requestData() {
        showLoadingContentDialog();
        new Order().qianfei(UserManager.getUserInfo().getU_id(),this,1);
    }

    @Override
    public void onSuccess(String result, Call call, Response response, int what) {
        switch (what){
            case 1:
                String show_data = AppJsonUtil.getString(result,"show_data");
                if (!TextUtils.isEmpty(show_data)){
                    tVName.setText(AppJsonUtil.getString(result,"member"));
                    tVBuhanzengzhi.setText(AppJsonUtil.getString(result,"buhanshui"));
                    tVHansuiTotal.setText(AppJsonUtil.getString(result,"total"));
                    tVZengzhi.setText(AppJsonUtil.getString(result,"zengzhi"));
                    list.addAll(JSON.parseArray(AppJsonUtil.getString(result,"detail"),BillBenqiInfo.class));
                    aty.setListViewHeightBasedOnChildren(listView);
                    adapter.notifyDataSetChanged();
                }
                break;
        }
        super.onSuccess(result, call, response, what);
    }
}
