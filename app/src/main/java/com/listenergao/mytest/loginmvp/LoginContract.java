package com.listenergao.mytest.loginmvp;

/**
 * 由于每一个业务功能都对应有一套 MVP 接口，很多时候会将这一套接口封装在一起，
 * 组成一套契约。
 */
public interface LoginContract {

    interface OnLoginListener {
        void onLoginSuccess();

        void onLoginError(String errorMsg);
    }

    interface ILoginView {
        void showLoading();

        void hideLoading();

        void onLoginSuccess();

        void onLoginError(String errorMsg);

    }


    interface ILoginModel {
        void login(String username, String password, OnLoginListener listener);
    }

    interface ILoginPresenter {
        void attachView(ILoginView iLoginView);

        void detachView();

        boolean isAttachedView();

        void login(String username, String password);
    }


}
