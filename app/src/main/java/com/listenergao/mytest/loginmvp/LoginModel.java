package com.listenergao.mytest.loginmvp;

public class LoginModel implements LoginContract.ILoginModel {
    @Override
    public void login(String username, String password, LoginContract.OnLoginListener listener) {
        // 做网络请求，根据结果回调 listener
        listener.onLoginSuccess();
    }
}
