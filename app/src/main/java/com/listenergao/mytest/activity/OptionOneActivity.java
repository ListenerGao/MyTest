package com.listenergao.mytest.activity;

import android.util.Log;
import android.view.View;

import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.listenergao.mytest.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * create on 2018/12/3
 *
 * @author ListenerGao
 */
public class OptionOneActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_option_one_layout;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        toolbar.setTitle("Activity转场动画一");

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityCompat.finishAfterTransition(this);
    }

    @Override
    public void onClick(View v) {
        Log.d("gys", "返回事件");
        onBackPressed();
    }
}
