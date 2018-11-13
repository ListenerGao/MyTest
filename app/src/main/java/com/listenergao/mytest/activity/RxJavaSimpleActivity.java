package com.listenergao.mytest.activity;

import android.annotation.SuppressLint;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.listenergao.mytest.R;
import com.listenergao.mytest.requestBean.WxArticleBean;
import com.listenergao.mytest.utils.GsonUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * create on 18/11/12
 * RxJava 示例
 *
 * @author listenergao
 */
public class RxJavaSimpleActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_simple_request_content)
    TextView mTvSimpleRequestContent;
    @BindView(R.id.btn_simple_request)
    Button mBtnSimpleRequest;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_rx_java_simple;
    }

    @Override
    protected void initView() {

        ButterKnife.bind(this);
        mToolbar.setTitle("RxJava示例");
        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.btn_simple_request})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_simple_request:
                simpleRequest();
                break;
            default:
                break;
        }
    }

    @SuppressLint("CheckResult")
    private void simpleRequest() {
        Observable.create(new ObservableOnSubscribe<Response>() {
            @Override
            public void subscribe(ObservableEmitter<Response> emitter) throws Exception {
                Request request = new Request.Builder()
                        .url("http://wanandroid.com/wxarticle/chapters/json")
                        .build();
                Response response = new OkHttpClient().newCall(request).execute();
                emitter.onNext(response);
            }
        }).map(new Function<Response, WxArticleBean>() {
            @Override
            public WxArticleBean apply(Response response) throws Exception {
                Log.d("gys", "map 线程 = " + Thread.currentThread().getName());
                if (response.isSuccessful()) {
                    String json = response.body().string();
                    Log.d("gys", "json = " + json);
                    if (!TextUtils.isEmpty(json)) {
                        return GsonUtils.fromJson(json, WxArticleBean.class);
                    }
                    Log.d("gys", "json = null 1");
                    return null;
                }
                Log.d("gys", "json = null 2");
                return null;
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<WxArticleBean>() {
                    @Override
                    public void accept(WxArticleBean wxArticleBean) throws Exception {
                        //此处可以缓存数据等操作
                        LogUtils.json("gys", wxArticleBean.toString());
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<WxArticleBean>() {
                    @Override
                    public void accept(WxArticleBean wxArticleBean) throws Exception {
                        mTvSimpleRequestContent.setText(wxArticleBean.toString());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mTvSimpleRequestContent.setText(throwable.getMessage());
                    }
                });
    }
}
