package com.listenergao.mytest.activity;

import android.annotation.SuppressLint;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.listenergao.mytest.R;
import com.listenergao.mytest.requestBean.HomeArticleBean;
import com.listenergao.mytest.requestBean.WxArticleBean;
import com.listenergao.mytest.requestBean.WxArticleDetailBean;
import com.listenergao.mytest.utils.GsonUtils;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * create on 18/11/12
 * RxJava 示例
 *
 * @author listenergao
 */
public class RxJavaSimpleActivity extends BaseActivity {

    private static final String HOME_ARTICLE_CACHE = "homeArticleCache";

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_simple_request_content)
    TextView mTvSimpleRequestContent;
    @BindView(R.id.btn_simple_request)
    Button mBtnSimpleRequest;
    @BindView(R.id.tv_cache_request_content)
    TextView mTvCacheRequestContent;
    @BindView(R.id.btn_cache_request)
    Button mBtnCacheRequest;
    @BindView(R.id.tv_multiple_request_content)
    TextView mTvMultipleRequest;
    @BindView(R.id.btn_multiple_request)
    Button mBtnMultipleRequest;
    @BindView(R.id.tv_interval_content)
    TextView mTvIntervalContent;
    @BindView(R.id.btn_interval)
    Button mBtnInterval;
    @BindView(R.id.btn_interval_range)
    Button mBtnIntervalRange;

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


    @OnClick({R.id.btn_simple_request, R.id.btn_cache_request, R.id.btn_multiple_request, R.id.btn_interval, R.id.btn_interval_range})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_simple_request:
                simpleRequest();
                break;
            case R.id.btn_cache_request:
                cacheAndNetworkRequest();
                break;
            case R.id.btn_multiple_request:
                multipleRequest();
                break;
            case R.id.btn_interval:
                interval();
                break;
            case R.id.btn_interval_range:
                intervalRange();
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

    @SuppressLint("CheckResult")
    private void cacheAndNetworkRequest() {
        Observable<HomeArticleBean> cacheObservable = Observable.create(new ObservableOnSubscribe<HomeArticleBean>() {
            @Override
            public void subscribe(ObservableEmitter<HomeArticleBean> emitter) throws Exception {
                String cacheJson = SPUtils.getInstance().getString(HOME_ARTICLE_CACHE);
                if (TextUtils.isEmpty(cacheJson)) {
                    LogUtils.dTag("gys", "无缓存");
                    emitter.onComplete();
                } else {
                    LogUtils.dTag("gys", "有缓存");
                    emitter.onNext(GsonUtils.fromJson(cacheJson, HomeArticleBean.class));
                    // 无论是否有缓存，都请求网络
                    emitter.onComplete();
                }
            }
        });


        Observable<HomeArticleBean> networkObservable = Observable.create(new ObservableOnSubscribe<HomeArticleBean>() {
            @Override
            public void subscribe(ObservableEmitter<HomeArticleBean> emitter) throws Exception {
                LogUtils.dTag("gys", "执行网络请求");
                Request request = new Request.Builder()
                        .url("http://www.wanandroid.com/article/list/0/json")
                        .build();
                Response response = new OkHttpClient().newCall(request).execute();
                ResponseBody body = response.body();
                if (body != null) {
                    LogUtils.dTag("gys", "body != null ");
                    emitter.onNext(GsonUtils.fromJson(body.string(), HomeArticleBean.class));
                }
            }
        });

        Observable.concat(cacheObservable, networkObservable)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<HomeArticleBean>() {
                    @Override
                    public void accept(HomeArticleBean homeArticleBean) throws Exception {
                        LogUtils.dTag("gys", "处理数据....");
                        SPUtils.getInstance().put(HOME_ARTICLE_CACHE, GsonUtils.toJson(homeArticleBean));
                        mTvCacheRequestContent.setText(homeArticleBean.toString());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mTvCacheRequestContent.setText(throwable.getMessage());
                    }
                });


    }

    @SuppressLint("CheckResult")
    private void multipleRequest() {
        Observable.create(new ObservableOnSubscribe<WxArticleBean>() {
            @Override
            public void subscribe(ObservableEmitter<WxArticleBean> emitter) throws Exception {
                Log.d("gys", "1 thread = " + Thread.currentThread().getName());
                Request request = new Request.Builder()
                        .url("http://wanandroid.com/wxarticle/chapters/json")
                        .build();
                Response response = new OkHttpClient().newCall(request).execute();
                if (response.isSuccessful()) {
                    String json = response.body().string();
                    WxArticleBean wxArticleBean = GsonUtils.fromJson(json, WxArticleBean.class);
                    Log.d("gys", "wxArticleBean = " + wxArticleBean.toString());
                    emitter.onNext(wxArticleBean);
                }
            }
        })       //指定耗时进程
                .subscribeOn(Schedulers.io())
                //指定doOnNext执行线程是主线程
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<WxArticleBean>() {
                    @Override
                    public void accept(WxArticleBean wxArticleBean) throws Exception {
                        Log.d("gys", "2 thread = " + Thread.currentThread().getName());
                        if (wxArticleBean != null && wxArticleBean.getData() != null) {
                            StringBuffer context = new StringBuffer("公众号名称：");
                            for (WxArticleBean.DataBean bean : wxArticleBean.getData()) {
                                context.append(bean.getName() + "，");
                            }
                            mTvMultipleRequest.append(context.toString());
                            mTvMultipleRequest.append("\n");
                        }
                    }
                })
                //指定flatMap执行线程是io线程
                .observeOn(Schedulers.io())
                .flatMap(new Function<WxArticleBean, ObservableSource<WxArticleDetailBean>>() {
                    @Override
                    public ObservableSource<WxArticleDetailBean> apply(WxArticleBean wxArticleBean) throws Exception {
                        Log.d("gys", "3 thread = " + Thread.currentThread().getName());
                        if (wxArticleBean != null && wxArticleBean.getData() != null && wxArticleBean.getData().size() > 0) {
                            String url = "http://wanandroid.com/wxarticle/list/" + wxArticleBean.getData().get(0).getId() + "/1/json";
                            Log.d("gys", "url = " + url);
                            Request request = new Request.Builder()
                                    .url(url)
                                    .build();
                            Response response = new OkHttpClient().newCall(request).execute();
                            String json = response.body().string();
                            WxArticleDetailBean wxArticleDetailBean = GsonUtils.fromJson(json, WxArticleDetailBean.class);
                            return Observable.just(wxArticleDetailBean);
                        }
                        return null;
                    }
                })
                //指定最后观察者在主线程
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<WxArticleDetailBean>() {
                    @Override
                    public void accept(WxArticleDetailBean wxArticleDetailBean) throws Exception {
                        Log.d("gys", "4 thread = " + Thread.currentThread().getName());
                        mTvMultipleRequest.append(wxArticleDetailBean.toString());
                        Log.d("gys", "wxArticleDetailBean = " + wxArticleDetailBean.toString());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d("gys", "error = " + throwable.toString());
                    }
                });


    }

    private Disposable mDisposable;

    private void interval() {
        mDisposable = Flowable.interval(1, TimeUnit.SECONDS)
                .doOnNext(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Log.d("gys", "accept aLong = " + aLong);
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Log.d("gys", "aLong = " + aLong);
                        mTvIntervalContent.setText(aLong.toString());
                    }
                });
    }

    /**
     * 利用 intervalRange 操作符实现获取验证码功能
     */
    private void intervalRange() {
        /**
         * 10s倒计时
         */
        final int count = 11;
        mBtnIntervalRange.setEnabled(false);
        /**
         * start：起始数值
         * count：发射数量
         * initialDelay：延迟执行时间
         * period：发射周期时间
         * unit：时间单位
         */
        mDisposable = Flowable.intervalRange(0, count, 0, 1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Log.d("gys", "accept aLong = " + aLong);
                        mBtnIntervalRange.setText("重新获取（" + (count - 1 - aLong) + "）");
                    }
                })
                .doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        Log.d("gys", "doOnComplete run");
                        mBtnIntervalRange.setEnabled(true);
                        mBtnIntervalRange.setText("获取验证码");
                    }
                })
                .subscribe();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDisposable != null) {
            mDisposable.dispose();
        }
    }
}
