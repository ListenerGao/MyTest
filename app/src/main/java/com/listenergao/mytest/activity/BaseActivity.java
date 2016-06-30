package com.listenergao.mytest.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by admin on 2016/6/21.
 */
public abstract class BaseActivity extends AppCompatActivity {
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
        if (getLayoutResId() != -1){
            setContentView(getLayoutResId());
        }
        initView();
        initData();
    }



}
