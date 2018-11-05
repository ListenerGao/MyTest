package com.listenergao.mytest.activity;

import android.annotation.SuppressLint;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.listenergao.mytest.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * create on 2018/10/31
 *
 * @author ListenerGao
 */
public class RxJavaOperatorActivity extends BaseActivity {


    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.btn_create)
    Button mBtnCreate;
    @BindView(R.id.tv_create_content)
    TextView mTvCreateContent;
    @BindView(R.id.btn_map)
    Button mBtnMap;
    @BindView(R.id.tv_map_content)
    TextView mTvMapContent;
    @BindView(R.id.btn_zip)
    Button mBtnZip;
    @BindView(R.id.tv_zip_content)
    TextView mTvZipContent;
    @BindView(R.id.btn_concat)
    Button mBtnConcat;
    @BindView(R.id.tv_concat_content)
    TextView mTvConcatContent;
    @BindView(R.id.btn_flat_map)
    Button mBtnFlatMap;
    @BindView(R.id.tv_flat_map_content)
    TextView mTvFlatMapContent;
    @BindView(R.id.btn_concat_map)
    Button mBtnConcatMap;
    @BindView(R.id.tv_concat_map_content)
    TextView mTvConcatMapContent;
    @BindView(R.id.activity_rx_java_test)
    LinearLayout activityRxJavaTest;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_rx_java_operator;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);

        mToolbar.setTitle("RxJava操作符");
        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.btn_create, R.id.btn_map, R.id.btn_zip, R.id.btn_concat, R.id.btn_flat_map, R.id.btn_concat_map})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_create:
                create();
                break;
            case R.id.btn_map:
                map();
                break;
            case R.id.btn_zip:
                zip();
                break;
            case R.id.btn_concat:
                concat();
                break;
            case R.id.btn_flat_map:
                flatMap();
                break;
            case R.id.btn_concat_map:
                concatMap();
                break;
            default:
                break;
        }
    }

    private StringBuffer sb;

    private void create() {
        Log.d("gys", " create.......");
        sb = new StringBuffer();
        // 使用create方式创建一个被观察者（发射器）
        Observable<String> stringObservable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                Log.d("gys", " subscribe1.......");
                sb.append("subscribe1" + "\n");
                emitter.onNext("1");
                Log.d("gys", " subscribe2.......");
                sb.append("subscribe2" + "\n");
                emitter.onNext("2");
                Log.d("gys", " subscribe3.......");
                sb.append("subscribe3" + "\n");
                emitter.onNext("3");
                Log.d("gys", " subscribe4.......");
                sb.append("subscribe4" + "\n");
                emitter.onNext("4");
                emitter.onComplete();
            }
        });

        //订阅观察者
        stringObservable.subscribe(new Observer<String>() {
            Disposable disposable;

            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
                Log.d("gys", " subscribe  d = " + d.isDisposed());
                sb.append("onSubscribe d = " + d.isDisposed() + "\n");
            }

            @Override
            public void onNext(String s) {
                Log.d("gys", " onNext.......s = " + s);
                sb.append("onNext  s = " + s + "\n");
//                if ("2".equals(s)) {
//                    // disposable的返回值可以控制是否接收事件
//                    disposable.dispose();
//                }
            }

            @Override
            public void onError(Throwable e) {
                Log.d("gys", " onError   e = " + e.getMessage());
                sb.append("onError  e = " + e.getMessage() + "\n");
            }

            @Override
            public void onComplete() {
                Log.d("gys", " onComplete.......");
                sb.append("onComplete " + "\n");
                mTvCreateContent.setText(sb.toString());
            }
        });
    }

    @SuppressLint("CheckResult")
    private void map() {
        //map的作用是对发射器发送的每一个事件应用一个函数，是的每一个事件都按照指定的函数去变化，而在 2.x 中它的作用几乎一致。
        sb = new StringBuffer();
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("1");
                emitter.onNext("2");
                emitter.onNext("3");

            }
        }).map(new Function<String, Integer>() {
            @Override
            public Integer apply(String s) throws Exception {
                return Integer.valueOf(s);
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                sb.append("this is result " + integer + "\n");
                mTvMapContent.setText(sb.toString());
            }

        });
    }

    /**
     * zip专用于合并事件，该合并不是连接，而是两两配对，也就意味着，最终配对出的Observable发射事件数目只和少的那个相同
     * <p>
     * zip 组合事件的过程就是分别从发射器 A 和发射器 B 各取出一个事件来组合，并且一个事件只能被使用一次，
     * 组合的顺序是严格按照事件发送的顺序来进行的，所以上面截图中，可以看到，1 永远是和 A 结合的，2 永远是和 B 结合的。
     * <p>
     * 最终接收器收到的事件数量是和发送器发送事件最少的那个发送器的发送事件数目相同，所以如代码所示，D 很孤单，没有人愿意和它交往。
     */
    @SuppressLint("CheckResult")
    private void zip() {
        Observable.zip(getStringObservable(), getIntegerObservable(), new BiFunction<String, Integer, String>() {
            @Override
            public String apply(String s, Integer integer) throws Exception {
                return "this result is " + s + integer;
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.d("gys", "accept   s = " + s);
            }
        });
    }

    private Observable<String> getStringObservable() {
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                Log.d("gys", "subscribe   A");
                emitter.onNext("A");
                Log.d("gys", "subscribe   B");
                emitter.onNext("B");
                Log.d("gys", "subscribe   C");
                Thread.sleep(3000);
                emitter.onNext("C");
                Log.d("gys", "subscribe   D");
                emitter.onNext("D");
                Log.d("gys", "subscribe   Complete");
                emitter.onComplete();
            }
        });
    }

    private Observable<Integer> getIntegerObservable() {
        return Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                Log.d("gys", "subscribe   1");
                emitter.onNext(1);
                Log.d("gys", "subscribe   2");
                emitter.onNext(2);
                Log.d("gys", "subscribe   3");
                Thread.sleep(3000);
                emitter.onNext(3);
                Log.d("gys", "subscribe   Complete");
                emitter.onComplete();
            }
        });
    }

    /**
     * concat将两个单一的发射器连接成一个发射器
     */
    @SuppressLint("CheckResult")
    private void concat() {

        Observable.concat(Observable.just(1, 2, 3), Observable.just(4, 5))
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d("gys", "integer = " + integer);
                    }
                });
    }

    /**
     * flatMap可以把一个发射器 Observable 通过某种方法转换为多个 Observables，
     * 然后再把这些分散的 Observables装进一个单一的发射器 Observable。
     * 但有个需要注意的是，flatMap 并不能保证事件的顺序，如果需要保证，需要用到我们下面的ConcatMap
     */
    @SuppressLint("CheckResult")
    private void flatMap() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
            }
        }).flatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(Integer integer) throws Exception {
                List<String> list = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    list.add("I am value " + integer);
                    Log.d("gys", "i = " + i + "   integer = " + integer);
                }
                int delayTime = (int) (1 + Math.random() * 10);
                Log.d("gys", "delayTime = " + delayTime);
                return Observable.fromIterable(list).delay(delayTime, TimeUnit.MILLISECONDS);

            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.d("gys", "s = " + s);
                    }
                });

    }

    /**
     * concatMap 与 FlatMap 的唯一区别就是 concatMap 保证了顺序
     */
    @SuppressLint("CheckResult")
    private void concatMap() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
            }
        }).concatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(Integer integer) throws Exception {
                List<String> list = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    list.add("I am value " + integer);
                    Log.d("gys", "i = " + i + "   integer = " + integer);
                }
                int delayTime = (int) (1 + Math.random() * 10);
                Log.d("gys", "delayTime = " + delayTime);
                return Observable.fromIterable(list).delay(delayTime, TimeUnit.MILLISECONDS);

            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.d("gys", "s = " + s);
                    }
                });

    }

}
