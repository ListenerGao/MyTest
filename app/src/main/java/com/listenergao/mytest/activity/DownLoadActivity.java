package com.listenergao.mytest.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.listenergao.mytest.R;
import com.listenergao.mytest.download.DownloadService;
import com.listenergao.mytest.download.FileInfo;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ListenerGao on 2016/8/20.
 * <p/>
 * 实现断点续传下载功能
 */
public class DownLoadActivity extends BaseActivity {
    private static final String TAG = "DownLoadActivity";
    @BindView(R.id.tv_file_name)
    TextView tvFileName;
    @BindView(R.id.pb_progressBar)
    ProgressBar pbProgressBar;
    @BindView(R.id.bt_start)
    Button btStart;
    @BindView(R.id.bt_stop)
    Button btStop;

    //测试用下载地址和文件名
    private String mDownLoadUrl = "http://sw.bos.baidu.com/sw-search-sp/software/a5165ca000166/kugou_8.0.90.19180_setup.exe";
    private String mFileName = "kugou_8.0.90.19180_setup.exe";
    private FileInfo mFileInfo;

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
        mFileInfo = new FileInfo(0,mDownLoadUrl,mFileName,0,0);

        //注册广播接收器.代码中动态注册
        IntentFilter filter = new IntentFilter();
        filter.addAction(DownloadService.ACTION_UPDATE);
        registerReceiver(mReceiver,filter);
    }

    @OnClick({R.id.bt_start, R.id.bt_stop})
    public void onClick(View view) {
        Intent intent = new Intent(this, DownloadService.class);
        switch (view.getId()) {
            case R.id.bt_start:     //开始下载
                intent.setAction(DownloadService.ACTION_START);
                intent.putExtra("fileInfo",mFileInfo);
                startService(intent);
                break;
            case R.id.bt_stop:      //停止下载
                intent.setAction(DownloadService.ACTION_STOP);
                intent.putExtra("fileInfo",mFileInfo);
                startService(intent);
                break;
        }
    }

    /**
     * 更新UI的广播接收器
     */
    BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (DownloadService.ACTION_UPDATE == intent.getAction()) {
                int finished = intent.getIntExtra("finished",0);
                pbProgressBar.setProgress(finished);
                Log.d(TAG,"广播接收的下载进度:" + finished);
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //取消广播的注册
        unregisterReceiver(mReceiver);
    }
}
