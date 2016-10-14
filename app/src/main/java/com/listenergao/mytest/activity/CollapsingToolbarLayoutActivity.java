package com.listenergao.mytest.activity;

import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;

import com.listenergao.mytest.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CollapsingToolbarLayoutActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.activity_collapsing_toolbar_layout)
    LinearLayout activityCollapsingToolbarLayout;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_collapsing_toolbar_layout;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);

        toolbar.setTitle("CollapsingToolbarLayout");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void initData() {

    }

}
