package com.listenergao.mytest.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.listenergao.mytest.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TabLayoutActivity extends BaseActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.activity_tab_layout)
    LinearLayout activityTabLayout;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.bt_tl_viewpager)
    Button btTabLayoutTest;

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
        //添加8个Tab标签，并设置第一个Tab标签为选中状态.
        tabLayout.addTab(tabLayout.newTab().setText("Tab 1"),true);
        tabLayout.addTab(tabLayout.newTab().setText("Tab 2"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab 3"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab 4"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab 5"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab 6"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab 7"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab 8"));
        //设置Tab的模式为可滑动，当tab标签超过屏幕宽度时，可以滑动.
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.bt_tl_viewpager)
    public void onClick(View view){
        startActivity(new Intent(this,TabLayoutViewPagerActivity.class));
    }

}
