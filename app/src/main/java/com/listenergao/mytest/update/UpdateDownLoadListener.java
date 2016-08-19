package com.listenergao.mytest.update;

/**
 * Created by ListenerGao on 2016-8-6.
 */
public interface UpdateDownLoadListener {

    void onStarted();

    void onProgressChanged(int progress);

    void onFinished(float fileSize);

    void onFailure(String errorMessage);
}
