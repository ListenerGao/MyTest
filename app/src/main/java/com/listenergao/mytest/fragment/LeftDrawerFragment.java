package com.listenergao.mytest.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.listenergao.mytest.R;

/**
 * Created by admin on 2016/6/29.
 * 侧拉菜单界面
 */
public class LeftDrawerFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_left_menu,null);
        return view;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }
}
