package com.listenergao.mytest.loginmvp;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.ToastUtils;
import com.listenergao.mytest.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity implements LoginContract.ILoginView {

    private ActivityLoginBinding mBinding;
    private LoginPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityLoginBinding.inflate(getLayoutInflater());

        mPresenter = new LoginPresenter();
        mPresenter.attachView(this);

        mBinding.btnLogin.setOnClickListener(view -> {
            String username = mBinding.etUsername.getText().toString();
            String password = mBinding.etPassword.getText().toString();
            if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                ToastUtils.showShort("用户名或密码不能为空！");
                return;
            }
            mPresenter.login(username, password);
        });

    }

    @Override
    public void showLoading() {
        mBinding.progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        mBinding.progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onLoginSuccess() {
        ToastUtils.showShort("登录成功");
    }

    @Override
    public void onLoginError(String errorMsg) {
        ToastUtils.showShort(errorMsg);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }
}
