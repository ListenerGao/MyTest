package com.listenergao.mytest.update;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by ListenerGao on 2016-8-6.
 */
public class UpdateManager {

    private static UpdateManager manager;
    private ThreadPoolExecutor executor;
    private UpdateDownLoadRequest request;

    private UpdateManager() {
        executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
    }

    public static UpdateManager getInstance(){
        if(manager == null){
            synchronized (UpdateManager.class){
                if(manager == null)
                    return manager = new UpdateManager();
            }
        }
        return manager;
    }

    public void startDownload(String downloadUrl,String localPath,UpdateDownLoadListener listener){
        if(request != null)
            return;
        else {
            checkLocalPath(localPath);
            request = new UpdateDownLoadRequest(downloadUrl,localPath,listener);
            executor.submit(request);
        }

    }

    /**
     * 检查存储路径
     * @param localPath
     */
    private void checkLocalPath(String localPath) {
        File dir = new File(localPath.substring(0,localPath.lastIndexOf("/")+1));
        if(!dir.exists()){
            dir.mkdir();
        }
        File file = new File(localPath);
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
