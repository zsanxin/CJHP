package com.kupurui.cjhp.ui.index;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.frame.http.HttpListener;
import com.android.frame.ui.BaseActivity;
import com.android.frame.util.AppJsonUtil;
import com.android.frame.util.SPUtils;
import com.kupurui.cjhp.R;
import com.kupurui.cjhp.adapter.BillAdapter;
import com.kupurui.cjhp.bean.BillInfo;
import com.kupurui.cjhp.http.Order;
import com.kupurui.cjhp.ui.bill.BillDetailedAty;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 我的账单
 * Created by Administrator on 2017/4/17.
 */

public class IndexBillAty extends BaseActivity implements HttpListener {

    @Bind(R.id.text_title)
    TextView text_title;
    @Bind(R.id.img_back)
    ImageView img_back;

    @Bind(R.id.listview)
    ListView listview;

    private BillAdapter adapter;
    private List<BillInfo> list = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.index_bill_aty;
    }

    @Override
    public void initData() {
        text_title.setText(this.getString(R.string.index_bill));

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putString("detail",list.get(position).getDetail());
                bundle.putString("groupid",list.get(position).getGroupid());
                bundle.putString("month",list.get(position).getMonth());
                startActivity(BillDetailedAty.class,bundle);
            }
        });
        adapter = new BillAdapter(this,list,R.layout.index_bill_listview_item);
        listview.setAdapter(adapter);
    }

    @Override
    public void requestData() {
        showLoadingContent();
        new Order().index(new SPUtils("CJH").get("u_id","").toString(),this,1);
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

    @Override
    public void onSuccess(String result, Call call, Response response, int what) {
        switch (what) {
            case 1: //初次加载数据
                list.clear();
                list.addAll(AppJsonUtil.getArrayList(result,BillInfo.class));
                adapter.notifyDataSetChanged();
                break;
        }
        super.onSuccess(result, call, response, what);
    }

}
