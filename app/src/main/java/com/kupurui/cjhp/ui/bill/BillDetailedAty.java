package com.kupurui.cjhp.ui.bill;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.frame.ui.BaseActivity;
import com.kupurui.cjhp.R;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 账单明细
 * Created by Administrator on 2017/4/20.
 */

public class BillDetailedAty extends BaseActivity {

    @Bind(R.id.text_title)
    TextView text_title;
    @Bind(R.id.img_back)
    ImageView img_back;

    @Bind(R.id.bill_tab1)
    TextView tVBilltab1;
    @Bind(R.id.bill_tab2)
    TextView tVBilltab2;
    @Bind(R.id.bill_tab3)
    TextView tVBilltab3;


    private BillCurrentCostFgt billCurrentCostFgt;//本期费用
    private BillPastArrearsFgt billPastArrearsFgt;//往期费用
    private BillPayablesFgt billPayablesFgt;//应付款

    public static String detail = null;
    public static String groupid = null;
    public static String month = null;
    /**
     * 用于对Fragment进行管理
     */
    private FragmentManager fragmentManager;

    @Override
    public int getLayoutId() {
        return R.layout.bill_detailed_aty;
    }

    @Override
    public void initData() {
        Bundle bundle = getIntent().getExtras();
        detail = bundle.getString("detail");
        groupid = bundle.getString("groupid");
        month = bundle.getString("month");
        text_title.setText("我的账单-明细");
        fragmentManager = getFragmentManager();
        // 第一次启动时选中第0个tab
        setTabSelection(0);
    }

    @Override
    public void requestData() {

    }

    @OnClick({R.id.img_back,R.id.bill_tab1,R.id.bill_tab2,R.id.bill_tab3})
    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()){
            case R.id.img_back:
                finish();
                break;
            case R.id.bill_tab1:
                setTabSelection(0);
                break;
            case R.id.bill_tab2:
                setTabSelection(1);
                break;
            case R.id.bill_tab3:
                setTabSelection(2);
                break;
        }
    }

    /**
     * 根据传入的index参数来设置选中的tab页。
     *
     * @param index
     *            每个tab页对应的下标。0表示报事报修，1表示待处理，2表示处理中，3表示已处理。
     */
    private void setTabSelection(int index) {
        // 每次选中之前先清楚掉上次的选中状态
        clearSelection();
        // 开启一个Fragment事务
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
        hideFragments(transaction);
        switch (index) {
            case 0:
                // 当点击了报事报修tab时，改变文字颜色
                tVBilltab1.setTextColor(getResources().getColor(R.color.ae8603b));
                billCurrentCostFgt=new BillCurrentCostFgt(); //实例化要添加的Fragment
                transaction.replace(R.id.bill_frame_content, billCurrentCostFgt);    //根据ID指定要添加到的FrameLayout
                break;
            case 1:
                // 当点击了待处理tab时，改变文字颜色
                tVBilltab2.setTextColor(getResources().getColor(R.color.ae8603b));
                billPastArrearsFgt = new BillPastArrearsFgt();
                transaction.replace(R.id.bill_frame_content, billPastArrearsFgt);

                break;
            case 2:
                // 当点击了处理中tab时，改变控件的图片和文字颜色
                tVBilltab3.setTextColor(getResources().getColor(R.color.ae8603b));
                billPayablesFgt = new BillPayablesFgt();
                transaction.replace(R.id.bill_frame_content, billPayablesFgt);
                break;
        }
        transaction.commit();
    }

    /**
     * 清除掉所有的选中状态。
     */
    private void clearSelection() {
        tVBilltab1.setTextColor(getResources().getColor(R.color.a808080));
        tVBilltab2.setTextColor(getResources().getColor(R.color.a808080));
        tVBilltab3.setTextColor(getResources().getColor(R.color.a808080));
    }

    /**
     * 将所有的Fragment都置为隐藏状态。
     *
     * @param transaction
     *            用于对Fragment执行操作的事务
     */
    private void hideFragments(FragmentTransaction transaction) {
        if (billCurrentCostFgt != null) {
            transaction.hide(billCurrentCostFgt);
        }
        if (billPastArrearsFgt != null) {
            transaction.hide(billPastArrearsFgt);
        }
        if (billPayablesFgt != null) {
            transaction.hide(billPayablesFgt);
        }
    }

    //设置listview高度
    public void setListViewHeightBasedOnChildren(ListView listView) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            // 计算子项View 的宽高
            listItem.measure(0, 0);
            // 统计所有子项的总高度
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }

}
