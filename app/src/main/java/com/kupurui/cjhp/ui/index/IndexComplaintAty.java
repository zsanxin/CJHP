package com.kupurui.cjhp.ui.index;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.frame.ui.BaseActivity;
import com.kupurui.cjhp.R;
import com.kupurui.cjhp.ui.complaint.ComplaintAlreadyTreatmentFgt;
import com.kupurui.cjhp.ui.complaint.ComplaintFgt;
import com.kupurui.cjhp.ui.complaint.ComplaintPendingTreatmentFgt;
import com.kupurui.cjhp.ui.complaint.ComplaintTreatmentFgt;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 投诉建议
 * Created by Administrator on 2017/4/17.
 */

public class IndexComplaintAty extends BaseActivity{

    @Bind(R.id.text_title)
    TextView text_title;
    @Bind(R.id.img_back)
    ImageView img_back;
    @Bind(R.id.complaint_tab1)
    TextView complaint_tab1;
    @Bind(R.id.complaint_tab2)
    TextView complaint_tab2;
    @Bind(R.id.complaint_tab3)
    TextView complaint_tab3;
    @Bind(R.id.complaint_tab4)
    TextView complaint_tab4;

    public ComplaintFgt complaintFgt;//投诉建议
    public ComplaintPendingTreatmentFgt complaintPendingTreatmentFgt;//待处理
    public ComplaintTreatmentFgt complaintTreatmentFgt;//处理中
    public ComplaintAlreadyTreatmentFgt complaintAlreadyTreatmentFgt;//已处理
    /**
     * 用于对Fragment进行管理
     */
    private FragmentManager fragmentManager;


    @Override
    public int getLayoutId() {
        return R.layout.index_complaint_aty;
    }

    @Override
    public void initData() {
        text_title.setText(this.getString(R.string.index_complaint));
        fragmentManager = getFragmentManager();
        // 第一次启动时选中第0个tab
        setTabSelection(0);

    }

    @Override
    public void requestData() {

    }

    @OnClick({R.id.img_back,R.id.complaint_tab1,R.id.complaint_tab2,R.id.complaint_tab3,R.id.complaint_tab4})
    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()){
            case R.id.img_back:
                finish();
                break;
            case R.id.complaint_tab1:
                setTabSelection(0);
                break;
            case R.id.complaint_tab2:
                setTabSelection(1);
                break;
            case R.id.complaint_tab3:
                setTabSelection(2);
                break;
            case R.id.complaint_tab4:
                setTabSelection(3);
                break;
        }
    }

    /**
     * 根据传入的index参数来设置选中的tab页。
     *
     * @param index
     *            每个tab页对应的下标。0表示报事报修，1表示待处理，2表示处理中，3表示已处理。
     */
    public void setTabSelection(int index) {
        // 每次选中之前先清楚掉上次的选中状态
        clearSelection();
        // 开启一个Fragment事务
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
        hideFragments(transaction);
        switch (index) {
            case 0:
                // 当点击了报事报修tab时，改变文字颜色
                complaint_tab1.setTextColor(getResources().getColor(R.color.ae8603b));
                complaintFgt=new ComplaintFgt(); //实例化要添加的Fragment
                transaction.replace(R.id.complaint_frame_content, complaintFgt);    //根据ID指定要添加到的FrameLayout
                break;
            case 1:
                // 当点击了待处理tab时，改变文字颜色
                complaint_tab2.setTextColor(getResources().getColor(R.color.ae8603b));
                complaintPendingTreatmentFgt = new ComplaintPendingTreatmentFgt();
                transaction.replace(R.id.complaint_frame_content, complaintPendingTreatmentFgt);

                break;
            case 2:
                // 当点击了处理中tab时，改变控件的图片和文字颜色
                complaint_tab3.setTextColor(getResources().getColor(R.color.ae8603b));
                complaintTreatmentFgt = new ComplaintTreatmentFgt();
                transaction.replace(R.id.complaint_frame_content, complaintTreatmentFgt);
                break;
            case 3:
                // 当点击了已处理tab时，改变控件的图片和文字颜色
                complaint_tab4.setTextColor(getResources().getColor(R.color.ae8603b));
                complaintAlreadyTreatmentFgt = new ComplaintAlreadyTreatmentFgt();
                transaction.replace(R.id.complaint_frame_content, complaintAlreadyTreatmentFgt);
                break;
        }
        transaction.commit();
    }

    /**
     * 清除掉所有的选中状态。
     */
    private void clearSelection() {
        complaint_tab1.setTextColor(getResources().getColor(R.color.a808080));
        complaint_tab2.setTextColor(getResources().getColor(R.color.a808080));
        complaint_tab3.setTextColor(getResources().getColor(R.color.a808080));
        complaint_tab4.setTextColor(getResources().getColor(R.color.a808080));
    }

    /**
     * 将所有的Fragment都置为隐藏状态。
     *
     * @param transaction
     *            用于对Fragment执行操作的事务
     */
    private void hideFragments(FragmentTransaction transaction) {
        if (complaintFgt != null) {
            transaction.hide(complaintFgt);
        }
        if (complaintPendingTreatmentFgt != null) {
            transaction.hide(complaintPendingTreatmentFgt);
        }
        if (complaintTreatmentFgt != null) {
            transaction.hide(complaintTreatmentFgt);
        }
        if (complaintAlreadyTreatmentFgt != null) {
            transaction.hide(complaintAlreadyTreatmentFgt);
        }
    }

}
