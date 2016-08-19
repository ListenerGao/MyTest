package com.listenergao.mytest.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Environment;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;

import com.listenergao.mytest.R;
import com.orhanobut.logger.Logger;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;

import java.io.File;

import okhttp3.Call;

/**
 * Created by ListenerGao on 2016/8/13.
 * 下载更新的服务
 */
public class UpdateService1 extends Service {
    private static final String downloadUrl = "http://182.92.6.139:80/apk/school_2.17_117.apk";
    private static final String TAG ="UpdateService1" ;
    private NotificationCompat.Builder mBuilder;
    private NotificationManager mNotificationManager;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        downloadApk();
        return super.onStartCommand(intent, flags, startId);
    }

    private void downloadApk() {
        showNotification();
        OkHttpUtils.get()
                .url(downloadUrl)
                .build()
                .execute(new FileCallBack(Environment.getExternalStorageDirectory().getAbsolutePath(), getApkName(downloadUrl)) {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(File response, int id) {

                    }

                    @Override
                    public void inProgress(float progress, long total, int id) {
                        super.inProgress(progress, total, id);
                        int nowProgress = (int)(progress * 100);
                        if (nowProgress >= 100) {
                            mBuilder.setProgress(0,0,false);    //移除进度条
                            mBuilder.setContentTitle("下载完成,点击安装");
                            stopSelf(); //停止服务

                        }else {
                            if (nowProgress > 0) {
                                mBuilder.setDefaults(Notification.COLOR_DEFAULT);
                            }
                            if (nowProgress % 10 == 0){
                                //注意:此方法在4.0以后版本才有用.如果需要支持早期版本,需使用RemoteViews来自定义视图
                                mBuilder.setProgress(100,nowProgress,false);
                                mBuilder.setContentTitle("正在下载...");
//                            mBuilder.setOngoing(true);
                            }
                        }
                        mNotificationManager.notify(1,mBuilder.build());
                        Logger.d("total = " + total +",progress = " +(progress * 100));
                    }
                });

    }

    public void showNotification() {
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setSmallIcon(R.drawable.hacker)     //设置通知小Icon
//                .setContentTitle("开始下载...")     //设置通知栏标题
                .setTicker("开始下载...")
                .setWhen(System.currentTimeMillis())    //通知产生的时间，会在通知信息里显示，一般是系统获取到的时间
                .setPriority(NotificationCompat.PRIORITY_MAX)  //设置通知的优先级PRIORITY_HIGH或PRIORITY_MAX  以浮动形式展示
                //.setAutoCancel(true)    //设置这个标志当用户单击面板就可以让通知将自动取消
//                .setOngoing(true)       ////true，设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
                // 设置通知的提示音
                // Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                // builder.setSound(alarmSound);
                .setDefaults(NotificationCompat.DEFAULT_ALL);//或者设置系统默认DEFAULT_ALL,包含DEFAULT_SOUND(提示音), DEFAULT_VIBRATE(振动), DEFAULT_LIGHTS(灯光闪烁)

//        mNotificationManager.notify(1, mBuilder.build());
    }

    /**
     * 通过下载url截取apk文件名
     *
     * @param url
     * @return
     */
    private String getApkName(String url) {
        return url.substring(url.lastIndexOf("/") + 1, url.length());
    }

}
