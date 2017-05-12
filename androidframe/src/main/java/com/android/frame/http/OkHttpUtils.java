package com.android.frame.http;


import android.app.Application;

import com.android.frame.application.BaseApplication;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateFactory;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import okhttp3.Cache;
import okhttp3.OkHttpClient;


public class OkHttpUtils {
    private static OkHttpClient.Builder singleton;

    public static OkHttpClient getInstance() {
        if (singleton == null) {
            synchronized (OkHttpUtils.class) {
                if (singleton == null) {
                    singleton = new OkHttpClient().newBuilder();
                    File cacheDir = new File(BaseApplication.getApplicationCotext().getCacheDir(), "okhttp/cache");

                    try {
                        singleton.cache(new Cache(cacheDir, 1024 * 1024 * 10));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    singleton.connectTimeout(10, TimeUnit.SECONDS);
                    singleton.readTimeout(10, TimeUnit.SECONDS);
                    singleton.writeTimeout(20, TimeUnit.SECONDS);
                }
            }
        }
        return singleton.build();
    }


    public static void initOkHttpSSL(Application context) {
        if (singleton == null) {
            synchronized (OkHttpUtils.class) {
                if (singleton == null) {
                    singleton = new OkHttpClient().newBuilder();
                    File cacheDir = new File(BaseApplication.getApplicationCotext().getCacheDir(), "okhttp/cache");

                    try {
                        singleton.cache(new Cache(cacheDir, 1024 * 1024 * 10));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    singleton.connectTimeout(10, TimeUnit.SECONDS);
                    singleton.readTimeout(10, TimeUnit.SECONDS);
                    singleton.writeTimeout(20, TimeUnit.SECONDS);

                    try {
                        setCertificates(context.getAssets().open("public.pem"));
                    } catch (IOException e){
                        e.printStackTrace();
                    }

                }
            }
        }
    }


    public static void setCertificates(InputStream... certificates) {
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null);
            int index = 0;
            for (InputStream certificate : certificates) {
                String certificateAlias = Integer.toString(index++);
                keyStore.setCertificateEntry(certificateAlias, certificateFactory.generateCertificate(certificate));

                try {
                    if (certificate != null)
                        certificate.close();
                } catch (IOException e) {
                }
            }

            SSLContext sslContext = SSLContext.getInstance("TLS");

            TrustManagerFactory trustManagerFactory =
                    TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());

            trustManagerFactory.init(keyStore);
            sslContext.init
                    (
                            null,
                            trustManagerFactory.getTrustManagers(),
                            new SecureRandom()
                    );
            singleton.sslSocketFactory(sslContext.getSocketFactory());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
