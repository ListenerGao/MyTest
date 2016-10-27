package com.listenergao.mytest.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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
        //设置DrawerLayout
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close) {
        };
        drawerToggle.syncState();
        drawerLayout.setDrawerListener(drawerToggle);
        //获取头布局内部的控件.在旧版本中，假如你要获得 NavigationView 中的文本控件，你可以在 activity 中直接调用 findViewById() 方法来获得。
        // 但是在 Support Library 23.1.0 版本之后，这个不再适用，在我使用的 Support Library 23.1.1 版本中，
        // 可以直接调用 getHeaderView()方法先得到 Header,然后在通过header来获得头部内的控件。
        View header = navigationView.getHeaderView(0);
        TextView userName = (TextView) header.findViewById(R.id.tv_username);
        TextView email = (TextView) header.findViewById(R.id.tv_email);
        userName.setText("Listener Gao");
        email.setText("hnthgys@gmail.com");

        //处理菜单列表种图标不显示原始颜色的问题.  当你设置完图标后,会发现无论图标的原始颜色是什么,都会全部变成灰色.
        // 此时,你可以通过app:itemIconTint="@color/..."属性为图标设置统一的颜色.当然,如果你需要图标显示自己原始的颜色,
        // 可以调用NavigationView的setItemIconTintList(ColorStateList tint)方法,参数传为null即可.
//        navigationView.setItemIconTintList(null);

        //设置菜单列表的点击事件。
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.drawer_home:
                        Toast.makeText(NavigationViewActivity.this, "Home", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawers();    //点击条目后，关闭侧滑才菜单
                        item.setChecked(true);          //设置点击过的item为选中状态，字体和图片的颜色会发生变化，颜色会变为和Toolbar的颜色一致。
                        toolbar.setTitle("Home");       //修改Toolbar显示的Title
                        break;
                    case R.id.drawer_favourite:
                        Toast.makeText(NavigationViewActivity.this, "Favourite", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawers();
                        item.setChecked(true);
                        toolbar.setTitle("Favourite");
                        break;
                    case R.id.drawer_download:
                        Toast.makeText(NavigationViewActivity.this, "Download", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawers();
                        item.setChecked(true);
                        toolbar.setTitle("Download");
                        break;
                    case R.id.drawer_more:
                        Toast.makeText(NavigationViewActivity.this, "More", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawers();
                        item.setChecked(true);
                        toolbar.setTitle("More");
                        break;
                    case R.id.drawer_settings:
                        Toast.makeText(NavigationViewActivity.this, "Settings", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawers();
                        item.setChecked(true);
                        toolbar.setTitle("Settings");
                        break;
                }
                return false;
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onBackPressed() {
        //重写返回键，当侧滑菜单处于打开状态，点击返回键就关闭侧滑菜单。
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawers();
        else
            super.onBackPressed();
    }
}
