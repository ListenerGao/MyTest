package com.listenergao.mytest.activity;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.listenergao.mytest.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ListenerGao on 2016/10/11.
 * <p>
 * android 6.0新控件测试
 */
public class Android6NewWidget extends BaseActivity implements View.OnClickListener {

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
        Toast.makeText(this, "FloatingActionButton按钮被点击了..", Toast.LENGTH_SHORT).show();
    }
}
