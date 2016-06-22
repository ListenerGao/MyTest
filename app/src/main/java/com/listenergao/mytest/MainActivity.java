package com.listenergao.mytest;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

import com.listenergao.mytest.activity.BaseActivity;
import com.listenergao.mytest.fragment.MainFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {
    /**
     * 主页面的帧布局
     */
    @BindView(R.id.content_frame)
    private FrameLayout content_frame;

    private static final String TAG_LEFT_MENU = "TAG_LEFT_MENU";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @Override
    public void initView() {
        super.initView();
    }

    @Override
    public void initData() {
        super.initData();
       initFragment();
    }

    /**
     * 初始化主界面和菜单界面
     */
    private void initFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();   //开启事务
        fragmentTransaction.replace(R.id.content_frame,new MainFragment(),TAG_LEFT_MENU);
        fragmentTransaction.commit();   //提交事务
    }


}
