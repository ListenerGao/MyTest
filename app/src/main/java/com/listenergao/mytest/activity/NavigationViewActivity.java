package com.listenergao.mytest.activity;

import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;

import com.listenergao.mytest.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NavigationViewActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.activity_navigation_view)
    LinearLayout activityNavigationView;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_navigation_view;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);

        toolbar.setTitle("NavigationView");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void initData() {

    }
}
