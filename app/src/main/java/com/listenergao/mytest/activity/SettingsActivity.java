package com.listenergao.mytest.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.listenergao.mytest.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by admin on 2016/8/11.
 */
public class SettingsActivity extends BaseActivity {
    @BindView(R.id.tv_version_number)
    TextView mVersionNumber;
    @BindView(R.id.tv_update)
    TextView mUpdate;
    @BindView(R.id.ll_version_update)
    LinearLayout mVersionUpdate;
    @BindView(R.id.ll_function_introduction)
    LinearLayout mFunctionIntroduction;
    @BindView(R.id.ll_help)
    LinearLayout mHelp;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_settings;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.ll_version_update, R.id.ll_function_introduction, R.id.ll_help})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_version_update:
                break;
            case R.id.ll_function_introduction:
                Toast.makeText(this, "功能介绍", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_help:
                Toast.makeText(this, "帮助", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
