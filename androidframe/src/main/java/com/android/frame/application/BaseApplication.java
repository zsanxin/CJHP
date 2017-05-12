package com.android.frame.application;

import android.app.Application;
import android.content.Context;

import com.android.frame.R;
import com.android.frame.util.CrashHandler;
import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.common.internal.Supplier;
import com.facebook.common.util.ByteConstants;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.producers.NetworkFetcher;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;

import java.io.File;


/**
 *  开发使用的application
 *  注意需要加以下权限
 *
 <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
 <uses-permission android:name="android.permission.INTERNET" />
 <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
 <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
 *
 */
public class BaseApplication extends Application {

    public static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
//        //初始化异常捕捉
//        CrashHandler.getInstance().init(this);
        //初始化图片加载框架
        initPresco();
        //初始化log工具
        Logger.init("result")               // default tag : PRETTYLOGGER or use just init()
                .setMethodCount(3)            // default 2
                .hideThreadInfo()             // default it is shown
                .setLogLevel(LogLevel.FULL);  // default : LogLevel.FULL

    }

    public void initPresco() {
        //缓存图片基路径

        final File file=new File("/sdcard/"+getResources().getString(R.string.app_name)+"/cache/");
        if (!file.exists()){
            file.mkdirs();
        }
        DiskCacheConfig.Builder diskCacheConfig = DiskCacheConfig.newBuilder(mContext)
                .setBaseDirectoryPathSupplier(new Supplier<File>() {
                    @Override
                    public File get() {
                        return file;
                    }
                })
                .setBaseDirectoryName("image_cache")
                .setMaxCacheSize(40 * ByteConstants.MB)
                .setMaxCacheSizeOnLowDiskSpace(10 * ByteConstants.MB)
                .setMaxCacheSizeOnVeryLowDiskSpace(2 * ByteConstants.MB)
                ;
        ImagePipelineConfig.Builder config = ImagePipelineConfig.newBuilder(mContext);
        config.setMainDiskCacheConfig(diskCacheConfig.build());
        Fresco.initialize(this,config.build());
    }


    public static Context getApplicationCotext() {

        return mContext.getApplicationContext();

    }
}
