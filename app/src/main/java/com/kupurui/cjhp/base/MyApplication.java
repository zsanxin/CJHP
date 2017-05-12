package com.kupurui.cjhp.base;

import com.android.frame.application.BaseApplication;
import com.android.frame.http.OkHttpUtils;
import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.common.internal.Supplier;
import com.facebook.common.util.ByteConstants;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.backends.okhttp3.OkHttpImagePipelineConfigFactory;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.pgyersdk.crash.PgyCrashManager;

import java.io.File;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Administrator on 2017/4/17.
 */

public class MyApplication extends BaseApplication{

    @Override
    public void onCreate() {
        super.onCreate();
        JPushInterface.init(this); //极光初始化
        PgyCrashManager.register(this);
    }

    @Override
    public void initPresco() {
//        super.initPresco();
        //缓存图片基路径
//        OkHtttils.initOkHttpSSL(this);
        final File file=new File("/sdcard/"+getResources().getString(com.android.frame.R.string.app_name)+"/cache/");
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
                .setMaxCacheSizeOnVeryLowDiskSpace(2 * ByteConstants.MB);

//        ImagePipelineConfig.Builder config = ImagePipelineConfig.newBuilder(mContext);
        ImagePipelineConfig.Builder config = OkHttpImagePipelineConfigFactory.newBuilder(this,OkHttpUtils.getInstance());
        config.setMainDiskCacheConfig(diskCacheConfig.build());
        Fresco.initialize(this,config.build());
    }



}
