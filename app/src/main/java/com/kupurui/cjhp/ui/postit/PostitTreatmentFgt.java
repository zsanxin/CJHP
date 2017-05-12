package com.kupurui.cjhp.ui.postit;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.android.frame.http.HttpListener;
import com.android.frame.ui.BaseFragment;
import com.android.frame.util.AppJsonUtil;
import com.android.frame.util.Toolkit;
import com.kupurui.cjhp.R;
import com.kupurui.cjhp.adapter.PostitAdapter;
import com.kupurui.cjhp.bean.PostitInfo;
import com.kupurui.cjhp.config.UserManager;
import com.kupurui.cjhp.http.Repair;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
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
 * 处理中
 * Created by Administrator on 2017/4/18.
 */

public class PostitTreatmentFgt extends BaseFragment implements PtrHandler, LoadMoreHandler, HttpListener {
    @Bind(R.id.listview)
    ListView listview;
    @Bind(R.id.load_more_list_view_container)
    LoadMoreListViewContainer loadMoreListViewContainer;
    @Bind(R.id.ptr_frame)
    PtrFrameLayout ptrFrame;
    @Bind(R.id.img_empty)
    ImageView imgEmpty;
    @Bind(R.id.tv_empty)
    TextView tvEmpty;
    @Bind(R.id.ll_empty)
    LinearLayout llEmpty;


    private String u_id = null;
    private int page = 0;

    PostitAdapter adapter;
    private List<PostitInfo> list = new ArrayList<>();


    @Override
    public int getLayoutId() {
        return R.layout.postit_pending_treatment_fgt;
    }

    @Override
    public void initData() {
        u_id = UserManager.getUserInfo().getU_id();
        PtrInitHelper.initPtr(getActivity(), ptrFrame);
        ptrFrame.setPtrHandler(this);

        loadMoreListViewContainer.useDefaultFooter();
        loadMoreListViewContainer.setAutoLoadMore(true);
        loadMoreListViewContainer.loadMoreFinish(false, true);
        loadMoreListViewContainer.setLoadMoreHandler(this);

        listview.setFooterDividersEnabled(false);
        adapter = new PostitAdapter(getActivity(), list, R.layout.postit_pending_treatment_listview_item);
        listview.setAdapter(adapter);
        listview.setEmptyView(llEmpty);
    }

    @Override
    public void requestData() {
        showLoadingContentDialog();
        new Repair().clz(u_id, String.valueOf(page), this, 1);
    }

    @Override
    public void onSuccess(String result, Call call, Response response, int what) {
        switch (what) {
            case 1: //初次加载数据
                if (!(AppJsonUtil.getResultInfo(result).getShow_data().equals("") || AppJsonUtil.getResultInfo(result).getShow_data() == null)) {
                    page = 0;
                    list.clear();
                    list.addAll(JSON.parseArray(AppJsonUtil.getString(result, "list"), PostitInfo.class));
                    adapter.notifyDataSetChanged();
                    ptrFrame.refreshComplete();
                }
                break;
            case 2://上拉加载更多
                if (AppJsonUtil.getResultInfo(result).getShow_data().equals("") || AppJsonUtil.getResultInfo(result).getShow_data() == null) {
                    adapter.notifyDataSetChanged();
                    ptrFrame.refreshComplete();
                    loadMoreListViewContainer.loadMoreFinish(false, false);
                } else {
                    try {
                        List<PostitInfo> moreData = JSON.parseArray(AppJsonUtil.getString(result, "list"), PostitInfo.class);
                        if (Toolkit.listIsEmpty(moreData)) {
                            loadMoreListViewContainer.loadMoreFinish(false, false);
                        } else {
                            page++;
                            list.addAll(moreData);
                            adapter.notifyDataSetChanged();
                            loadMoreListViewContainer.loadMoreFinish(false, true);
                        }
                    } catch (Exception e) {
                        adapter.notifyDataSetChanged();
                        showToast("暂无更多数据");
                    }
                    break;
                }
        }
        super.onSuccess(result, call, response, what);
    }

    @Override
    public void onError(String result, Call call, Response response, int what) {
        if (what == 1) {
            adapter.notifyDataSetChanged();
            ptrFrame.refreshComplete();
            loadMoreListViewContainer.loadMoreFinish(false, true);
        } else if (what == 2) {
            adapter.notifyDataSetChanged();
            ptrFrame.refreshComplete();
            loadMoreListViewContainer.loadMoreFinish(false, true);
        }
        super.onError(result, call, response, what);
    }

    void switchUpData(int what) {
        if (what == 0) {
            new Repair().clz(u_id, "0", this, 1);
        } else if (what == 1) {
            new Repair().clz(u_id, (page + 1) + "", this, 2);
        }
    }

    @Override
    public void onLoadMore(LoadMoreContainer loadMoreContainer) {
        switchUpData(1);
    }

    @Override
    public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
        return PtrDefaultHandler.checkContentCanBePulledDown(ptrFrame, listview, header);
    }

    @Override
    public void onRefreshBegin(PtrFrameLayout frame) {
        switchUpData(0);
    }


}
