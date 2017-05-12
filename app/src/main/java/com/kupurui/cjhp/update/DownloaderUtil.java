package com.kupurui.cjhp.update;

import android.app.DownloadManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;

import com.kupurui.cjhp.R;
import com.kupurui.cjhp.config.UserManager;


/**
 * Created by admin on 2016/4/25.
 * 文件下载工具类
 */
public class DownloaderUtil {

    private Context context;

    public long reference;

    public DownloaderUtil(Context context) {
        this.context = context;
    }

    public DownloadManager downloadManager;

    public long downLoader(String url) {
        String serviceString = Context.DOWNLOAD_SERVICE;

        downloadManager = (DownloadManager) context.getSystemService(serviceString);
        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);
//        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_ONLY_COMPLETION);
        request.setTitle(context.getResources().getString(R.string.app_name));
        request.setDescription("下载中...");
        reference = downloadManager.enqueue(request);
        UserManager.setAppid(reference);
        return reference;
    }



    /**
     * @return 获得当前版本号
     * @throws Exception
     */
    public static int getVersionCode(Context context) {
        // 获取packagemanager的实例
        PackageManager packageManager = context.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
//            showToast("版本获取失败");
            e.printStackTrace();
        }
        int version = packInfo.versionCode;
        return version;
    }


    /**
     * @return 获得当前版本号
     * @throws Exception
     */
    public static String  getVersionName(Context context) {
        // 获取packagemanager的实例
        PackageManager packageManager = context.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
//            showToast("版本获取失败");
            e.printStackTrace();
        }
        String version = packInfo.versionName;
        return version;
    }

}
