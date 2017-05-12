package com.kupurui.cjhp.ui.notice;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.frame.http.HttpListener;
import com.android.frame.ui.BaseActivity;
import com.android.frame.util.AppJsonUtil;
import com.kupurui.cjhp.R;
import com.kupurui.cjhp.adapter.NoticeAdapter;
import com.kupurui.cjhp.bean.NoticeInfo;
import com.kupurui.cjhp.config.UserManager;
import com.kupurui.cjhp.http.Message;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.loadmore.LoadMoreContainer;
import in.srain.cube.views.ptr.loadmore.LoadMoreHandler;
import in.srain.cube.views.ptr.loadmore.LoadMoreListViewContainer;
import in.srain.cube.views.ptr.util.PtrInitHelper;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 通知
 * Created by Administrator on 2017/4/18.
 */

public class NoticeRedAty extends BaseActivity implements PtrHandler, LoadMoreHandler, HttpListener {

    @Bind(R.id.text_title)
    TextView text_title;
    @Bind(R.id.img_back)
    ImageView img_back;
    @Bind(R.id.listview)
    ListView listview;
    @Bind(R.id.load_more_list_view_container)
    LoadMoreListViewContainer loadMoreListViewContainer;
    @Bind(R.id.ptr_frame)
    PtrFrameLayout ptrFrame;
    @Bind(R.id.ll_empty)
    LinearLayout llEmpty;

    private NoticeAdapter adapter;
    private ArrayList<NoticeInfo> list = new ArrayList<>();
    private int p = 1;
    private String u_id = null;

    @Override
    public int getLayoutId() {
        return R.layout.notice_orange_aty;
    }

    @Override
    public void initData() {
        u_id = UserManager.getUserInfo().getU_id();
        text_title.setText(this.getString(R.string.notice_red));
        PtrInitHelper.initPtr(this, ptrFrame);
        ptrFrame.setPtrHandler(this);
        loadMoreListViewContainer.useDefaultFooter();
        loadMoreListViewContainer.setAutoLoadMore(true);
        loadMoreListViewContainer.loadMoreFinish(false, false);
        loadMoreListViewContainer.setLoadMoreHandler(this);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putString("id",list.get(position).getId());
                startActivity(NoticeOrangeMessageAty.class,bundle);
            }
        });
        listview.setFooterDividersEnabled(false);

        adapter = new NoticeAdapter(this, list, R.layout.notice_listview_item);
        listview.setAdapter(adapter);
        listview.setEmptyView(llEmpty);
    }

    @Override
    public void requestData() {
        showLoadingContent();
        new Message().tongzhi(u_id,this,1);
    }

    @Override
    public void onSuccess(String result, Call call, Response response, int what) {
        switch (what) {
            case 1: //初次加载数据
                p=1;
                list.clear();
                list.addAll(AppJsonUtil.getArrayList(result,NoticeInfo.class));
                adapter.notifyDataSetChanged();
                ptrFrame.refreshComplete();
                break;
        }
        super.onSuccess(result, call, response, what);
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
    public void onLoadMore(LoadMoreContainer loadMoreContainer) {
    }

    @Override
    public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
        return PtrDefaultHandler.checkContentCanBePulledDown(ptrFrame, listview, header);
    }

    @Override
    public void onRefreshBegin(PtrFrameLayout frame) {

    }
}
