package com.listenergao.mytest.loginmvp;

public class LoginPresenter implements LoginContract.ILoginPresenter, LoginContract.OnLoginListener {

    private LoginContract.ILoginView mLoginView;
    private final LoginModel mLoginModel;

    public LoginPresenter() {
        mLoginModel = new LoginModel();
    }

    @Override
    public void attachView(LoginContract.ILoginView iLoginView) {
        this.mLoginView = iLoginView;
    }

    @Override
    public void detachView() {
        mLoginView = null;
    }

    @Override
    public boolean isAttachedView() {
        return mLoginView != null;
    }

    @Override
    public void login(String username, String password) {
        mLoginModel.login(username, password, this);
    }

    @Override
    public void onLoginSuccess() {
        if (isAttachedView()) {
            mLoginView.hideLoading();
            mLoginView.onLoginSuccess();
        }
    }

    @Override
    public void onLoginError(String errorMsg) {
        if (isAttachedView()) {
            mLoginView.hideLoading();
            mLoginView.onLoginError(errorMsg);
        }
    }
}
