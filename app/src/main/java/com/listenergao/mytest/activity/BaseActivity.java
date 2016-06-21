package com.listenergao.mytest.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by admin on 2016/6/21.
 */
public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        initData();
    }

    /**
     * 初始化界面
     */
    public void initView() {};


    /**
     * 初始化数据
     */
    public void initData() {};
}
