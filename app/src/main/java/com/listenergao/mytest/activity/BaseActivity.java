package com.listenergao.mytest.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

/**
 * Created by ListenerGao on 2016/6/21.
 * 创建父类的Activity
 */
public abstract class BaseActivity extends FragmentActivity {
    /**
     * 得到布局文件
     * @return
     */
    protected abstract int getLayoutResId();

    /**
     * 初始化界面
     */
    protected abstract void initView();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏导航栏
//        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);  //继承AppCompatActivity时使用
        requestWindowFeature(Window.FEATURE_NO_TITLE);  //继承Activity时使用
        if (getLayoutResId() != -1){
            setContentView(getLayoutResId());
        }
        initView();
        initData();
    }



}
