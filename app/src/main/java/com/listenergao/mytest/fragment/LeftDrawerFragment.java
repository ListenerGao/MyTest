package com.listenergao.mytest.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.listenergao.mytest.R;
import com.listenergao.mytest.activity.PopupWindowTest;
import com.listenergao.mytest.activity.SettingsActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by admin on 2016/6/29.
 * 侧拉菜单界面
 */
public class LeftDrawerFragment extends BaseFragment {

    @BindView(R.id.settings)
    LinearLayout mSettings;
    @BindView(R.id.about_us)
    LinearLayout mAboutUs;
    @BindView(R.id.tv_popupwindow)
    TextView tvPopupwindow;
    @BindView(R.id.tv2)
    TextView tv2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_left_menu, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.settings, R.id.about_us,R.id.tv_popupwindow, R.id.tv2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.settings:     //设置页面
                openActivity(getContext(), SettingsActivity.class);
                break;
            case R.id.about_us:
                break;
            case R.id.tv_popupwindow:   //PopupWindow测试页面
                openActivity(getContext(), PopupWindowTest.class);
                break;
            case R.id.tv2:
                break;
        }
    }


    public void openActivity(Context context, Class clazz) {
        Intent intent = new Intent(context, clazz);
        startActivity(intent);
    }

}
