package com.listenergao.mytest.data;

import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.listenergao.mytest.R;
import com.listenergao.mytest.download.DownloadService;
import com.listenergao.mytest.download.FileInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ListenerGao on 2016/8/24.
 * <p/>
 * 多个文件文件显示列表
 */
public class FilesAdapter extends BaseAdapter {
    private Context mContext;
    private List<FileInfo> mFileInfos;
    private Messenger mMessenger;

    public FilesAdapter(Context mContext, List<FileInfo> mFileInfos) {
        this.mContext = mContext;
        this.mFileInfos = mFileInfos;
    }

    @Override
    public int getCount() {
        return mFileInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return mFileInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final FileInfo mFileInfo = mFileInfos.get(position);
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_download, null);
            holder = new ViewHolder(convertView);
            //下载过程中,界面需要不断刷新,将下面代码放在此处,减少执行次数.
            holder.tvFileName.setText(mFileInfo.getFileName());
            holder.btStart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, DownloadService.class);
                    intent.setAction(DownloadService.ACTION_START);
                    intent.putExtra("fileInfo", mFileInfo);
                    mContext.startService(intent);

//                    Message msg = new Message();
//                    msg.what = DownloadService.MSG_START;
//                    msg.obj = mFileInfo;
//                    try {
//                        mMessenger.send(msg);
//                    } catch (RemoteException e) {
//                        e.printStackTrace();
//                    }
                }
            });
            holder.btStop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, DownloadService.class);
                    intent.setAction(DownloadService.ACTION_STOP);
                    intent.putExtra("fileInfo", mFileInfo);
                    mContext.startService(intent);

//                    Message msg = new Message();
//                    msg.what = DownloadService.MSG_STOP;
//                    msg.obj = mFileInfo;
//                    try {
//                        mMessenger.send(msg);
//                    } catch (RemoteException e) {
//                        e.printStackTrace();
//                    }
                }
            });
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.pbProgressBar.setProgress(mFileInfo.getFinished());
        return convertView;
    }

    /**
     * 对外提供更新进度条方法
     *
     * @param id       文件id,和position值一致
     * @param progress 进度
     */
    public void updateProgress(int id, int progress) {
        FileInfo fileInfo = mFileInfos.get(id);
        fileInfo.setFinished(progress);
        notifyDataSetChanged();
    }

    public void setMessenger(Messenger messenger) {
        this.mMessenger = messenger;
    }


    static class ViewHolder {
        @BindView(R.id.tv_file_name)
        TextView tvFileName;
        @BindView(R.id.pb_progressBar)
        ProgressBar pbProgressBar;
        @BindView(R.id.bt_start)
        Button btStart;
        @BindView(R.id.bt_stop)
        Button btStop;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
