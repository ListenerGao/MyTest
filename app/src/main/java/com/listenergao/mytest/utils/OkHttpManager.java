package com.listenergao.mytest.utils;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.internal.$Gson$Types;
import com.listenergao.mytest.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by hnthgys on 2016/7/7.
 * 对OkHttp的简单二次封装
 */
public class OkHttpManager {

    private static OkHttpManager mInstance;
    private final OkHttpClient mOkHttpClient;
    private Handler mDelivery;
    private Gson mGson;


    private OkHttpManager(){
        mOkHttpClient = new OkHttpClient();
        mDelivery = new Handler(Looper.getMainLooper());
        mGson = new Gson();

    }

    public static OkHttpManager getInstance(){
        if (mInstance == null){
            synchronized (OkHttpManager.class){
                if (mInstance == null) {
                    mInstance = new OkHttpManager();
                }
            }
        }
        return mInstance;
    }

    /**
     * 同步的get请求
     * @param url
     * @return
     * @throws IOException
     */
    private  Response _getAsyn(String url) throws IOException{
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = mOkHttpClient.newCall(request);
        Response execute = call.execute();
        return execute;

    }

    /**
     * 异步的get请求
     * @param url
     * @param callback
     * @throws IOException
     */
    private void _getAsyn(String url,ResultCallback callback) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        deliveryResult(callback,request);

    }


    public static abstract class ResultCallback<T>{
        Type mType;

        public ResultCallback() {
            mType = getSupperclassTypeParamer(getClass());
        }

        static Type getSupperclassTypeParamer(Class<?> subClass){
            Type superClass = subClass.getGenericSuperclass();
            if (superClass instanceof Class)
                throw new RuntimeException("Missing type parameter.");
            ParameterizedType parameterizedType = (ParameterizedType) superClass;
            return $Gson$Types.canonicalize(parameterizedType.getActualTypeArguments()[0]);
        }



        public abstract void onError(Request request,Exception e);

        public abstract void onSuccess(T response);
    }

    /**
     * 分发网络请求的回调
     * @param callback
     * @param request
     */
    private void deliveryResult(final ResultCallback callback, final Request request){
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                sendFailedStringCallback(request,e,callback);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                try {
                    String string = response.body().string();
                    if (callback.mType == String.class){
                        sendSuccessResultCallback(string,callback);
                    }else {
                        Object o = mGson.fromJson(string, callback.mType);
                        sendSuccessResultCallback(o,callback);
                    }
                }catch (IOException e){
                    sendFailedStringCallback(response.request(),e,callback);
                }catch (com.google.gson.JsonParseException e) {//Json解析的错误
                    sendFailedStringCallback(response.request(),e,callback);
                }
            }
        });
    }

    /**
     * 发送失败的回调
     * @param request
     * @param e
     * @param callback
     */
    private void sendFailedStringCallback(final Request request, final Exception e, final ResultCallback callback){
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                if (callback != null) {
                    callback.onError(request,e);
                }
            }
        });
    }

    /**
     * 发送成功的回调
     * @param object
     * @param callback
     */
    private void sendSuccessResultCallback(final Object object, final ResultCallback callback)
    {
        mDelivery.post(new Runnable()
        {
            @Override
            public void run()
            {
                if (callback != null)
                {
                    callback.onSuccess(object);
                }
            }
        });
    }

    /*********************************对外提供的方法*********************************/
    public static void getAsyn(String url,ResultCallback callback){
        getInstance()._getAsyn(url,callback);
    }

    /**
     * 使用Picasso显示网络图片
     * @param url
     * @param view
     */
    public static void displayImage(String url, ImageView view) {
        if (!TextUtils.isEmpty(url)) {
            Picasso.with(UiUtils.getContext())
                    .load(url)
                    .placeholder(R.drawable.hacker)
                    .error(R.drawable.hacker)
                    .into(view);
        } else {
            view.setImageResource(R.drawable.hacker);
        }
    }

}
