package com.listenergao.mytest.activity;

import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;

import com.listenergao.mytest.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AppBarLayoutActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.activity_app_bar_layout)
    LinearLayout activityAppBarLayout;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_app_bar_layout;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);

        toolbar.setTitle("AppBarLayout");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void initData() {

    }

}
