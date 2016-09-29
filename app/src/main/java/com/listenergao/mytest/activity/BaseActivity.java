package com.listenergao.mytest.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.LinearLayout;

/**
 * Created by ListenerGao on 2016/6/21.
 * 创建父类的Activity
 */
public abstract class BaseActivity extends AppCompatActivity {
    private Toolbar mToolBar;
    private LinearLayout rootLinearLayout;
    private String title;

    /**
     * 得到布局文件
     *
     * @return
     */
    protected abstract int getLayoutResId();

    /**
     * 初始化界面
     */
    protected abstract void initView();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    public String getToolbarTitle() {
        if (TextUtils.isEmpty(title)) {
            return "";
        } else {
            return title;
        }
    }

    public void setToolbarTitle(String title) {
        this.title = title;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //这句话很关键,注意调用父类的方法
//        super.setContentView(R.layout.activity_base);
        //隐藏导航栏
//        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);  //继承AppCompatActivity时使用
//        requestWindowFeature(Window.FEATURE_NO_TITLE);  //继承Activity时使用

        //在代码里直接声明透明状态栏更有效
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
//            layoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | layoutParams.flags);
//        }
//
//        initToolBar();

        if (getLayoutResId() != -1) {
            setContentView(getLayoutResId());
//            mToolBar = (Toolbar) findViewById(R.id.toolbar);
//            mToolBar.setNavigationIcon(R.drawable.arrow_left);
//            mToolBar.setNavigationOnClickListener(this);
        }
        initView();
        if (savedInstanceState == null) {   //处理当屏幕发生旋转时,Fragment会重建,导致界面出现覆盖的问题.
            initData();
        }
    }

//    private void initToolBar() {
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        if (toolbar != null) {
//            setSupportActionBar(toolbar);
//            //设置Title
//            toolbar.setTitle(getToolbarTitle());
//            //设置 返回 图标
//            toolbar.setNavigationIcon(R.drawable.arrow_left);
//            toolbar.setNavigationOnClickListener(this);
//        }
//    }
//
//    @Override
//    public void setContentView(@LayoutRes int layoutResID) {
////        setContentView(View.inflate(this,layoutResID,null));
//        setContentView(getLayoutResId());
//        initView();
//    }
//
//    @Override
//    public void setContentView(View view) {
//        rootLinearLayout = (LinearLayout) findViewById(R.id.root_layout);
//        if (rootLinearLayout == null) return;
//        rootLinearLayout.addView(view,new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//        initView();
//        initToolBar();
//    }
//
//    @Override
//    public void onClick(View v) {
//        finish();
//    }
}
