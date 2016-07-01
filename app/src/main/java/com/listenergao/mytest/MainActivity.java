package com.listenergao.mytest;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import com.listenergao.mytest.activity.BaseActivity;
import com.listenergao.mytest.fragment.LeftDrawerFragment;
import com.listenergao.mytest.fragment.MainFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends BaseActivity {

    //Fragment标识
    private static final String TAG_MAIN_MENU = "TAG_MAIN_MENU";
    private static final String TAG_LEFT_MENU = "TAG_LEFT_MENU";
    @BindView(R.id.content_frame)
    FrameLayout contentFrame;
    @BindView(R.id.left_drawer)
    FrameLayout leftDrawerFragment;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.civ_title_head)
    CircleImageView civ_title_head;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        civ_title_head.setVisibility(View.VISIBLE);

    }

    @Override
    public void initData() {
        initFragment();

    }

    @OnClick(R.id.civ_title_head)
    public void showLeftDrawer(){
        //点击打开侧滑菜单
        drawerLayout.openDrawer(Gravity.LEFT);
    }

    /**
     * 初始化主界面和菜单界面
     */
    private void initFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();   //开启事务
        fragmentTransaction.replace(R.id.content_frame, new MainFragment(), TAG_MAIN_MENU);   //主页面
        fragmentTransaction.replace(R.id.left_drawer, new LeftDrawerFragment(), TAG_LEFT_MENU);//侧拉菜单页
        fragmentTransaction.commit();   //提交事务
    }


}
