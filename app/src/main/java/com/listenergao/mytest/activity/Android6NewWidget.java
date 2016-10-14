package com.listenergao.mytest.activity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.listenergao.mytest.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ListenerGao on 2016/10/11.
 * <p>
 * android 6.0新控件测试
 */
public class Android6NewWidget extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "Android6NewWidget";
    @BindView(R.id.activity_android6_new_widget)
    CoordinatorLayout rootLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab_button)
    FloatingActionButton fabButton;
    @BindView(R.id.til_username)
    TextInputLayout tilUsername;
    @BindView(R.id.til_password)
    TextInputLayout tilPassword;
    @BindView(R.id.bt_tl)
    Button btTl;
    @BindView(R.id.bt_nv)
    Button btNv;
    @BindView(R.id.bt_cl)
    Button btCl;
    @BindView(R.id.bt_abl)
    Button btAbl;
    @BindView(R.id.bt_ctl)
    Button btCtl;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_android6_new_widget;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);

        toolbar.setTitle("Android6.0 新控件");
        setSupportActionBar(toolbar);
        //显示返回箭头
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //设置FloatingActionButton的点击事件
        //fabButton.setOnClickListener(this);

        hideKeyboard();
        //初始化TextInputLayout
        //得到EditText对象
        final EditText userEditText = tilUsername.getEditText();
        final EditText pwdEditText = tilPassword.getEditText();
        //设置hint提示,也可直接在xml中设置
        //userEditText.setHint("Username");
        //pwdEditText.setHint("Password");


        //EditText添加文本变化监听
        userEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.d(TAG, "beforeTextChanged执行了....s = " + s + "---start = " + start + "---count = " + count + "---after = " + after);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d(TAG, "onTextChanged执行了....s = " + s + "---start = " + start + "---count = " + count + "---before = " + before);
                if (s.length() > 7) {
                    tilUsername.setErrorEnabled(true);
                    tilUsername.setError("用户名长度不能超过8个");
                } else {
                    tilUsername.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d(TAG, "afterTextChanged执行了....s = " + s);
            }
        });

        pwdEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() < 6) {
                    tilPassword.setErrorEnabled(true);
                    tilPassword.setError("密码长度不能小于6个");
                } else {
                    tilPassword.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected void initData() {

    }

    /**
     * 隐藏软键盘
     */
    private void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


    @OnClick({R.id.fab_button, R.id.bt_tl, R.id.bt_nv, R.id.bt_cl, R.id.bt_abl, R.id.bt_ctl})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab_button:
                //得到Snackbar对象
                final Snackbar snackbar = Snackbar.make(rootLayout, "我是Snackbar...", Snackbar.LENGTH_LONG);
                //设置Snackbar背景
                snackbar.getView().setBackgroundResource(R.color.colorPrimary);
                snackbar.show();
                //显示带Action的Snackbar
                snackbar.setAction("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //关闭Snackbar
                        snackbar.dismiss();
                    }
                });
                break;
            case R.id.bt_tl:
                openActivity(this, TabLayoutActivity.class);
                break;
            case R.id.bt_nv:
                openActivity(this, NavigationViewActivity.class);
                break;
            case R.id.bt_cl:
                openActivity(this, CoordinatorLayoutActivity.class);
                break;
            case R.id.bt_abl:
                openActivity(this, AppBarLayoutActivity.class);
                break;
            case R.id.bt_ctl:
                openActivity(this, CollapsingToolbarLayoutActivity.class);
                break;
        }
    }

    public void openActivity(Context context, Class clazz) {
        Intent intent = new Intent(context, clazz);
        startActivity(intent);
    }
}
