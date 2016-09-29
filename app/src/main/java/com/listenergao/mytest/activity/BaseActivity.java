package com.listenergao.mytest.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by ListenerGao on 2016/6/21.
 * 创建父类的Activity
 */
public abstract class BaseActivity extends AppCompatActivity {
    /**
     * 得到布局文件
     *
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
//        requestWindowFeature(Window.FEATURE_NO_TITLE);  //继承Activity时使用
        if (getLayoutResId() != -1) {
            setContentView(getLayoutResId());
        }
        initView();
        if (savedInstanceState == null) {   //处理当屏幕发生旋转时,Fragment会重建,导致界面出现覆盖的问题.
            initData();
        }
    }


}
