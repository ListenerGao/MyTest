package com.listenergao.mytest.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;

import com.listenergao.mytest.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ListenerGao on 2016/9/3.
 * <p/>
 * Activity切换动画
 */
public class AnimActivity extends BaseActivity {
    @BindView(R.id.ib_arrow_left)
    ImageButton ibArrowLeft;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_anim;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);

        ibArrowLeft.setVisibility(View.VISIBLE);
        toolbar.setTitle("ActivityAnim-Test");
    }

    @Override
    protected void initData() {

    }


    @OnClick(R.id.ib_arrow_left)
    public void onClick() {
        finish();
    }
}
