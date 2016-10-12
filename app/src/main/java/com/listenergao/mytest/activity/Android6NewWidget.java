package com.listenergao.mytest.activity;

import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.listenergao.mytest.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ListenerGao on 2016/10/11.
 * <p>
 * android 6.0新控件测试
 */
public class Android6NewWidget extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.activity_android6_new_widget)
    CoordinatorLayout rootLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab_button)
    FloatingActionButton fabButton;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_android6_new_widget;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);

        toolbar.setTitle("Android6.0 新控件");
        setSupportActionBar(toolbar);
        //显示返回箭头
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //设置FloatingActionButton的点击事件
        fabButton.setOnClickListener(this);

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        //得到Snackbar对象
        final Snackbar snackbar = Snackbar.make(rootLayout, "我是Snackbar...", Snackbar.LENGTH_LONG);
        //设置Snackbar背景
        snackbar.getView().setBackgroundResource(R.color.colorPrimary);
        snackbar.show();
        //显示带Action的Snackbar
        snackbar.setAction("取消", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //关闭Snackbar
                snackbar.dismiss();
            }
        });
    }
}
