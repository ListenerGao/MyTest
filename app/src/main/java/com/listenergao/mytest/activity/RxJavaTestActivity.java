package com.listenergao.mytest.activity;

import android.os.CountDownTimer;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.listenergao.mytest.R;
import com.listenergao.mytest.requestApi.RequestApi;
import com.listenergao.mytest.requestBean.LatestMsgBean;
import com.listenergao.mytest.requestBean.ThemeDailyBean;
import com.listenergao.mytest.requestBean.ThemesBean;
import com.orhanobut.logger.Logger;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RxJavaTestActivity extends BaseActivity {

    private final static String TAG = "RxJavaTestActivity";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.activity_rx_java_test)
    LinearLayout activityRxJavaTest;
    @BindView(R.id.btn_start)
    Button btnStart;
    @BindView(R.id.btn_request)
    Button btnRequest;
    @BindView(R.id.btn_both)
    Button btnBoth;
    @BindView(R.id.btn_send)
    Button btnSend;
    @BindView(R.id.tv_show)
    TextView tvShow;
    @BindView(R.id.tv_show_msg)
    TextView tvShowMsg;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_rx_java_test;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);

        toolbar.setTitle("RxJava测试");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    protected void initData() {

    }

    private void start() {
        //创建被观察者(方式一)
        Observable switcher = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext("on");
                e.onNext("on");
                e.onNext("off");
                e.onNext("on");
                e.onComplete();
            }
        });
        //创建被观察者(方式二)
        Observable<String> observable = Observable.just("on", "on", "off", "on");

        //创建被观察者(方式三)
        String[] array = {"on", "on", "off", "on"};
        Observable<String> stringObservable = Observable.fromArray(array);

        //创建观察者
        Observer<String> light = new Observer<String>() {
            Disposable disposable = null;

            @Override
            public void onError(Throwable e) {
                //出现错误时调用这个方法
                Logger.d("onError:" + e.getMessage());
            }

            @Override
            public void onComplete() {
                //被观察者的onCompleted()事件会走到这.
                Logger.d("onComplete:结束观察....");
            }

            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
                Logger.d("onSubscribe执行了....");
                tvShow.append("onSubscribe方法最先执行....disposable = " + disposable + "\n\n");
            }

            @Override
            public void onNext(String s) {
                if (s.equals("off")) {
                    //移除订阅关系(不再接收被观察者发送的消息了...),onComplete()将不会执行.
                    disposable.dispose();
                }
                //处理传递过来的事件
                Logger.d("onNext:" + s);
                tvShow.append("onNext接收的消息:" + s + "\n");
            }
        };

        //订阅
        observable.subscribe(light);
    }

    private void request() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://news-at.zhihu.com/api/4/")
                .addConverterFactory(GsonConverterFactory.create())     //使用Gson解析,将json串转换为Bean对象.
                .build();
        //通过代理的方法,得到Api接口.
        final RequestApi requestApi = retrofit.create(RequestApi.class);
        Call<LatestMsgBean> call = requestApi.getLatestMsg();
        //异步执行Http请求
        call.enqueue(new Callback<LatestMsgBean>() {
            @Override
            public void onResponse(Call<LatestMsgBean> call, Response<LatestMsgBean> response) {
                LatestMsgBean latestMsgBean = response.body();
                Logger.d(latestMsgBean);
            }

            @Override
            public void onFailure(Call<LatestMsgBean> call, Throwable t) {

            }
        });
    }

    private void request2() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://news-at.zhihu.com/api/4/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestApi requestApi = retrofit.create(RequestApi.class);
        Call<ThemeDailyBean> themeDaily = requestApi.getThemeDaily(12);
        themeDaily.enqueue(new Callback<ThemeDailyBean>() {
            @Override
            public void onResponse(Call<ThemeDailyBean> call, Response<ThemeDailyBean> response) {
                if (response.isSuccessful()) {
                    ThemeDailyBean themeDailyBean = response.body();
                    Logger.d(themeDailyBean);
                }
            }

            @Override
            public void onFailure(Call<ThemeDailyBean> call, Throwable t) {

            }
        });
    }

    private void useRxJava_Retrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://news-at.zhihu.com/api/4/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        RequestApi requestApi = retrofit.create(RequestApi.class);
        requestApi.getThemes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ThemesBean>() {
                    @Override
                    public void accept(ThemesBean themesBean) throws Exception {
                        Logger.d(themesBean.toString());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });


//        new Subscriber<ThemesBean>() {
//            @Override
//            public void onSubscribe(Subscription s) {
//                tvShowMsg.append("onSubscribe \n");
//                // Flowable并不是订阅就开始发送数据，而是需等到执行Subscription#request才能开始发送数据。
//                // 当然，使用简化subscribe订阅方法会默认指定Long.MAX_VALUE。
//                s.request(Long.MAX_VALUE);
//                Logger.d("onSubscribe:"+s);
//            }
//
//            @Override
//            public void onNext(ThemesBean themesBean) {
//                Logger.d(themesBean.toString());
//                tvShowMsg.append("onNext:" + themesBean.toString() + "\n\n");
//            }
//
//            @Override
//            public void onError(Throwable t) {
//                tvShowMsg.append("onError \n");
//                Logger.d("onError:");
//                t.printStackTrace();
//            }
//
//            @Override
//            public void onComplete() {
//                tvShowMsg.append("onComplete \n");
//                Logger.d("onComplete:");
//            }
//        }

//        new Observer<ThemesBean>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//                tvShowMsg.append("onSubscribe \n");
//                Logger.d("onSubscribe:");
//            }
//
//            @Override
//            public void onNext(ThemesBean themesBean) {
//                Logger.d(themesBean.toString());
//                tvShowMsg.append("onNext:" + themesBean.toString() + "\n\n");
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                tvShowMsg.append("onError \n");
//                Logger.d("onError:" + e.getMessage());
//            }
//
//            @Override
//            public void onComplete() {
//                tvShowMsg.append("onComplete \n");
//                Logger.d("onComplete:");
//            }
//        }
    }

    /**
     * 发送验证码
     * 使用CountDownTimer实现倒计时功能
     */
    private void sendVerificationCode() {
        new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                btnSend.setText(millisUntilFinished / 1000 + "s可再次发送");
                btnSend.setBackgroundColor(getResources().getColor(R.color.darker_gray));
                btnSend.setEnabled(false);
            }

            @Override
            public void onFinish() {
                btnSend.setEnabled(true);
                btnSend.setText("发送验证码");
                btnSend.setBackgroundColor(getResources().getColor(R.color.color_red));
            }
        }.start();
    }

    /**
     * 发送验证码
     * 使用RxJava实现倒计时功能
     */
    private void sendVerificationCodeWithRxJava() {
        final int count = 60;
        Observable.interval(0, 1, TimeUnit.SECONDS)
                .map(new Function<Long, Long>() {   //转换
                    @Override
                    public Long apply(Long aLong) throws Exception {

                        return count - aLong;
                    }
                })
                .take(count + 1)   //执行60次
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        //执行过程中,设置按钮不可被点击
                        btnSend.setEnabled(false);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        btnSend.setBackgroundColor(getResources().getColor(R.color.color_grayish));
                    }

                    @Override
                    public void onNext(Long value) {
//                        btnSend.setBackgroundColor(getResources().getColor(R.color.color_grayish));
                        btnSend.setText(value + "s 后可再次发送验证码");
                        Logger.d("onNext:" + value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        btnSend.setEnabled(true);
                        btnSend.setBackgroundColor(getResources().getColor(R.color.color_red));
                        btnSend.setText("发送验证码");
                    }
                });
    }

    @OnClick({R.id.btn_start, R.id.btn_request, R.id.btn_both, R.id.btn_send})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_start:
                start();
                break;

            case R.id.btn_request:
                request();
                request2();
                break;

            case R.id.btn_both:
                useRxJava_Retrofit();
                break;

            case R.id.btn_send:
//                sendVerificationCode();
                sendVerificationCodeWithRxJava();
                break;
        }
    }
}
