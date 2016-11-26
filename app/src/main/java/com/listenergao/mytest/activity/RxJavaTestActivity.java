package com.listenergao.mytest.activity;

import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.listenergao.mytest.R;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class RxJavaTestActivity extends BaseActivity {

    private final static String TAG = "RxJavaTestActivity";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.activity_rx_java_test)
    LinearLayout activityRxJavaTest;
    @BindView(R.id.btn_start)
    Button btnStart;
    @BindView(R.id.tv_show)
    TextView tvShow;

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
        Observable<String> observable= Observable.just("on","on","off","on");

        //创建被观察者(方式三)
        String[] array = {"on","on","off","on"};
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
                tvShow.append("onSubscribe方法最先执行....disposable = " + disposable +"\n\n");
            }

            @Override
            public void onNext(String s) {
                if (s.equals("off")) {
                    //移除订阅关系(不再接收被观察者发送的消息了...),onComplete()将不会执行.
                    disposable.dispose();
                }
                //处理传递过来的事件
                Logger.d("onNext:" + s);
                tvShow.append("onNext接收的消息:"+s+"\n");
            }
        };

        //订阅
        observable.subscribe(light);
    }

    @OnClick(R.id.btn_start)
    public void onClick() {
        start();
    }
}
