package com.kupurui.cjhp.ui.index;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.frame.ui.BaseActivity;
import com.kupurui.cjhp.R;
import com.kupurui.cjhp.ui.postit.PostitAlreadyTreatmentFgt;
import com.kupurui.cjhp.ui.postit.PostitFgt;
import com.kupurui.cjhp.ui.postit.PostitPendingTreatmentFgt;
import com.kupurui.cjhp.ui.postit.PostitTreatmentFgt;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 报事报修
 * Created by Administrator on 2017/4/17.
 */

public class IndexPostitAty extends BaseActivity {

    @Bind(R.id.text_title)
    TextView text_title;
    @Bind(R.id.img_back)
    ImageView img_back;
    @Bind(R.id.tab_post1)
    TextView tab_post1;
    @Bind(R.id.tab_post2)
    TextView tab_post2;
    @Bind(R.id.tab_post3)
    TextView tab_post3;
    @Bind(R.id.tab_post4)
    TextView tab_post4;

    private PostitFgt postFgt;//报事报修
    public PostitPendingTreatmentFgt postPendingTreatmentFgt;//待处理
    private PostitTreatmentFgt postTreatmentFgt;//处理中
    private PostitAlreadyTreatmentFgt postAlreadyTreatmentFgt;//已处理
    /**
     * 用于对Fragment进行管理
     */
    private FragmentManager fragmentManager;

    @Override
    public int getLayoutId() {
        return R.layout.index_post_aty;
    }

    @Override
    public void initData() {
        text_title.setText(this.getString(R.string.index_postit));
        fragmentManager = getFragmentManager();
        // 第一次启动时选中第0个tab
        setTabSelection(0);
    }

    @Override
    public void requestData() {

    }

    @OnClick({R.id.tab_post1,R.id.tab_post2,R.id.tab_post3,R.id.tab_post4,R.id.img_back})
    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()){
            case R.id.img_back:
                finish();
                break;
            case R.id.tab_post1:
                setTabSelection(0);
                break;
            case R.id.tab_post2:
                setTabSelection(1);
                break;
            case R.id.tab_post3:
                setTabSelection(2);
                break;
            case R.id.tab_post4:
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
                tab_post1.setTextColor(getResources().getColor(R.color.ae8603b));
                postFgt=new PostitFgt(); //实例化要添加的Fragment
                transaction.replace(R.id.frame_content, postFgt);    //根据ID指定要添加到的FrameLayout
                break;
            case 1:
                // 当点击了待处理tab时，改变文字颜色
                tab_post2.setTextColor(getResources().getColor(R.color.ae8603b));
                postPendingTreatmentFgt = new PostitPendingTreatmentFgt();
                transaction.replace(R.id.frame_content, postPendingTreatmentFgt);
                break;
            case 2:
                // 当点击了处理中tab时，改变控件的图片和文字颜色
                tab_post3.setTextColor(getResources().getColor(R.color.ae8603b));
                postTreatmentFgt = new PostitTreatmentFgt();
                transaction.replace(R.id.frame_content, postTreatmentFgt);
                break;
            case 3:
                // 当点击了已处理tab时，改变控件的图片和文字颜色
                tab_post4.setTextColor(getResources().getColor(R.color.ae8603b));
                postAlreadyTreatmentFgt = new PostitAlreadyTreatmentFgt();
                transaction.replace(R.id.frame_content, postAlreadyTreatmentFgt);
                break;
        }
        transaction.commit();
    }

    /**
     * 清除掉所有的选中状态。
     */
    private void clearSelection() {
        tab_post1.setTextColor(getResources().getColor(R.color.a808080));
        tab_post2.setTextColor(getResources().getColor(R.color.a808080));
        tab_post3.setTextColor(getResources().getColor(R.color.a808080));
        tab_post4.setTextColor(getResources().getColor(R.color.a808080));
    }

    /**
     * 将所有的Fragment都置为隐藏状态。
     *
     * @param transaction
     *            用于对Fragment执行操作的事务
     */
    private void hideFragments(FragmentTransaction transaction) {
        if (postFgt != null) {
            transaction.hide(postFgt);
        }
        if (postPendingTreatmentFgt != null) {
            transaction.hide(postPendingTreatmentFgt);
        }
        if (postTreatmentFgt != null) {
            transaction.hide(postTreatmentFgt);
        }
        if (postAlreadyTreatmentFgt != null) {
            transaction.hide(postAlreadyTreatmentFgt);
        }
    }

}
