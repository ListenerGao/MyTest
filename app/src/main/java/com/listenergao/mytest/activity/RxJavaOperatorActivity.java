package com.listenergao.mytest.activity;

import android.annotation.SuppressLint;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.listenergao.mytest.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
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
    @BindView(R.id.btn_distinct)
    Button mBtnDistinct;
    @BindView(R.id.btn_filter)
    Button mBtnFilter;
    @BindView(R.id.btn_buffer)
    Button mBtnBuffer;
    @BindView(R.id.btn_timer)
    Button mBtnTimer;
    @BindView(R.id.btn_interval)
    Button mBtnInterval;
    @BindView(R.id.btn_skip)
    Button mBtnSkip;
    @BindView(R.id.btn_take)
    Button mBtnTake;
    @BindView(R.id.btn_just)
    Button mBtnJust;
    @BindView(R.id.btn_single)
    Button mBtnSingle;
    @BindView(R.id.btn_debounce)
    Button mBtnDebounce;
    @BindView(R.id.btn_defer)
    Button mBtnDefer;
    @BindView(R.id.btn_last)
    Button mBtnLast;
    @BindView(R.id.btn_merge)
    Button mBtnMerge;
    @BindView(R.id.btn_reduce)
    Button mBtnReduce;
    @BindView(R.id.btn_scan)
    Button mBtnScan;
    @BindView(R.id.btn_window)
    Button mBtnWindow;
    private Disposable mDisposable;
    @BindView(R.id.btn_do_on_next)
    Button mBtnDoOnNext;

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


    @OnClick({R.id.btn_create, R.id.btn_map, R.id.btn_zip, R.id.btn_concat, R.id.btn_flat_map, R.id.btn_concat_map,
            R.id.btn_distinct, R.id.btn_filter, R.id.btn_buffer, R.id.btn_timer, R.id.btn_interval, R.id.btn_do_on_next,
            R.id.btn_skip, R.id.btn_take, R.id.btn_just, R.id.btn_single, R.id.btn_debounce, R.id.btn_defer, R.id.btn_last,
            R.id.btn_merge, R.id.btn_reduce, R.id.btn_scan, R.id.btn_window})
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
            case R.id.btn_distinct:
                distinct();
                break;
            case R.id.btn_filter:
                filter();
                break;
            case R.id.btn_buffer:
                buffer();
                break;
            case R.id.btn_timer:
                timer();
                break;
            case R.id.btn_interval:
                interval();
                break;
            case R.id.btn_do_on_next:
                doOnNext();
                break;
            case R.id.btn_skip:
                skip();
                break;
            case R.id.btn_take:
                take();
                break;
            case R.id.btn_just:
                just();
                break;
            case R.id.btn_single:
                single();
                break;
            case R.id.btn_debounce:
                debounce();
                break;
            case R.id.btn_defer:
                defer();
                break;
            case R.id.btn_last:
                last();
                break;
            case R.id.btn_merge:
                merge();
                break;
            case R.id.btn_reduce:
                reduce();
                break;
            case R.id.btn_scan:
                scan();
                break;
            case R.id.btn_window:
                window();
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

    /**
     * distinct 去重，sorted 排序
     */
    @SuppressLint("CheckResult")
    private void distinct() {
        Observable.just(1, 4, 7, 2, 3, 4, 5, 8, 5, 6)
                .distinct()
                .sorted()
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d("gys", "accept    integer = " + integer);
                    }
                });
    }

    /**
     * filter 过滤器
     */
    @SuppressLint("CheckResult")
    private void filter() {
        Observable.just(10, 20, 30, 40, 50, 60)
                .filter(new Predicate<Integer>() {
                    @Override
                    public boolean test(Integer integer) throws Exception {
                        return integer >= 20;
                    }
                }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.d("gys", "accept    integer = " + integer);
            }
        });
    }

    /**
     * 我们把 1, 2, 3, 4, 5 依次发射出来，经过 buffer 操作符，其中参数 skip 为 2， count 为 3，而我们的输出 依次是 123，345，5。
     * 显而易见，我们 buffer 的第一个参数是 count，代表最大取值，在事件足够的时候，一般都是取 count 个值，然后每次跳过 skip 个事件。
     */
    @SuppressLint("CheckResult")
    private void buffer() {
        Observable.just(1, 2, 3, 4, 5)
                .buffer(3, 2)
                .subscribe(new Consumer<List<Integer>>() {
                    @Override
                    public void accept(List<Integer> integers) throws Exception {
                        Log.d("gys", "size = " + integers.size());
                        for (Integer integer : integers) {
                            Log.d("gys", "integer = " + integer);
                        }
                    }
                });
    }

    /**
     * timer 定时任务。
     * 注意：timer 默认在新线程，所以需要切换回主线程
     */
    @SuppressLint("CheckResult")
    private void timer() {
        Observable.timer(3, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                //切换到主线程
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Log.d("gys", "aLong = " + aLong);
                        Toast.makeText(RxJavaOperatorActivity.this, "测试", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * interval 用于间隔时间执行某个操作，其接受三个参数，分别是第一次发送延迟，间隔时间，时间单位。
     * 注意：interval 默认在新线程，所以需要切换回主线程
     */
    @SuppressLint("CheckResult")
    private void interval() {
        mDisposable = Observable.interval(2, 2, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Log.d("gys", "aLong = " + aLong);
                    }
                });
    }

    /**
     * doOnNext 应该不算一个操作符，但考虑到其常用性。它的作用是让订阅者在接收到数据之前干点有意思的事情，可以对数据进行操作处理。
     */
    @SuppressLint("CheckResult")
    private void doOnNext() {
        Observable.just(1, 3, 5, 7, 9)
                .doOnNext(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d("gys", "doOnNext  integer = " + integer);
                    }
                }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.d("gys", "accept  integer = " + integer);
            }
        });
    }

    /**
     * skip 很有意思，其实作用就和字面意思一样，接受一个 long 型参数 count ，代表跳过 count 个数目开始接收。
     */
    @SuppressLint("CheckResult")
    private void skip() {
        Observable.just(1, 2, 3, 4, 5)
                .skip(3)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d("gys", "integer = " + integer);
                    }
                });

    }

    /**
     * take，接受一个 long 型参数 count ，代表至多接收 count 个数据。
     */
    @SuppressLint("CheckResult")
    private void take() {
        Observable.just(1, 2, 3, 4, 5)
                .take(2)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d("gys", "integer = " + integer);
                    }
                });
    }

    /**
     * just，没什么好说的，其实在前面各种例子都说明了，就是一个简单的发射器依次调用 onNext() 方法。
     */
    private void just() {

    }

    /**
     * Single 只会接收一个参数，而 SingleObserver 只会调用 onError() 或者 onSuccess()。
     */
    @SuppressLint("CheckResult")
    private void single() {
        Log.d("gys", "random = " + new Random().nextInt());
        Single.just(new Random().nextInt())
                .subscribe(new SingleObserver<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d("gys", "onSubscribe");
                    }

                    @Override
                    public void onSuccess(Integer integer) {
                        Log.d("gys", "onSuccess  integer = " + integer);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("gys", "onError");
                    }
                });
    }

    /**
     * debounce 去除发送频率过快的项，看起来好像没啥用处，但你信我，后面绝对有地方很有用武之地。
     * <p>
     * 代码很清晰，去除发送间隔时间小于 500 毫秒的发射事件，所以 1 和 3 被去掉了。
     */
    @SuppressLint("CheckResult")
    private void debounce() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                Log.d("gys", "subscribe current thread = " + Thread.currentThread().getName());
                emitter.onNext(1);
                Thread.sleep(400);
                emitter.onNext(2);
                Thread.sleep(505);
                emitter.onNext(3);
                Thread.sleep(100);
                emitter.onNext(4);
                Thread.sleep(605);
                emitter.onNext(5);
                Thread.sleep(510);
                emitter.onComplete();

            }
        }).debounce(500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d("gys", "accept current thread = " + Thread.currentThread().getName());
                        Log.d("gys", "accept  integer = " + integer);
                    }
                });
    }

    /**
     * defer 简单地时候就是每次订阅都会创建一个新的 Observable，并且如果没有被订阅，就不会产生新的 Observable。
     */
    private void defer() {
        Observable<Integer> observable = Observable.defer(new Callable<ObservableSource<Integer>>() {
            @Override
            public ObservableSource<Integer> call() throws Exception {
                return Observable.just(1, 2, 3);
            }
        });

        observable.subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d("gys", "onSubscribe   d = " + d.isDisposed());
            }

            @Override
            public void onNext(Integer integer) {
                Log.d("gys", "integer = " + integer);
            }

            @Override
            public void onError(Throwable e) {
                Log.d("gys", "onError = " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.d("gys", "onComplete");
            }
        });
    }

    /**
     * last 操作符仅取出可观察到的最后一个值，或者是满足某些条件的最后一项。
     */
    @SuppressLint("CheckResult")
    private void last() {
        Observable.just(1, 2, 3, 4, 5, 6)
                .last(3)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d("gys", "integer = " + integer);
                    }
                });

    }

    /**
     * merge 顾名思义，熟悉版本控制工具的你一定不会不知道 merge 命令，而在 Rx 操作符中，merge 的作用是把多个 Observable 结合起来，接受可变参数，也支持迭代器集合。
     * 注意它和 concat 的区别在于，不用等到 发射器 A 发送完所有的事件再进行发射器 B 的发送。
     */
    @SuppressLint("CheckResult")
    private void merge() {
        Observable.merge(Observable.just(4, 5), Observable.just(1, 2, 3))
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d("gys", " integer = " + integer);
                    }
                });

    }

    /**
     * reduce 操作符每次用一个方法处理一个值，可以有一个 seed 作为初始值。
     * 代码中，我们中间采用 reduce ，支持一个 function 为两数值相加，所以应该最后的值是：1 + 2 = 3 + 3 = 6 。
     */
    @SuppressLint("CheckResult")
    private void reduce() {
        Observable.just(1, 2, 3)
                .reduce(new BiFunction<Integer, Integer, Integer>() {
                    @Override
                    public Integer apply(Integer integer, Integer integer2) throws Exception {
                        return integer + integer2;
                    }
                }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.d("gys", "sum = " + integer);
            }
        });
    }

    /**
     * scan 操作符作用和上面的 reduce 一致，唯一区别是 reduce 是个只追求结果的坏人，而 scan 会始终如一地把每一个步骤都输出。
     */
    @SuppressLint("CheckResult")
    private void scan() {
        Observable.just(1, 2, 3)
                .scan(new BiFunction<Integer, Integer, Integer>() {
                    @Override
                    public Integer apply(Integer integer, Integer integer2) throws Exception {
                        return integer + integer2;
                    }
                }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.d("gys", "integer = " + integer);
            }
        });
    }

    /**
     * window 按照实际划分窗口，将数据发送给不同的 Observable
     */
    @SuppressLint("CheckResult")
    private void window() {
        Observable.interval(1, TimeUnit.SECONDS)
                .take(6)
                .window(3)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Observable<Long>>() {
                    @Override
                    public void accept(Observable<Long> longObservable) throws Exception {
                        Log.e("gys", "Sub Divide begin...\n");
                        longObservable.subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Consumer<Long>() {
                                    @Override
                                    public void accept(Long aLong) throws Exception {
                                        Log.d("gys", "aLong = " + aLong);
                                    }
                                });
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }
}
