package com.listenergao.mytest.data;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;


/**
 * ViewPager的适配器
 *
 * @author By ListenerGao
 *         Create at 2016/10/15 16:36
 */
public class TabFragmentAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> mFragments;
    private List<String> mTabTitles;
    public TabFragmentAdapter(FragmentManager fm, List<Fragment> fragments,List<String> tabTitles) {
        super(fm);
        this.mFragments = fragments;
        this.mTabTitles = tabTitles;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
    //注意:需要重写此方法,从标签集合中获取到title,否则标签上的title则不会显示.
    @Override
    public CharSequence getPageTitle(int position) {
        return mTabTitles.get(position);
    }
}
