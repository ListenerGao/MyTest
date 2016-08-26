package com.listenergao.mytest.download;

import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by ListenerGao on 2016/8/20.
 * <p/>
 * 下载任务类
 */
public class DownloadTask {
    private static final String TAG = "DownloadTask";
    //定义带有缓存的线程池
    public static ExecutorService sExecutorService = Executors.newCachedThreadPool();
    public boolean isPause = false;  //是否暂停下载
    private Context mContext;
    private FileInfo mFileInfo;
    private ThreadDao mDao;
    private float mFinished = 0;  //记录下载进度
    private int mThreadCount = 1;   //下载线程的数量,默认为1个线程下载
    private List<DownloadThread> mThreadList;   //线程集合,管理线程
    private Timer mTimer = new Timer(); //定时器
    private Messenger mMessenger;

    public DownloadTask(Context mContext, Messenger messenger,FileInfo mFileInfo, int ThreadCount) {
        this.mContext = mContext;
        this.mMessenger = messenger;
        this.mFileInfo = mFileInfo;
        this.mThreadCount = ThreadCount;
        mDao = new ThreadDaoImpl(mContext);
    }

    public void download() {
        //读取数据库线程信息,是否有已下载但未完成的信息
        List<ThreadInfo> threadInfos = mDao.queryThreads(mFileInfo.getUrl());
        if (threadInfos.size() == 0) {  //表明是第一次下载
            //获得每个线程下载长度
            int length = mFileInfo.getLength() / mThreadCount;
            for (int i = 0; i < mThreadCount; i++) {
                // 设置线程信息,每个线程下载的开始,结束位置
                ThreadInfo threadInfo = new ThreadInfo(i, mFileInfo.getUrl(), length * i, (i + 1) * length - 1, 0);
                //设置最后一个线程下载的结束位置为文件的长度   防止除不尽有余数.
                if (i == mThreadCount - 1) {
                    threadInfo.setEnd(mFileInfo.getLength());
                }
                //将线程信息添加到集合中
                threadInfos.add(threadInfo);
                //将每个线程的下载信息插入数据库
                mDao.insertThread(threadInfo);
            }
        }
        mThreadList = new ArrayList<>();
        //开启多个线程下载
        for (ThreadInfo info : threadInfos) {
            DownloadThread thread = new DownloadThread(info);
//            thread.start();
            //使用线程池去启动线程进行下载
            DownloadTask.sExecutorService.execute(thread);
            //将下载线程添加到线程集合中,便于管理
            mThreadList.add(thread);
        }
        //启动定时任务
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                //发送更新进度的广播
//                int progress = (int) (mFinished / mFileInfo.getLength() * 100);
//                Intent intent = new Intent(DownloadService.ACTION_UPDATE);
//                intent.putExtra("finished", progress);  //以百分比的形式传递数据
//                Log.d(TAG,"mFinished:"+mFinished+"---mFileInfo.getLength()"+mFileInfo.getLength()+"--progress:"+progress);
//                intent.putExtra("id", mFileInfo.getId());    //文件id,用于判断是下载那个文件的进度
//                mContext.sendBroadcast(intent); //发送广播

                //使用Message发送进度
                if (!isPause){
                    Message msg = new Message();
                    msg.what = DownloadService.MSG_UPDATE;
                    msg.arg1 = (int) (mFinished / mFileInfo.getLength() * 100);
                    msg.arg2 = mFileInfo.getId();
                    try {
                        mMessenger.send(msg);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, 1000, 1000);   //延时1s执行,每隔1s执行一次

    }

    /**
     * 判断是否所有的线程都执行完毕
     */
    private synchronized void checkAllThreadsFinished() {
        boolean allThreadsFinished = true;

        for (DownloadThread thread : mThreadList) {
            if (!thread.isThreadFinished) {
                allThreadsFinished = false;
                break;
            }
        }
        if (allThreadsFinished) {
            //发送广播通知UI,下载任务结束
//            Intent intent = new Intent(DownloadService.ACTION_FINISH);
//            intent.putExtra("fileInfo", mFileInfo);  //将下载文件信息传递过去
//            mContext.sendBroadcast(intent);

            //使用Message发送任务结束消息
            Message msg = new Message();
            msg.what = DownloadService.MSG_FINISH;
            msg.obj = mFileInfo;
            try {
                mMessenger.send(msg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }

            //下载完成时,删除线程信息
            mDao.deleteThread(mFileInfo.getUrl());

            //取消定时器
            mTimer.cancel();
            //下载完成时,停止服务
            mContext.stopService(new Intent(mContext,DownloadService.class));
        }
    }

    /**
     * 下载线程
     */
    class DownloadThread extends Thread {
        private ThreadInfo mThreadInfo;
        private boolean isThreadFinished = false;     //标识 线程是否执行完毕

        public DownloadThread(ThreadInfo mThreadInfo) {
            this.mThreadInfo = mThreadInfo;
        }

        @Override
        public void run() {
            //向数据库插入线程下载信息,插入之前先进行查询,不存在时才进行插入-------该代码放在线程外面操作,避免造成死锁
//            if (!mDao.isExistsThread(mThreadInfo.getUrl(), mThreadInfo.getId())) {
//                mDao.insertThread(mThreadInfo);
//            }
            HttpURLConnection conn = null;
            RandomAccessFile raf = null;
            InputStream is = null;
            //设置下载位置
            try {
                URL url = new URL(mThreadInfo.getUrl());
                conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(5000);
                conn.setRequestMethod("GET");
                //设置下载位置
                int start = mThreadInfo.getStart() + mThreadInfo.getFinished();
                conn.setRequestProperty("Range", "bytes=" + start + "-" + mThreadInfo.getEnd());
                //设置文件写入位置
                File file = new File(DownloadService.DOWNLOAD_PATH, mFileInfo.getFileName());
                raf = new RandomAccessFile(file, "rwd");
                raf.seek(start);    //从指定位置写入   例如seek(10),将从第11个字节开始写入,跳过前面10个字节
                //开始下载
//                Intent intent = new Intent(DownloadService.ACTION_UPDATE);
                //累加已下载的进度，如果第一次下载，就是0
                mFinished += mThreadInfo.getFinished();
                if (conn.getResponseCode() == 206) {    //注意此处响应码应是206,因为上面设置了Range,表示下载的是其中的一部分
                    //读取数据
                    is = conn.getInputStream();
                    byte[] buffer = new byte[1024 * 4];
                    int length = -1;
                    while ((length = is.read(buffer)) != -1) {
                        //写入文件
                        raf.write(buffer, 0, length);
                        //将下载进度发送给Activity(使用广播)
                        mFinished += length;    //累加整个文件下载进度
                        mThreadInfo.setFinished(mThreadInfo.getFinished() + length);    //累加每个线程下载进度
                        //此处需要注意,当使用int时,注意int值的范围
//                        int progress = (int) (mFinished * 100 / mFileInfo.getLength());
//                        Log.d(TAG, "下载进度:" + mFinished + "---文件长度:" + mFileInfo.getLength() + "--百分比:" + mFinished * 100 / mFileInfo.getLength());
//                        if (System.currentTimeMillis() - time > 1000) {  //每隔1000毫秒发送一次广播
//                            time = System.currentTimeMillis();
//                            //progress = (int) (mFinished / mFileInfo.getLength() * 100);
//                            intent.putExtra("finished", progress);  //以百分比的形式传递数据
//                            intent.putExtra("id", mFileInfo.getId());    //文件id,用于判断是下载那个文件的进度
//                            mContext.sendBroadcast(intent); //发送广播
//                        }
                        //当下载暂停时,保存当前下载进度
                        if (isPause) {
                            mDao.updateThread(mThreadInfo.getUrl(), mThreadInfo.getId(), mThreadInfo.getFinished());
                            return;
                        }
                    }
                    isThreadFinished = true;  //单个线程执行完毕
                    //下载完成时,删除线程信息
//                    mDao.deleteThread(mThreadInfo.getUrl(),mThreadInfo.getId());
                    //检查是否所有线程都执行完毕.
                    checkAllThreadsFinished();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    conn.disconnect();
                    raf.close();
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
