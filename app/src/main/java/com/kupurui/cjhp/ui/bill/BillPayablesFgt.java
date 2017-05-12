package com.kupurui.cjhp.ui.bill;

import android.widget.TextView;

import com.android.frame.http.HttpListener;
import com.android.frame.ui.BaseFragment;
import com.android.frame.util.AppJsonUtil;
import com.kupurui.cjhp.R;
import com.kupurui.cjhp.http.Order;

import butterknife.Bind;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 应付款
 * Created by Administrator on 2017/4/20.
 */

public class BillPayablesFgt extends BaseFragment implements HttpListener{

    @Bind(R.id.tv_yufukuan)
    TextView tVYufukuan;
    @Bind(R.id.tv_benwang)
    TextView tVBenwang;
    @Bind(R.id.tv_total)
    TextView tVTotal;
    @Bind(R.id.tv_user_name)
    TextView tvUserName;
    @Bind(R.id.tv_month)
    TextView tvMonth;


    @Override
    public int getLayoutId() {
        return R.layout.bill_paybles_fgt;
    }

    @Override
    public void initData() {

    }

    @Override
    public void requestData() {
        showLoadingContentDialog();
        new Order().fukuan(BillDetailedAty.detail,BillDetailedAty.groupid,BillDetailedAty.month,this,1);
    }

    @Override
    public void onSuccess(String result, Call call, Response response, int what) {
        tvUserName.setText(AppJsonUtil.getString(result,"member"));
        tvMonth.setText(AppJsonUtil.getString(result,"month"));
        tVYufukuan.setText(AppJsonUtil.getString(result,"yufukuan"));
        tVBenwang.setText(AppJsonUtil.getString(result,"yingfu"));
        tVTotal.setText(AppJsonUtil.getString(result,"total"));
        super.onSuccess(result, call, response, what);
    }
}
