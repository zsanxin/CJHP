package com.kupurui.cjhp.ui.index;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.util.ArraySet;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.frame.ui.BaseActivity;
import com.android.frame.util.AppJsonUtil;
import com.android.frame.util.SPUtils;
import com.kupurui.cjhp.R;
import com.kupurui.cjhp.config.UserManager;
import com.kupurui.cjhp.http.Banben;
import com.kupurui.cjhp.ui.gesture.GestureModifyPasswordAty;
import com.kupurui.cjhp.ui.login.LoginAccountLoginAty;
import com.kupurui.cjhp.ui.mine.MineGesturePasswordAty;
import com.kupurui.cjhp.ui.mine.MineMailboxAty;
import com.kupurui.cjhp.ui.mine.MineModifyPasswordAty;
import com.kupurui.cjhp.update.DownloaderUtil;
import com.kupurui.cjhp.utils.CacheUtil;

import java.io.File;
import java.util.Set;

import butterknife.Bind;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import okhttp3.Call;
import okhttp3.Response;

import static android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP;
import static com.kupurui.cjhp.R.id.rlayout_bill;

/**
 * 主页
 * Created by Administrator on 2017/4/17.
 */

public class IndexAty extends BaseActivity {

    @Bind(R.id.img_menu)
    ImageView imgMenu;
    @Bind(R.id.rlayout_notice)
    RelativeLayout rLayout_notice;
    @Bind(rlayout_bill)
    RelativeLayout rLayout_bill;
    @Bind(R.id.rlayout_postit)
    RelativeLayout rLayout_postit;
    @Bind(R.id.rlayout_complaint)
    RelativeLayout rLayout_complaint;
    @Bind(R.id.drawerlayout)
    DrawerLayout drawerLayout;

    @Bind(R.id.tv_id_num)
    TextView tVIdNum;
    @Bind(R.id.layout_modify_pwd)
    LinearLayout layoutModifyPassword;
    @Bind(R.id.layout_mailbox)
    LinearLayout layoutMailbox;
    @Bind(R.id.layout_gesture_pwd)
    LinearLayout layoutGesturePassword;
    @Bind(R.id.layout_updata)
    LinearLayout layoutUpdata;
    @Bind(R.id.layout_clear_cache)
    LinearLayout layoutClearCache;
    @Bind(R.id.layout_exit)
    LinearLayout layoutExit;
    @Bind(R.id.img_modify_password_icon)
    ImageView iVModifyPasswordIcon;
    @Bind(R.id.img_mailbox_icon)
    ImageView iVMailboxIcon;
    @Bind(R.id.img_gesture_password_icon)
    ImageView iVGesturePasswordIcon;
    @Bind(R.id.img_updata_icon)
    ImageView iVUpdataIcon;
    @Bind(R.id.img_clear_cache_icon)
    ImageView iVClearCacheIcon;
    @Bind(R.id.img_exit_icon)
    ImageView iVExitIcon;

    @Bind(R.id.text_modify_password_icon)
    TextView tVModifyPasswordIcon;
    @Bind(R.id.text_mailbox_icon)
    TextView tVMailboxIcon;
    @Bind(R.id.text_gesture_password_icon)
    TextView tVGesturePasswordIcon;
    @Bind(R.id.text_updata_icon)
    TextView tVUpdataIcon;
    @Bind(R.id.text_clear_cache_icon)
    TextView tVClearCacheIcon;
    @Bind(R.id.text_exit_icon)
    TextView tVExitIcon;
    @Bind(R.id.tv_versionName)
    TextView tvVersionName;
    @Bind(R.id.tv_cache_size)
    TextView tvCacheSize;

    private AlertDialog myDialog = null;

    private String shoushi = null;
    private String id = null;
    private long exitTime = 0;

    @Override
    public int getLayoutId() {
        return R.layout.index_aty;
    }

    @Override
    public void initData() {

        Set<String> tag = new ArraySet<>();
        tag.add(UserManager.getUserInfo().getC_id());
        JPushInterface.setAliasAndTags(this, UserManager.getUserInfo().getU_id(), tag, new TagAliasCallback() {
            @Override
            public void gotResult(int i, String s, Set<String> set) {

            }
        });
        //设置版本名
        tvVersionName.setText("当前版本:V"+DownloaderUtil.getVersionName(this));
        //检查缓存大小,获取存储卡中的绝对路径+包名得到sp中或者sqlite中的文件大小
        try {
            String cacheSize= CacheUtil.getFormatSize(Double.valueOf(CacheUtil.getFolderSize(new File(Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+"com.kupurui.cjhp"+File.separator+"cache"))));
            tvCacheSize.setText(cacheSize);
        } catch (Exception e) {
            e.printStackTrace();
        }

        shoushi = new SPUtils("CJH").get("shoushi", "").toString();
        id = new SPUtils("CJH").get("u_id", "").toString();
        if (!TextUtils.isEmpty(id)) {
            tVIdNum.setText(id);
        }
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED, Gravity.LEFT);
        drawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                rLayout_notice.setClickable(false);
                rLayout_bill.setClickable(false);
                rLayout_postit.setClickable(false);
                rLayout_complaint.setClickable(false);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                rLayout_notice.setClickable(true);
                rLayout_bill.setClickable(true);
                rLayout_postit.setClickable(true);
                rLayout_complaint.setClickable(true);
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }

    @Override
    public void requestData() {

    }

    @OnClick({R.id.img_menu, R.id.rlayout_notice, rlayout_bill, R.id.rlayout_postit, R.id.rlayout_complaint,
            R.id.layout_modify_pwd, R.id.layout_mailbox, R.id.layout_gesture_pwd, R.id.layout_updata, R.id.layout_clear_cache, R.id.layout_exit})
    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.img_menu:
                drawerLayout.openDrawer(Gravity.LEFT);
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED,
                        Gravity.LEFT);
                break;
            case R.id.rlayout_notice:
                startActivity(IndexNoticeAty.class, null);
                break;
            case rlayout_bill:
                startActivity(IndexBillAty.class, null);
                break;
            case R.id.rlayout_postit:
                startActivity(IndexPostitAty.class, null);
                break;
            case R.id.rlayout_complaint:
                startActivity(IndexComplaintAty.class, null);
                break;
            case R.id.layout_modify_pwd:
                //修改密码
                selection(0);
                startActivity(MineModifyPasswordAty.class, null);
                break;
            case R.id.layout_mailbox:
                //密保邮箱
                selection(1);
                startActivity(MineMailboxAty.class, null);
                break;
            case R.id.layout_gesture_pwd:
                //手势密码
                selection(2);
                if (shoushi.equals("0")) {
                    startActivity(MineGesturePasswordAty.class, null);
                } else {
                    startActivity(GestureModifyPasswordAty.class, null);
                }
                break;
            case R.id.layout_updata:
                //检查更新
                selection(3);
                checkVersion();
                break;
            case R.id.layout_clear_cache:
                //清楚缓存
                selection(4);
                showLoadingDialog("清理中");
                Handler handler=new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        CacheUtil.clearAllCache(getApplicationContext());
                        dismissLoadingDialog();
                        showToast("清理成功");
                    }
                },2000);
                break;
            case R.id.layout_exit:
                //退出登录
                selection(5);
                showdialog();
                break;
        }
    }

    /**
     * 检查版本更新
     */
    private void checkVersion() {
        showLoadingDialog("");
        new Banben().index(DownloaderUtil.getVersionCode(this),this,1);
        Log.e("code",String.valueOf(DownloaderUtil.getVersionCode(this)));
    }

    private void showdialog() {
        myDialog = new AlertDialog.Builder(IndexAty.this).create();
        myDialog.show();
        myDialog.getWindow().setContentView(R.layout.mine_exit_login_dialog);

        myDialog.getWindow().findViewById(R.id.btn_cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //取消
                myDialog.dismiss();
            }
        });
        myDialog.getWindow().findViewById(R.id.btn_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //确认
                myDialog.dismiss();
                Intent intent = new Intent(getApplicationContext(), LoginAccountLoginAty.class);
                intent.addFlags(FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            }
        });
    }

    private void selection(int i) {
        switch (i) {
            case 0:
                layoutModifyPassword.setBackgroundColor(getResources().getColor(R.color.ec9000));
                layoutMailbox.setBackgroundColor(getResources().getColor(R.color.white));
                layoutGesturePassword.setBackgroundColor(getResources().getColor(R.color.white));
                layoutUpdata.setBackgroundColor(getResources().getColor(R.color.white));
                layoutClearCache.setBackgroundColor(getResources().getColor(R.color.white));
                layoutExit.setBackgroundColor(getResources().getColor(R.color.white));
                iVModifyPasswordIcon.setBackgroundResource(R.drawable.img_modify_password_white);
                iVMailboxIcon.setBackgroundResource(R.drawable.img_mail_orange);
                iVGesturePasswordIcon.setBackgroundResource(R.drawable.img_gesture_pwd_orange);
                iVUpdataIcon.setBackgroundResource(R.drawable.img_updata_orange);
                iVClearCacheIcon.setBackgroundResource(R.drawable.img_clear_cache_orange);
                iVExitIcon.setBackgroundResource(R.drawable.img_exit_orange);
                tVModifyPasswordIcon.setTextColor(getResources().getColor(R.color.white));
                tVMailboxIcon.setTextColor(getResources().getColor(R.color.black));
                tVGesturePasswordIcon.setTextColor(getResources().getColor(R.color.black));
                tVUpdataIcon.setTextColor(getResources().getColor(R.color.black));
                tVClearCacheIcon.setTextColor(getResources().getColor(R.color.black));
                tVExitIcon.setTextColor(getResources().getColor(R.color.black));
                break;
            case 1:
                layoutModifyPassword.setBackgroundColor(getResources().getColor(R.color.white));
                layoutMailbox.setBackgroundColor(getResources().getColor(R.color.ec9000));
                layoutGesturePassword.setBackgroundColor(getResources().getColor(R.color.white));
                layoutUpdata.setBackgroundColor(getResources().getColor(R.color.white));
                layoutClearCache.setBackgroundColor(getResources().getColor(R.color.white));
                layoutExit.setBackgroundColor(getResources().getColor(R.color.white));
                iVModifyPasswordIcon.setBackgroundResource(R.drawable.img_modify_password_orange);
                iVMailboxIcon.setBackgroundResource(R.drawable.img_mail_white);
                iVGesturePasswordIcon.setBackgroundResource(R.drawable.img_gesture_pwd_orange);
                iVUpdataIcon.setBackgroundResource(R.drawable.img_updata_orange);
                iVClearCacheIcon.setBackgroundResource(R.drawable.img_clear_cache_orange);
                iVExitIcon.setBackgroundResource(R.drawable.img_exit_orange);
                tVModifyPasswordIcon.setTextColor(getResources().getColor(R.color.black));
                tVMailboxIcon.setTextColor(getResources().getColor(R.color.white));
                tVGesturePasswordIcon.setTextColor(getResources().getColor(R.color.black));
                tVUpdataIcon.setTextColor(getResources().getColor(R.color.black));
                tVClearCacheIcon.setTextColor(getResources().getColor(R.color.black));
                tVExitIcon.setTextColor(getResources().getColor(R.color.black));
                break;
            case 2:
                layoutModifyPassword.setBackgroundColor(getResources().getColor(R.color.white));
                layoutMailbox.setBackgroundColor(getResources().getColor(R.color.white));
                layoutGesturePassword.setBackgroundColor(getResources().getColor(R.color.ec9000));
                layoutUpdata.setBackgroundColor(getResources().getColor(R.color.white));
                layoutClearCache.setBackgroundColor(getResources().getColor(R.color.white));
                layoutExit.setBackgroundColor(getResources().getColor(R.color.white));
                iVModifyPasswordIcon.setBackgroundResource(R.drawable.img_modify_password_orange);
                iVMailboxIcon.setBackgroundResource(R.drawable.img_mail_orange);
                iVGesturePasswordIcon.setBackgroundResource(R.drawable.img_gesture_pwd_white);
                iVUpdataIcon.setBackgroundResource(R.drawable.img_updata_orange);
                iVClearCacheIcon.setBackgroundResource(R.drawable.img_clear_cache_orange);
                iVExitIcon.setBackgroundResource(R.drawable.img_exit_orange);
                tVModifyPasswordIcon.setTextColor(getResources().getColor(R.color.black));
                tVMailboxIcon.setTextColor(getResources().getColor(R.color.black));
                tVGesturePasswordIcon.setTextColor(getResources().getColor(R.color.white));
                tVUpdataIcon.setTextColor(getResources().getColor(R.color.black));
                tVClearCacheIcon.setTextColor(getResources().getColor(R.color.black));
                tVExitIcon.setTextColor(getResources().getColor(R.color.black));
                break;
            case 3:
                layoutModifyPassword.setBackgroundColor(getResources().getColor(R.color.white));
                layoutMailbox.setBackgroundColor(getResources().getColor(R.color.white));
                layoutGesturePassword.setBackgroundColor(getResources().getColor(R.color.white));
                layoutUpdata.setBackgroundColor(getResources().getColor(R.color.ec9000));
                layoutClearCache.setBackgroundColor(getResources().getColor(R.color.white));
                layoutExit.setBackgroundColor(getResources().getColor(R.color.white));
                iVModifyPasswordIcon.setBackgroundResource(R.drawable.img_modify_password_orange);
                iVMailboxIcon.setBackgroundResource(R.drawable.img_mail_orange);
                iVGesturePasswordIcon.setBackgroundResource(R.drawable.img_gesture_pwd_orange);
                iVUpdataIcon.setBackgroundResource(R.drawable.img_updata_white);
                iVClearCacheIcon.setBackgroundResource(R.drawable.img_clear_cache_orange);
                iVExitIcon.setBackgroundResource(R.drawable.img_exit_orange);
                tVModifyPasswordIcon.setTextColor(getResources().getColor(R.color.black));
                tVMailboxIcon.setTextColor(getResources().getColor(R.color.black));
                tVGesturePasswordIcon.setTextColor(getResources().getColor(R.color.black));
                tVUpdataIcon.setTextColor(getResources().getColor(R.color.white));
                tVClearCacheIcon.setTextColor(getResources().getColor(R.color.black));
                tVExitIcon.setTextColor(getResources().getColor(R.color.black));
                break;
            case 4:
                layoutModifyPassword.setBackgroundColor(getResources().getColor(R.color.white));
                layoutMailbox.setBackgroundColor(getResources().getColor(R.color.white));
                layoutGesturePassword.setBackgroundColor(getResources().getColor(R.color.white));
                layoutUpdata.setBackgroundColor(getResources().getColor(R.color.white));
                layoutClearCache.setBackgroundColor(getResources().getColor(R.color.ec9000));
                layoutExit.setBackgroundColor(getResources().getColor(R.color.white));
                iVModifyPasswordIcon.setBackgroundResource(R.drawable.img_modify_password_orange);
                iVMailboxIcon.setBackgroundResource(R.drawable.img_mail_orange);
                iVGesturePasswordIcon.setBackgroundResource(R.drawable.img_gesture_pwd_orange);
                iVUpdataIcon.setBackgroundResource(R.drawable.img_updata_orange);
                iVClearCacheIcon.setBackgroundResource(R.drawable.img_clear_cache_white);
                iVExitIcon.setBackgroundResource(R.drawable.img_exit_orange);
                tVModifyPasswordIcon.setTextColor(getResources().getColor(R.color.black));
                tVMailboxIcon.setTextColor(getResources().getColor(R.color.black));
                tVGesturePasswordIcon.setTextColor(getResources().getColor(R.color.black));
                tVUpdataIcon.setTextColor(getResources().getColor(R.color.black));
                tVClearCacheIcon.setTextColor(getResources().getColor(R.color.white));
                tVExitIcon.setTextColor(getResources().getColor(R.color.black));
                break;
            case 5:
                layoutModifyPassword.setBackgroundColor(getResources().getColor(R.color.white));
                layoutMailbox.setBackgroundColor(getResources().getColor(R.color.white));
                layoutGesturePassword.setBackgroundColor(getResources().getColor(R.color.white));
                layoutUpdata.setBackgroundColor(getResources().getColor(R.color.white));
                layoutClearCache.setBackgroundColor(getResources().getColor(R.color.white));
                layoutExit.setBackgroundColor(getResources().getColor(R.color.ec9000));
                iVModifyPasswordIcon.setBackgroundResource(R.drawable.img_modify_password_orange);
                iVMailboxIcon.setBackgroundResource(R.drawable.img_mail_orange);
                iVGesturePasswordIcon.setBackgroundResource(R.drawable.img_gesture_pwd_orange);
                iVUpdataIcon.setBackgroundResource(R.drawable.img_updata_orange);
                iVClearCacheIcon.setBackgroundResource(R.drawable.img_clear_cache_orange);
                iVExitIcon.setBackgroundResource(R.drawable.img_exit_white);
                tVModifyPasswordIcon.setTextColor(getResources().getColor(R.color.black));
                tVMailboxIcon.setTextColor(getResources().getColor(R.color.black));
                tVGesturePasswordIcon.setTextColor(getResources().getColor(R.color.black));
                tVUpdataIcon.setTextColor(getResources().getColor(R.color.black));
                tVClearCacheIcon.setTextColor(getResources().getColor(R.color.black));
                tVExitIcon.setTextColor(getResources().getColor(R.color.white));
                break;
        }
    }


    @Override
    public void onSuccess(final String result, Call call, Response response, int what) {
        if (what == 1) {
            String status = AppJsonUtil.getString(result, "status");
            if (status.equals("0")) {
                showToast("已是最新版本!");
            } else {
                final String versionCode = AppJsonUtil.getString(result, "version");
                if (UserManager.getIgnoreVersion().equals(versionCode)) {
                    return;
                }
                if (AppJsonUtil.getString(result, "is_must").equals("0")) {
                    new android.app.AlertDialog.Builder(this).setTitle("有新的版本").setMessage(Html.fromHtml(AppJsonUtil.getString(result, "content"))).setNegativeButton("以后再说", null).setPositiveButton("更新", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            DownloaderUtil downloaderUtil = new DownloaderUtil(IndexAty.this);
                            downloaderUtil.downLoader(AppJsonUtil.getString(result, "appurl"));
                        }
                    }).setNeutralButton("忽略该版本", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            UserManager.setIgnoreVersion(versionCode);
                        }
                    }).create().show();
                } else {
                    new android.app.AlertDialog.Builder(this).setTitle("有新的版本").setMessage(Html.fromHtml(AppJsonUtil.getString(result, "content"))).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    }).setPositiveButton("更新", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            DownloaderUtil downloaderUtil = new DownloaderUtil(IndexAty.this);
                            downloaderUtil.downLoader(AppJsonUtil.getString(result, "appurl"));
                        }
                    }).show();
                }
            }
        }
        super.onSuccess(result, call, response, what);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if((System.currentTimeMillis()-exitTime) > 2000){
                showToast("再按一次退出程序");
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
