package com.listenergao.mytest.activity;

import android.support.v7.widget.Toolbar;

import com.listenergao.mytest.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ListenerGao on 2016/9/3.
 * <p/>
 * Fragment测试
 */
public class TestFragmentActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolBar;
//    @BindView(R.id.ib_arrow_left)
//    ImageButton ibArrowLeft;
//    @BindView(R.id.toolbar)
//    Toolbar toolbar;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_anim;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);

//        setToolbarTitle("Fragment测试");
    }

    @Override
    protected void initData() {

    }

}
