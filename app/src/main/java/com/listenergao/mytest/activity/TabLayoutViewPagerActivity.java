package com.listenergao.mytest.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;

import com.listenergao.mytest.R;
import com.listenergao.mytest.data.TabFragmentAdapter;
import com.listenergao.mytest.fragment.TabFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * TabLayout+ViewPager示例
 *
 * @author By ListenerGao
 *         create at 2016/10/14 18:52
 */
public class TabLayoutViewPagerActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.activity_tab_layout_view_pager)
    LinearLayout activityTabLayoutViewPager;
    private List<String> mTabList;
    private List<Fragment> mTabFragments;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_tab_layout_view_pager;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);

        toolbar.setTitle("TabLayout+ViewPager");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    protected void initData() {
        mTabList = initTabList();
        initTabLayout(mTabList);
        mTabFragments = initFragments(mTabList);

        TabFragmentAdapter adapter = new TabFragmentAdapter(getSupportFragmentManager(),mTabFragments,mTabList);
        viewpager.setAdapter(adapter);
        //将TabLayout与ViewPager关联起来(注意:该行代码需在ViewPager设置Adapter之后调用)
        tabLayout.setupWithViewPager(viewpager);
        //为Tabs设置适配器
        tabLayout.setTabsFromPagerAdapter(adapter);
    }

    /**
     * 初始化Tab标签数据
     *
     * @return
     */
    private List<String> initTabList() {
        List<String> tabList = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            tabList.add("TAB " + i);
        }
        return tabList;
    }

    /**
     * 初始化TabLayout
     *
     * @param list
     */
    private void initTabLayout(List<String> list) {
        for (int i = 0; i < list.size(); i++) {
            if (i == 0)
                tabLayout.addTab(tabLayout.newTab().setText(list.get(i)), true);
            else
                tabLayout.addTab(tabLayout.newTab().setText(list.get(i)));
        }
        //设置TabLayout的模式为可滑动
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    /**
     * 初始化Tab标签对应的Fragment
     *
     * @param list 标签集合数据
     * @return
     */
    public List<Fragment> initFragments(List<String> list) {
        List<Fragment> mTabFragments = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            Fragment tabFragment = new TabFragment();
            Bundle bundle = new Bundle();
            bundle.putString("Content", list.get(i));
            tabFragment.setArguments(bundle);
            mTabFragments.add(tabFragment);
        }
        return mTabFragments;
    }

}
