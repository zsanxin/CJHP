package com.android.frame.ui;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.frame.R;
import com.android.frame.http.HttpListener;
import com.android.frame.util.AppManger;
import com.android.frame.util.DensityUtils;
import com.android.frame.util.NetWorkUtils;
import com.android.frame.view.dialog.LoadingDialog;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;


/**
 * activity的基类
 */
public abstract class BaseActivity extends AppCompatActivity implements HttpListener {

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
     * 系统容器布局
     */
    private FrameLayout content;

    /**
     * 是否初始化请求网络数据
     */
    protected boolean isInitRequestData;

    protected boolean isShowToast = true;

    /**
     * activity是否销毁掉
     */
    protected boolean isDestroy;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppManger.getInstance().addActivity(this);

        // 设置是否是初始化网络操作
        isInitRequestData = setIsInitRequestData();
        //设置为竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(R.layout.base_activity);


        content = (FrameLayout) findViewById(R.id.content);

        if (getLayoutId() != 0) {
            View childview = getLayoutInflater().inflate(getLayoutId(), null, false);
            if (childview.getParent() != content) {
                content.addView(childview);
            }
        }
        ButterKnife.bind(this);

        initData();


        initRequsetMethod();


    }

    /**
     * 设置是否是初始化网络
     *
     * @return
     */
    public boolean setIsInitRequestData() {
        return true;
    }

    /**
     * 布局文件ID
     *
     * @return
     */
    public abstract int getLayoutId();

    /**
     * 初始化数据
     */
    public abstract void initData();

    /**
     * 请求网络初始化数据
     */
    public abstract void requestData();

    /**
     * 初始化请求网络
     */
    public void initRequsetMethod() {

        if (isInitRequestData) {
            //判断网络是否可用
            if (NetWorkUtils.isNetworkConnected(this)) {

                requestData();

            } else {
                showNetWorkErrorPage();
            }
        }

    }

    /**
     * 初始化toolbar
     *
     * @param toolbar
     * @param title
     */
    public void initToolbar(Toolbar toolbar, String title) {
        TextView tvTitle = (TextView) toolbar.getChildAt(toolbar.getChildCount()-1);
        tvTitle.setText(title);
        setSupportActionBar(toolbar);
        toolbar.setTitle("");
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void intoActivity(Object object, int what) {

    }


    public FrameLayout getContent() {
        return content;
    }

    /**
     * view的点击事件
     */
    public void onClick(View view) {

    }


    //======================================进度条相关=========================================

    /**
     * 显示网络错误界面
     */
    private void showNetWorkErrorPage() {


        if (errorView != null && errorView.getParent() == content) {
            content.removeView(errorView);
            content.addView(errorView);
        } else {

            errorView = getLayoutInflater().inflate(R.layout.frame_error_layout, null, false);
            errorView.setClickable(true);
            content.addView(errorView);
            Button btn_error = (Button) errorView.findViewById(R.id.btn_resh);
            btn_error.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //判断网络是否可用
                    if (NetWorkUtils.isNetworkConnected(BaseActivity.this)) {

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
            Toast.makeText(this.getApplicationContext(), message, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 显示进度对话条
     */
    public void showLoadingDialog(String message) {
        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog(this);
            mLoadingDialog.setContentView(R.layout.frame_loading_dialog);
        }
        mLoadingDialog.showLoadingDialog(message);
    }

    /**
     * 显示全屏遮盖的进度条
     *
     * @param spacingTop    距离顶部的距离单位dp
     * @param spacingBottom 距离底部的距离单位dp
     */
    public void showLoadingContent(int spacingTop, int spacingBottom) {

        if (mLoadingContent != null && mLoadingContent.getParent() == content) {
            content.removeView(mLoadingContent);
            content.addView(mLoadingContent);

        } else {
            mLoadingContent = (LinearLayout) getLayoutInflater().inflate(R.layout.frame_loading_content_dialog, null, false);

            if (spacingTop >= 0 && spacingBottom >= 0) {
                FrameLayout spacingView = (FrameLayout) mLoadingContent.findViewById(R.id.frame_spacing);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                params.topMargin = DensityUtils.dp2px(this, spacingTop);
                params.bottomMargin = DensityUtils.dp2px(this, spacingBottom);
                spacingView.setLayoutParams(params);

            } else if (spacingTop >= 0 && spacingBottom < 0) {
                FrameLayout spacingView = (FrameLayout) mLoadingContent.findViewById(R.id.frame_spacing);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                params.topMargin = DensityUtils.dp2px(this, spacingTop);
                spacingView.setLayoutParams(params);

            } else if (spacingTop < 0 && spacingBottom >= 0) {
                FrameLayout spacingView = (FrameLayout) mLoadingContent.findViewById(R.id.frame_spacing);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                params.bottomMargin = DensityUtils.dp2px(this, spacingBottom);
                spacingView.setLayoutParams(params);
            }
            content.addView(mLoadingContent);
        }


    }

    /**
     * 显示全屏遮盖的进度条(toolbar可以显示出来默认56dp)
     */
    public void showLoadingContent() {

        if (mLoadingContent != null && mLoadingContent.getParent() == content) {
            content.removeView(mLoadingContent);
            content.addView(mLoadingContent);

        } else {
            mLoadingContent = (LinearLayout) getLayoutInflater().inflate(R.layout.frame_loading_content_dialog, null, false);
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
    protected void onDestroy() {
        super.onDestroy();
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
            mLoadingDialog = null;
        }
        ButterKnife.unbind(this);
        isDestroy = true;
        AppManger.getInstance().killActivity(this);
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
        Intent intent = new Intent(this, className);
        if (options != null) {
            intent.putExtras(options);
        }
        startActivity(intent);
        if (hasAnimiation) {
            overridePendingTransition(R.anim.slide_right_in,
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
        Intent intent = new Intent(this, className);
        if (options != null) {
            intent.putExtras(options);
        }
        startActivityForResult(intent, requestCode);
        if (hasAnimiation) {
            overridePendingTransition(R.anim.slide_right_in,
                    R.anim.slide_left_out);

        }
    }

    @Override
    public void finish() {
        super.finish();
        if (hasAnimiation) {
            overridePendingTransition(R.anim.slide_left_in,
                    R.anim.slide_right_out);
        }
    }

    // ===============================网络监听======================================
    @Override
    public void onSuccess(String result, Call call, Response response, int what) {
        if (isDestroy)
            return;
        dismissLoadingDialog();
        dismissLoadingContent();
        hideNetWorkErrorPage();
        isInitRequestData = false;
    }

    @Override
    public void onError(String result, Call call, Response response, int what) {
        if (isDestroy)
            return;
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
        if (isDestroy)
            return;
        dismissLoadingDialog();
        dismissLoadingContent();
        showNetWorkErrorPage();
//
//        CrashHandler.getInstance().saveCrashInfo2File(t, "/sdcard/yzy/citytakeds/yzyHttpError/crash/");
    }


    @Override
    public void onParseFail() {
        if (isDestroy)
            return;
        dismissLoadingDialog();
        dismissLoadingContent();
        if (isInitRequestData) {
            showNetWorkErrorPage();
        }
    }

}
