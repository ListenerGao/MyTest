package com.listenergao.mytest.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.listenergao.mytest.R;
import com.listenergao.mytest.activity.DownLoadActivity;
import com.listenergao.mytest.download.DownloadService;
import com.listenergao.mytest.download.FileInfo;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ListenerGao on 2016/8/25.
 *
 * Notification管理类
 */
public class NotificationUtils {
    private NotificationManager mNotificationManager;
    private Map<Integer,Notification> mNiNotifications;  //管理通知的集合
    private Context mContext;
    public NotificationUtils(Context context) {
        this.mContext = context;
        //得到系统通知服务
        mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNiNotifications = new HashMap<>();
    }

    /**
     * 显示Notification
     * @param fileInfo
     */
    public void showNotification(FileInfo fileInfo){
        //判断通知是否显示了
        if (!mNiNotifications.containsKey(fileInfo.getId())) {
            //创建通知对象
            Notification notification = new Notification();
            //设置滚动文字
            notification.tickerText = fileInfo.getFileName() + "开始下载...";
            //设置显示时间
            notification.when = System.currentTimeMillis();
            //设置图标
            notification.icon = R.drawable.ic_notifications;
            //设置通知特性
            notification.flags = Notification.FLAG_AUTO_CANCEL;
            //设置点击通知栏的操作
            Intent intent = new Intent(mContext, DownLoadActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(mContext,0,intent,0);
            notification.contentIntent = pendingIntent;
            //创建RemoteView
            RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(),R.layout.notification);
            //设置TextView显示文本
            remoteViews.setTextViewText(R.id.tv_file_name,fileInfo.getFileName());
            //设置点击 下载 按钮的操作
            Intent intentStart = new Intent(mContext, DownloadService.class);
            intentStart.setAction(DownloadService.ACTION_START);
            intentStart.putExtra("fileInfo",fileInfo);
            PendingIntent pIntentStart = PendingIntent.getService(mContext,0,intentStart,0);
            remoteViews.setOnClickPendingIntent(R.id.bt_start,pIntentStart);
            //设置点击 暂停 按钮的操作
            Intent intentStop = new Intent(mContext, DownloadService.class);
            intentStop.setAction(DownloadService.ACTION_STOP);
            intentStop.putExtra("fileInfo",fileInfo);
            PendingIntent pIntentStop = PendingIntent.getService(mContext,0,intentStop,0);
            remoteViews.setOnClickPendingIntent(R.id.bt_stop,pIntentStop);
            //设置Notification视图
            notification.contentView = remoteViews;
            //发出通知
            mNotificationManager.notify(fileInfo.getId(),notification);
            //将通知添加到集合中
            mNiNotifications.put(fileInfo.getId(),notification);

        }
    }

    /**
     * 取消Notification
     * @param id
     */
    public void cancelNotification(int id) {
        mNotificationManager.cancel(id);
        //从集合中移除
        mNiNotifications.remove(id);
    }

    /**
     * 更新Notification
     * @param id
     * @param progress
     */
    public void updateNotification(int id,int progress) {
        Notification notification = mNiNotifications.get(id);
        if (notification != null) {
            //更新进度
            notification.contentView.setProgressBar(R.id.pb_progressBar,100,progress,false);
            mNotificationManager.notify(id,notification);
        }
    }
}
