package com.listenergao.mytest;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.MessageQueue;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.ToastUtils;
import com.listenergao.mytest.databinding.ActivityLeakBinding;
import com.listenergao.mytest.leak.LeakThread;
import com.listenergao.mytest.leak.LeakUtils;
import com.listenergao.mytest.leak.MethodStack;

/**
 * @description: 模拟内存泄漏
 * @date: 2022/11/17 11:25
 * @author: ListenerGao
 */
public class LeakActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityLeakBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLeakBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnStatic.setOnClickListener(this);
        binding.btnVariable.setOnClickListener(this);
        binding.btnThread.setOnClickListener(this);
        binding.btnApplication.setOnClickListener(this);
        binding.btnDump.setOnClickListener(this);

//        getApplication().registerActivityLifecycleCallbacks();
//        getSupportFragmentManager().registerFragmentLifecycleCallbacks(new FragmentManager.FragmentLifecycleCallbacks() {
//            @Override
//            public void onFragmentViewDestroyed(@NonNull FragmentManager fm, @NonNull Fragment f) {
//                super.onFragmentViewDestroyed(fm, f);
//            }
//
//            @Override
//            public void onFragmentDestroyed(@NonNull FragmentManager fm, @NonNull Fragment f) {
//                super.onFragmentDestroyed(fm, f);
//            }
//        }, true);


        Looper.myQueue().addIdleHandler(new MessageQueue.IdleHandler() {
            @Override
            public boolean queueIdle() {
                // 主线程空闲时会执行
                ToastUtils.showLong("主线程空闲了....");
                return false;
            }
        });

        new Thread() {
            @Override
            public void run() {
                // 自线程创建 Handler两种方式：
                // 一：使用主线程的 Looper
                // 二：调用 Looper.prepare()、以及 Looper.loop() 方法
                new Handler(getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {

                    }
                });

                Looper.prepare();
                // 主线程 Looper
                Looper mainLooper = Looper.getMainLooper();
                // 自线程 Looper
                Looper myLooper = Looper.myLooper();
                System.out.println("mainLooper:" + mainLooper + ", myLooper:" + myLooper);


                Looper.loop();
            }
        }.start();

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_static:
                LeakUtils.leakViews.add(v);
                break;
            case R.id.btn_variable:
                MethodStack.startWithTarget(v);
                break;
            case R.id.btn_thread:
                LeakThread thread = new LeakThread();
                thread.leakViews.add(v);
                thread.start();
                break;
            case R.id.btn_application:
                ((BaseApplication) getApplication()).leakViews.add(v);
                break;
//            case R.id.btn_dump:
//                LeakUtils.dump(this);
//                break;
            default:
                break;
        }
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("finalize....");
    }
}
