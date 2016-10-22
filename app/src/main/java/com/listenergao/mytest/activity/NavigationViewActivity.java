package com.listenergao.mytest.activity;

import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.jaeger.library.StatusBarUtil;
import com.listenergao.mytest.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NavigationViewActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    @BindView(R.id.navigation)
    NavigationView navigationView;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_navigation_view;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);

        //设置状态栏为全透明。
        StatusBarUtil.setTransparent(this);
        toolbar.setTitle("NavigationView");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            //设置点击打开侧滑菜单的图标。（以下两种方法均可）
            toolbar.setNavigationIcon(R.drawable.ic_drawer_home);
//            actionBar.setHomeAsUpIndicator(R.drawable.ic_drawer_home);
//            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    protected void initData() {

    }
}
