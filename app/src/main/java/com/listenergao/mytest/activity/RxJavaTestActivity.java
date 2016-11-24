package com.listenergao.mytest.activity;

import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;

import com.listenergao.mytest.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RxJavaTestActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.activity_rx_java_test)
    LinearLayout activityRxJavaTest;

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

}
