package com.listenergao.mytest.download;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadService extends Service {

    private static final String TAG = "DownloadService";
    /**
     * 标识 开始下载
     */
    public static final String ACTION_START = "ACTION_START";
    /**
     * 标识 停止下载
     */
    public static final String ACTION_STOP = "ACTION_STOP";
    /**
     * 标识 更新下载进度
     */
    public static final String ACTION_UPDATE = "ACTION_UPDATE";
    /**
     * 定义用来保存文件的路径
     */
    public static final String DOWNLOAD_PATH = Environment.getExternalStorageDirectory().getAbsolutePath()+"/downloads/";
    public static final int MSG_INIT = 0;
    private DownloadTask mTask; //下载任务

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //获取Intent传递的Action,判断是开始下载还是停止下载
        if (ACTION_START.equals(intent.getAction())) {
            FileInfo fileInfo = (FileInfo) intent.getSerializableExtra("fileInfo");
            Log.d(TAG,"开始下载--文件信息:"+fileInfo.toString());
            //启动初始化线程
            new InitThread(fileInfo).start();
        }else if (ACTION_STOP.equals(intent.getAction())) {
            FileInfo fileInfo = (FileInfo) intent.getSerializableExtra("fileInfo");
            Log.d(TAG,"停止下载--文件信息:"+fileInfo.toString());
            // 停止下载
            if (mTask != null) {
                mTask.isPause = true;
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_INIT:
                    FileInfo fileInfo = (FileInfo) msg.obj;
                    Log.d(TAG,"获取到的网络文件长度为:" + fileInfo.getLength());
                    //开启下载任务
                    mTask = new DownloadTask(DownloadService.this,fileInfo);
                    mTask.download();
                    break;
            }
        }
    };

    /**
     * 初始化子线程
     */
    class InitThread extends Thread {
        private FileInfo mFileInfo;

        public InitThread(FileInfo mFileInfo) {
            this.mFileInfo = mFileInfo;
        }

        @Override
        public void run() {
            HttpURLConnection conn = null;
            RandomAccessFile raf = null;
            try {
                URL url = new URL(mFileInfo.getUrl());
                conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(3000);   //设置连接超时时间为3000毫秒
                int length = -1;    //记录文件长度
                if (conn.getResponseCode() == 200) {
                    //获得文件长度
                    length = conn.getContentLength();
                }
                if (length <= 0) {  //判断,文件长度<=0,说明连接网络失败
                    return;
                }
                File dir = new File(DOWNLOAD_PATH);
                if (!dir.exists()) {    //判断,如果文件路径不存在,则创建
                    dir.mkdir();
                }
                //在本地创建文件
                File file = new File(dir,mFileInfo.getFileName());
                raf  = new RandomAccessFile(file,"rwd");
                //设置本地文件长度
                raf.setLength(length);

                mFileInfo.setLength(length);
                mHandler.obtainMessage(MSG_INIT,mFileInfo).sendToTarget();
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                try {
                    conn.disconnect(); //关闭连接
                    raf.close();    //关闭流
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
