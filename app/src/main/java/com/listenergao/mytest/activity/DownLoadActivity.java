package com.listenergao.mytest.activity;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.listenergao.mytest.R;
import com.listenergao.mytest.data.FilesAdapter;
import com.listenergao.mytest.download.DownloadService;
import com.listenergao.mytest.download.FileInfo;
import com.listenergao.mytest.utils.NotificationUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ListenerGao on 2016/8/20.
 * <p/>
 * 实现断点续传下载功能
 */
public class DownLoadActivity extends BaseActivity {
    private static final String TAG = "DownLoadActivity";

    @BindView(R.id.lv_download)
    ListView mListView;
    private FilesAdapter mAdapter;
    private NotificationUtils mNotificationUtils;
    private Messenger mServiceMessenger;    //service中的Messenger
    private boolean isFinished = false;     //是否下载完成
    ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //获得Service中的Messenger
            mServiceMessenger = new Messenger(service);
            //设置Adapter中的Messenger
            mAdapter.setMessenger(mServiceMessenger);

            //创建Activity中的Messenger
            Messenger activityMessenger = new Messenger(mHandler);
            //创建消息
            Message msg = new Message();
            msg.what = DownloadService.MSG_BIND;
            msg.replyTo = activityMessenger;
            //使用Service中的Messenger,发送Activity中的Messenger
            try {
                mServiceMessenger.send(msg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DownloadService.MSG_START:     //显示Notification
                    mNotificationUtils.showNotification((FileInfo) msg.obj);
                    break;
                case DownloadService.MSG_UPDATE:    //更新进度
                    int finished = msg.arg1;
                    int id = msg.arg2;
                    mAdapter.updateProgress(id, finished);
                    //更新Notification显示进度
                    mNotificationUtils.updateNotification(id, finished);
                    Log.d(TAG, "下载文件Id:" + id + "handler-广播接收的下载进度:" + finished);
                    break;
                case DownloadService.MSG_FINISH:        //下载完成
                    FileInfo fileInfo = (FileInfo) msg.obj;
                    mAdapter.updateProgress(fileInfo.getId(), 100);  //由于设置的1s发送广播一次,有可能在下载完成时进度条显示不完全
                    Toast.makeText(DownLoadActivity.this, fileInfo.getFileName() + "下载完毕", Toast.LENGTH_SHORT).show();
                    mNotificationUtils.cancelNotification(fileInfo.getId());
                    Log.d(TAG, fileInfo.getFileName() + "下载完毕");
                    if (mConnection != null) {
                        unbindService(mConnection);
                    }
                    isFinished = true;  //标识 下载完成
                    break;
            }
        }
    };

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_download;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);

    }

    @Override
    protected void initData() {
        //初始化文件信息对象
        FileInfo fileInfo = new FileInfo(0, "http://sw.bos.baidu.com/sw-search-sp/software/a5165ca000166/kugou_8.0.90.19180_setup.exe", "kugou_8.0.90.19180_setup.exe", 0, 0);
        FileInfo fileInfo1 = new FileInfo(1, "http://sw.bos.baidu.com/sw-search-sp/software/5fef587e53e/WeChat_2.1.0.37.exe", "WeChat_2.1.0.37.exe", 0, 0);
        FileInfo fileInfo2 = new FileInfo(2, "http://sw.bos.baidu.com/sw-search-sp/software/573f5db9b1f6a/QQ_8.5.18600.0_setup.exe", "QQ_8.5.18600.0_setup.exe", 0, 0);
        FileInfo fileInfo3 = new FileInfo(3, "http://dlsw.baidu.com/sw-search-sp/soft/4b/10222/GoogleEarthWin7.1.5.1557.1436254887.exe", "GoogleEarthWin7.1.5.1557.1436254887.exe", 0, 0);
        FileInfo fileInfo4 = new FileInfo(4, "http://dlsw.baidu.com/sw-search-sp/soft/9b/17183/BingDict_Setup.1408945849.exe", "BingDict_Setup.1408945849.exe", 0, 0);
        List<FileInfo> mFileInfos = new ArrayList<>();
        mFileInfos.add(fileInfo);
        mFileInfos.add(fileInfo1);
        mFileInfos.add(fileInfo2);
        mFileInfos.add(fileInfo3);
        mFileInfos.add(fileInfo4);
        mAdapter = new FilesAdapter(this, mFileInfos);
        mListView.setAdapter(mAdapter);

        //注册广播接收器.代码中动态注册
//        IntentFilter filter = new IntentFilter();
//        filter.addAction(DownloadService.ACTION_UPDATE);    //进度更新广播
//        filter.addAction(DownloadService.ACTION_FINISH);    //下载完成广播
//        filter.addAction(DownloadService.ACTION_START);    //点击下载时的广播
//        registerReceiver(mReceiver, filter);

        mNotificationUtils = new NotificationUtils(this);

        //绑定Service,获得Messenger
        Intent intent = new Intent(this, DownloadService.class);
        bindService(intent, mConnection, Service.BIND_AUTO_CREATE);
    }

    /**
     * 更新UI的广播接收器
     */
//    BroadcastReceiver mReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            if (DownloadService.ACTION_UPDATE.equals(intent.getAction())) {      //更新进度
//                int finished = intent.getIntExtra("finished", 0);
//                int id = intent.getIntExtra("id", 0);
//                mAdapter.updateProgress(id, finished);
//                //更新Notification显示进度
//                mNotificationUtils.updateNotification(id,finished);
//                Log.d(TAG, "下载文件Id:" + id + "广播接收的下载进度:" + finished);
//            }else if (DownloadService.ACTION_FINISH.equals(intent.getAction())){    //下载完成
//                FileInfo fileInfo = (FileInfo) intent.getSerializableExtra("fileInfo");
//                mAdapter.updateProgress(fileInfo.getId(),100);  //由于设置的1s发送广播一次,有可能在下载完成时进度条显示不完全
//                Toast.makeText(DownLoadActivity.this, fileInfo.getFileName()+"下载完毕", Toast.LENGTH_SHORT).show();
//                mNotificationUtils.cancelNotification(fileInfo.getId());
//                Log.d(TAG, fileInfo.getFileName()+"下载完毕");
//            }else if (DownloadService.ACTION_START.equals(intent.getAction())) {    //显示Notification
//                mNotificationUtils.showNotification((FileInfo) intent.getSerializableExtra("fileInfo"));
//            }
//        }
//    };
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //取消广播的注册
//        unregisterReceiver(mReceiver);

        //解绑服务
//        if (isFinished && mConnection != null) {
//            unbindService(mConnection);
//            Toast.makeText(DownLoadActivity.this, "Activity中destroy执行了...", Toast.LENGTH_SHORT).show();
//        }
    }
}
