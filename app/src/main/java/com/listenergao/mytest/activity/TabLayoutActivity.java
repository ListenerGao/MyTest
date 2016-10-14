package com.listenergao.mytest.activity;

import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;

import com.listenergao.mytest.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TabLayoutActivity extends BaseActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.activity_tab_layout)
    LinearLayout activityTabLayout;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_tab_layout;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);

        toolbar.setTitle("TabLayout");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void initData() {

    }

}
