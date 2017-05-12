package com.android.frame.ui;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.frame.R;
import com.android.frame.http.HttpListener;
import com.android.frame.util.DensityUtils;
import com.android.frame.util.NetWorkUtils;
import com.android.frame.view.dialog.LoadingDialog;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;


/**
 * fragment基类，实现懒加载,显示进度条的时候尽量放在onActivityCreated
 */
public abstract class BaseFragment extends Fragment implements HttpListener {

    /**
     * 请求网络对话弹窗
     */
    private LoadingDialog mLoadingDialog;
    /**
     * 请求网络全屏对话弹窗
     */
    private LinearLayout mLoadingContent;

    /**
     * 无网络全屏对话弹窗
     */
    private View errorView;
    /**
     * 容器布局
     */
    protected FrameLayout content;

    private boolean isPrepared;

    private LayoutInflater mInflater;

    /**
     * 是否初始化请求网络数据
     */
    protected boolean isInitRequestData;

    private boolean isShowToast = true;

    private boolean isAddToActivity;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initPrepare();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mInflater = inflater;


        View fgtView = inflater.inflate(R.layout.frame_base_fragment, container, false);
        content = (FrameLayout) fgtView.findViewById(R.id.content);

        View child = inflater.inflate(getLayoutId(), null, false);
        if (child.getParent() != content) {
            content.addView(child);
        }

        ButterKnife.bind(this, fgtView);
        return fgtView;
    }

    /**
     * 布局文件ID
     *
     * @return
     */
    public abstract int getLayoutId();


    /**
     * view的点击事件
     */
    public void onClick(View view){};


    //===========是否被添加到activity=======================
    public boolean isAddToActivity() {
        return isAddToActivity;
    }

    public void setAddToActivity(boolean addToActivity) {
        isAddToActivity = addToActivity;
    }


    //======================================进度条相关=========================================

    /**
     * 显示网络错误界面
     */
    private void showNetWorkErrorPage() {


        if (errorView != null && errorView.getParent() == content) {
//            content.removeView(errorView);
//            content.addView(errorView);
            errorView.setVisibility(View.GONE);
        } else {

            errorView = mInflater.inflate(R.layout.frame_error_layout, null, false);
            errorView.setClickable(true);
            content.addView(errorView);
            Button btn_error = (Button) errorView.findViewById(R.id.btn_resh);
            btn_error.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //判断网络是否可用
                    if (NetWorkUtils.isNetworkConnected(getActivity())) {

                        requestData();

                    } else {
                        showToast("请检查网络连接");
                    }
                }
            });
        }

    }

    /**
     * 隐藏网络错误界面
     */
    private void hideNetWorkErrorPage() {
        if (errorView != null && errorView.getParent() == content) {
            content.removeView(errorView);
        }

    }

    /**
     * 显示提示信息
     *
     * @param message
     */
    public void showToast(String message) {
        if (isShowToast) {
            Toast.makeText(this.getActivity().getApplicationContext(), message, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 显示进度对话条
     */
    public void showLoadingDialog(String message) {
        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog(getActivity());
            mLoadingDialog.setContentView(R.layout.frame_loading_dialog);
        }
        mLoadingDialog.showLoadingDialog(message);
    }

    /**
     * 显示全屏遮盖的进度条
     *
     * @param spacingTop    距离顶部的距离dp
     * @param spacingBottom 距离底部的距离dp
     */
    public void showLoadingContentDialog(int spacingTop, int spacingBottom) {

        if (mLoadingContent != null && mLoadingContent.getParent() == content) {
            content.removeView(mLoadingContent);
            content.addView(mLoadingContent);

        } else {
            mLoadingContent = (LinearLayout) mInflater.inflate(R.layout.frame_loading_fgt_content_dialog, null, false);

            if (spacingTop >= 0 && spacingBottom >= 0) {

                FrameLayout spacingView = (FrameLayout) mLoadingContent.findViewById(R.id.frame_spacing);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                params.topMargin = DensityUtils.dp2px(getActivity(), spacingTop);
                params.bottomMargin = DensityUtils.dp2px(getActivity(), spacingBottom);
                spacingView.setLayoutParams(params);

            } else if (spacingTop >= 0 && spacingBottom < 0) {
                FrameLayout spacingView = (FrameLayout) mLoadingContent.findViewById(R.id.frame_spacing);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                params.topMargin = DensityUtils.dp2px(getActivity(), spacingTop);
                spacingView.setLayoutParams(params);

            } else if (spacingTop < 0 && spacingBottom >= 0) {
                FrameLayout spacingView = (FrameLayout) mLoadingContent.findViewById(R.id.frame_spacing);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                params.bottomMargin = DensityUtils.dp2px(getActivity(), spacingBottom);
                spacingView.setLayoutParams(params);
            }
            content.addView(mLoadingContent);
        }


    }

    /**
     * 显示全屏遮盖的进度条
     */
    public void showLoadingContentDialog() {

        if (mLoadingContent != null && mLoadingContent.getParent() == content) {
            content.removeView(mLoadingContent);
            content.addView(mLoadingContent);
        } else {
            mLoadingContent = (LinearLayout) mInflater.inflate(R.layout.frame_loading_fgt_content_dialog, null, false);
            content.addView(mLoadingContent);
        }


    }

    /**
     * 隐藏进度条
     */
    public void dismissLoadingContent() {
        if (mLoadingContent != null && mLoadingContent.getParent() == content) {
            content.removeView(mLoadingContent);
        }
    }

    /**
     * 隐藏进度条
     */
    public void dismissLoadingDialog() {
        if (mLoadingDialog != null) {
            //结束掉网络请求
            mLoadingDialog.dismiss();
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
            mLoadingDialog = null;
        }
        ButterKnife.unbind(this);

    }

//================================================懒加载代码=======================================================

    /**
     * 第一次onResume中的调用onUserVisible避免操作与onFirstUserVisible操作重复
     */
    private boolean isFirstResume = true;

    @Override
    public void onResume() {
        super.onResume();
        if (isFirstResume) {
            isFirstResume = false;
            return;
        }
        if (getUserVisibleHint()) {
            onUserVisible();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (getUserVisibleHint()) {
            onUserInvisible();
        }
    }

    private boolean isFirstVisible = true;
    private boolean isFirstInvisible = true;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && !isFirstVisible) {
//            if (isFirstVisible) {
//                isFirstVisible = false;
//                initPrepare();
//            } else {
            onUserVisible();
//            }
        } else {
            if (isFirstInvisible) {
                isFirstInvisible = false;
                onFirstUserInvisible();
            } else {
                onUserInvisible();
            }
        }

        if (isVisibleToUser)
            isFirstInvisible = false;
    }

    /**
     * 初始化准备工作
     */
    public synchronized void initPrepare() {
//        if (isPrepared) {
        onFirstUserVisible();
//        } else {
//            isPrepared = true;
//        }
    }

    /**
     * 第一次fragment可见（进行初始化工作）
     */
    public void onFirstUserVisible() {
        // 设置是否是初始化网络操作
        isInitRequestData = setIsInitRequestData();
        initData();

        initRequsetMethod();


    }

    /**
     * 初始化请求网络
     */
    public void initRequsetMethod() {
        if (isInitRequestData) {
            //判断网络是否可用
            if (NetWorkUtils.isNetworkConnected(getActivity())) {
                requestData();
            } else {
                showNetWorkErrorPage();
            }
        }
    }

    /**
     * 初始化数据
     */
    public abstract void initData();

    /**
     * 请求网络初始化数据
     */
    public abstract void requestData();

    /**
     * 设置是否是初始化网络
     *
     * @return
     */
    public boolean setIsInitRequestData() {
        return true;
    }

    /**
     * fragment可见（切换回来或者onResume）
     */
    public void onUserVisible(){}


    /**
     * 第一次fragment不可见（不建议在此处理事件）
     */
    public void onFirstUserInvisible() {

    }

    /**
     * fragment不可见（切换掉或者onPause）
     */
    public void onUserInvisible() {


    }


    // ============================== 启动Activity ==============================

    public boolean isHasAnimiation() {
        return hasAnimiation;
    }

    public void setHasAnimiation(boolean hasAnimiation) {
        this.hasAnimiation = hasAnimiation;
    }

    private boolean hasAnimiation = true;

    /**
     * 启动一个Activity
     *
     * @param className 将要启动的Activity的类名
     * @param options   传到将要启动Activity的Bundle，不传时为null
     */
    public void startActivity(Class<?> className, Bundle options) {
        Intent intent = new Intent(getActivity(), className);
        if (options != null) {
            intent.putExtras(options);
        }
        startActivity(intent);
        if (hasAnimiation) {
            getActivity().overridePendingTransition(R.anim.slide_right_in,
                    R.anim.slide_left_out);

        }
    }


    /**
     * 启动一个有会返回值的Activity
     *
     * @param className   将要启动的Activity的类名
     * @param options     传到将要启动Activity的Bundle，不传时为null
     * @param requestCode 请求码
     */
    public void startActivityForResult(Class<?> className, Bundle options,
                                       int requestCode) {
        Intent intent = new Intent(getActivity(), className);
        if (options != null) {
            intent.putExtras(options);
        }
        startActivityForResult(intent, requestCode);
        if (hasAnimiation) {
            getActivity().overridePendingTransition(R.anim.slide_right_in,
                    R.anim.slide_left_out);

        }
    }

    // ===============================网络监听======================================
    @Override
    public void onSuccess(String result, Call call, Response response, int what) {
        dismissLoadingDialog();
        dismissLoadingContent();
        hideNetWorkErrorPage();
        isInitRequestData = false;
    }

    @Override
    public void onError(String result, Call call, Response response, int what) {
        dismissLoadingDialog();
        dismissLoadingContent();
        hideNetWorkErrorPage();

        try {
            showToast(new JSONObject(result).getString("message"));
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
        }
    }

    @Override
    public void onFailure(Call call, int what) {
        dismissLoadingDialog();
        dismissLoadingContent();
        showNetWorkErrorPage();
//
//        CrashHandler.getInstance().saveCrashInfo2File(t, "/sdcard/yzy/citytakeds/yzyHttpError/crash/");
    }


    @Override
    public void onParseFail() {
        dismissLoadingDialog();
        dismissLoadingContent();
        if (isInitRequestData) {
            showNetWorkErrorPage();
        }
    }
}