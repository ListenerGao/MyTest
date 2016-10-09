package com.listenergao.mytest;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.listenergao.mytest.activity.BaseActivity;
import com.listenergao.mytest.fragment.LeftDrawerFragment;
import com.listenergao.mytest.fragment.MainFragment;
import com.listenergao.mytest.utils.UiUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ListenerGao on 2016/7/1.
 */
public class MainActivity extends BaseActivity {

    //Fragment标识
    private static final String TAG_MAIN_MENU = "TAG_MAIN_MENU";
    private static final String TAG_LEFT_MENU = "TAG_LEFT_MENU";
    @BindView(R.id.drawer_layout)
    public DrawerLayout drawerLayout;
    @BindView(R.id.content_frame)
    FrameLayout contentFrame;
    @BindView(R.id.left_drawer)
    FrameLayout leftDrawerFragment;
    @BindView(R.id.toolbar)
    Toolbar mToolBar;

    private ActionBarDrawerToggle mActionBarDrawerToggle;


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
//        civ_title_head.setVisibility(View.VISIBLE);

        mToolBar.setTitle("首页"); //设置主标题
//        mToolBar.setTitleTextColor(getResources().getColor(R.color.white)); //设置主标题字体颜色
//        mToolBar.setTitleTextAppearance(this, R.style.Theme_ToolBar_Base_Title);    //修改主标题的外观,包括文字的颜色 以及大小等
//
//        mToolBar.setSubtitle("subTitle");   //设置子标题
//        mToolBar.setSubtitleTextColor(getResources().getColor(R.color.darker_gray));    //设置子标题字体颜色
//        mToolBar.setTitleTextAppearance(this, R.style.Theme_ToolBar_Base_Subtitle);     //修改子标题的外观,包裹文字的颜色,以及大小等
        setSupportActionBar(mToolBar);
//        mToolBar.setNavigationIcon(R.drawable.ic_drawer_home);   //设置导航栏图标
//        mToolBar.setLogo(R.mipmap.ic_launcher);     //设置app Logo

        //设置右上角的填充菜单
//        mToolBar.inflateMenu(R.menu.base_toolbar_menu);
        //设置右上角菜单的点击事件
//        mToolBar.setOnMenuItemClickListener(this);
        mActionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, mToolBar, R.string.open, R.string.close);
        mActionBarDrawerToggle.syncState();  // ?
        drawerLayout.setDrawerListener(mActionBarDrawerToggle);

    }

    @Override
    public void initData() {
        initFragment();
    }

//    @OnClick(R.id.civ_title_head)
//    public void showLeftDrawer(){
//        //点击打开侧滑菜单
//        drawerLayout.openDrawer(Gravity.LEFT);
//    }

    /**
     * 初始化主界面和菜单界面
     */
    private void initFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();   //开启事务
        fragmentTransaction.add(R.id.content_frame, new MainFragment(), TAG_MAIN_MENU);   //主页面
        fragmentTransaction.add(R.id.left_drawer, new LeftDrawerFragment(), TAG_LEFT_MENU);//侧拉菜单页
        fragmentTransaction.commit();   //提交事务


        MainFragment mainFragment = (MainFragment) fragmentManager.findFragmentByTag(TAG_MAIN_MENU);

    }

    /**
     * 初始化Action Menu
     *
     * @param menu
     * @return
     */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.base_toolbar_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        //得到searchView对象
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        //设置SearchView打开关闭的监听事件
        MenuItemCompat.setOnActionExpandListener(searchItem,expandListener);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * SearchView监听事件
     */
    MenuItemCompat.OnActionExpandListener expandListener = new MenuItemCompat.OnActionExpandListener() {
        //打开
        @Override
        public boolean onMenuItemActionExpand(MenuItem item) {
            Toast.makeText(MainActivity.this, "onMenuItemActionExpand方法执行了...", Toast.LENGTH_SHORT).show();
            // 返回true 打开搜索扩展视图,否则不会打开视图
            return true;
        }
        //关闭
        @Override
        public boolean onMenuItemActionCollapse(MenuItem item) {
            Toast.makeText(MainActivity.this, "onMenuItemActionCollapse方法执行了...", Toast.LENGTH_SHORT).show();
            // 返回true 关闭搜索扩展视图
            return true;
        }
    };


    /**
     * Action Menu按钮点击事件
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_notification:
                Toast.makeText(UiUtils.getContext(), "通知", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.action_item1:
                Toast.makeText(UiUtils.getContext(), "设置", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.action_item2:
                Toast.makeText(UiUtils.getContext(), "关于", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
