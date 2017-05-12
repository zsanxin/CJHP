package com.android.frame.http;

import android.os.Handler;
import android.widget.Toast;

import com.android.frame.config.BaseUserManger;
import com.android.frame.util.AppManger;
import com.android.frame.util.Toolkit;
import com.orhanobut.logger.Logger;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by admin on 2016/5/9.
 */
public class HttpUtils {
    private Handler handler;

    public HttpUtils() {
        handler = new Handler();
    }

    public void doCall(String url, RequestParams param, final HttpListener listener, final int what) {
        OkHttpClient okHttpClient = OkHttpUtils.getInstance();
        RequestBody requestBody;
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        //post请求不可传输空参数产生一条模拟数据
        if (Toolkit.listIsEmpty(param.getParams())){
            param.addParam("","");
        }
        for (int i = 0; i < param.getParams().size(); i++) {
            String key = param.getParams().get(i).getKey();
            Object value = param.getParams().get(i).getValue();
            if (null==value){
                continue;
            }

            if (value instanceof File){ //文件
                RequestBody body = RequestBody.create(MediaType.parse("application/octet-stream"), (File) value);
                File file = (File) value;
                builder.addFormDataPart(key,file.getName() , body);
            } else {
                builder.addPart(Headers.of("Content-Disposition",
                        "form-data; name=\"" + key + "\""),
                        RequestBody.create(null, (String) value));
            }
        }
        requestBody = builder.build();
        final Request request = new Request.Builder()
                .url(url)
                .header("token", BaseUserManger.getToken())
                .post(requestBody)
                .build();

        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, IOException e) {
                Logger.e(e);
                e.printStackTrace();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.onFailure(call, what);
                    }
                });

            }

            @Override
            public void onResponse(final Call call, final Response response) {
                String json = null;
                try {
                    json = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                    printExLogger(e);
                }
                final String finalJson = json;
                Logger.i("json数据是"+json);

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Logger.json(finalJson);
                            JSONObject object = new JSONObject(finalJson);
                            if (object.getInt("status") == 200) {
                                try {
                                    listener.onSuccess(finalJson, call, response, what);
                                } catch (Exception e) {
                                    Logger.w("onSuccess中代码有误请检查");
//                                    e.printStackTrace();
                                    printExLogger(e);
                                    showToast("网络连接错误");
                                    listener.onParseFail();
                                }
                            } else {
                                try {
                                    listener.onError(finalJson, call, response, what);
                                } catch (Exception e) {
                                    Logger.w("onError中代码有误请检查");
                                    printExLogger(e);
//                                    Logger.e(e);
//                                    e.printStackTrace();
                                    showToast("网络连接错误");
                                    listener.onParseFail();
                                }
                            }
                        } catch (Exception e) {
                            printExLogger(e);
//                            e.printStackTrace();
                            showToast("服务器异常，请重试");
                            listener.onFailure(null, what);
                        }
                    }
                });
            }

        });
    }

    private void showToast(String message) {
        Toast.makeText(AppManger.getInstance().getTopActivity(), message, Toast.LENGTH_SHORT).show();
    }

    public void printExLogger(Exception ex) {
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();
        Logger.i(result);
    }
   }
