package com.listenergao.mytest.update;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.io.File;

/**
 * Created by ListenerGao on 2016-8-6.
 */
public class UpdateService extends Service {

    private String apkUrl;
    private String localPath;
    private NotificationManager notificationManager;
    private Notification notification;
    private NotificationCompat.Builder builder;
    private static final String APPNAME = "VGEN_Contacts.apk";
    private static final int NOTIFICATION_CODE = 732;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent == null) {
            notifyUser("下载失败", 0);
            stopSelf();
        } else {
            apkUrl = intent.getStringExtra("apkUrl");
            localPath = Environment.getExternalStorageDirectory() + "/UpdateLoad/" + intent.getStringExtra("apkName");
            startDonwnLoad();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void startDonwnLoad() {
        UpdateManager.getInstance().startDownload(apkUrl, localPath, new UpdateDownLoadListener() {
            @Override
            public void onStarted() {
                createNotifacationCompat();
            }

            @Override
            public void onProgressChanged(int progress) {
                notifyUser("正在下载", progress);
            }

            @Override
            public void onFinished(float fileSize) {
                notifyUser("下载完成", 100);
                stopSelf();
            }

            @Override
            public void onFailure(String errorMessage) {
                //notifyUser(errorMessage, 0);
                stopSelf();
            }
        });
    }

    private void createNotifacationCompat() {
        Log.e("progress", "开始");
        builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(android.R.mipmap.sym_def_app_icon)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), android.R.mipmap.sym_def_app_icon))
                .setContentTitle("app_Name")
                .setAutoCancel(false)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis());


    }

    private void notifyUser(String message, int progress) {
        if (progress != 0){
            builder.setDefaults(Notification.COLOR_DEFAULT);
        }
        if (progress >= 0 && progress < 100) {
            builder.setProgress(100, progress, false);
        } else {
            builder.setProgress(0, 0, false);
            installApk();
            notificationManager.cancel(NOTIFICATION_CODE);
        }
        builder.setTicker(message);
        builder.setContentIntent(progress >= 100 ? getContentIntent() :
                PendingIntent.getActivity(this, 0, new Intent(), PendingIntent.FLAG_UPDATE_CURRENT));
        notification = builder.build();
        notificationManager.notify(NOTIFICATION_CODE, notification);

    }

    private void installApk() {
        File apkFile = new File(localPath);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.parse("file://" + apkFile.toString()), "application/vnd.android.package-archive");
        startActivity(intent);
    }

    public PendingIntent getContentIntent() {
        File apkFile = new File(localPath);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.parse("file//:" + apkFile.toString()), "application/vnd.android.package-archive");
        return PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }
}
