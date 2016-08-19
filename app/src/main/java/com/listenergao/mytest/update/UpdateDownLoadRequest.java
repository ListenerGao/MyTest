package com.listenergao.mytest.update;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;

/**
 * Created by ListenerGao on 2016-8-6.
 */
public class UpdateDownLoadRequest implements Runnable {

    private String mDownLoadUrl;
    private String mLocalFilePath;
    private UpdateDownLoadListener mListener;
    private boolean isDownLoading = false;
    private float currentLength;
    private DownLoadResponseHandler mDownLoadHandler;
    private float mCompleteSize;
    private int progress;

    public UpdateDownLoadRequest(String downLoadUrl, String localFilePath, UpdateDownLoadListener listener) {
        this.mDownLoadUrl = downLoadUrl;
        this.mLocalFilePath = localFilePath;
        this.mListener = listener;
        this.isDownLoading = true;
        this.mDownLoadHandler = new DownLoadResponseHandler();
    }

    @Override
    public void run() {
        makeRequest();
    }

    //建立连接
    private void makeRequest() {
        try {
            if (!Thread.currentThread().isInterrupted()) {
                URL url = new URL(mDownLoadUrl);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setConnectTimeout(5000);
                //强制保持连接
                urlConnection.setRequestProperty("Connection", "Keep-Alive");
                urlConnection.connect();
                if(urlConnection.getResponseCode() == 200){
                    mDownLoadHandler.sendStartMessage();
                    currentLength = urlConnection.getContentLength();
                    if (!Thread.currentThread().isInterrupted()) {
                        mDownLoadHandler.sendResponseMessage(urlConnection.getInputStream());
                    }
                }else {
                    mDownLoadHandler.sendFailureMessage("网络连接失败");
                }

            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String formatPoint(float value) {
        DecimalFormat format = new DecimalFormat("0.00");
        return format.format(value);
    }

    public class DownLoadResponseHandler {
        public static final int DOWNLOAD_SUCCESS = 0;
        public static final int DOWNLOAD_FAILURE = 1;
        public static final int DOWNLOAD_START = 2;
        public static final int DOWNLOAD_FINISH = 3;
        public static final int DOWNLOAD_PROGRESS = 4;

        private Handler handler;

        public DownLoadResponseHandler() {
            handler = new Handler(Looper.getMainLooper()) {
                @Override
                public void handleMessage(Message msg) {
                    sendSelfMessage(msg);
                }
            };
        }

        private void sendSelfMessage(Message msg) {
            switch (msg.what) {
                case DOWNLOAD_START:
                    handleStartMessage();
                    break;
                case DOWNLOAD_PROGRESS:
                    handleProgressMessage((Integer) msg.obj);
                    break;
                case DOWNLOAD_SUCCESS:
                    handleSuccessMessage();
                    break;
                case DOWNLOAD_FAILURE:
                    handleFailreMessage((String) msg.obj);
                    break;
                case DOWNLOAD_FINISH:
                    handleFinishMessage();
                    break;
            }
        }

        private void sendMessage(Message msg) {
            handler.sendMessage(msg);
        }

        private void sendStartMessage() {
            sendMessage(handler.obtainMessage(DOWNLOAD_START, null));
        }

        private void sendProgressMessage(int progress) {
            sendMessage(handler.obtainMessage(DOWNLOAD_PROGRESS, progress));
        }

        private void sendSuccessMessage() {
            sendMessage(handler.obtainMessage(DOWNLOAD_SUCCESS, null));
        }

        private void sendFinishMessage() {
            sendMessage(handler.obtainMessage(DOWNLOAD_FINISH, null));
        }

        private void sendFailureMessage(String errorMessage) {
            sendMessage(handler.obtainMessage(DOWNLOAD_FAILURE,errorMessage));
        }

        public void sendResponseMessage(InputStream inputStream) {
            RandomAccessFile randomAccessFile = null;
            mCompleteSize = 0;
            int limit = 0;
            try {
                int length = -1;
                byte[] buffer = new byte[1024 * 8];
                randomAccessFile = new RandomAccessFile(mLocalFilePath, "rwd");
                while ((length = inputStream.read(buffer)) != -1) {
                    if (isDownLoading && mCompleteSize < currentLength) {
                        randomAccessFile.write(buffer, 0, length);
                        mCompleteSize += length;
                        progress = (int) (mCompleteSize / currentLength * 100);
                        Log.e("progress", limit+"//////"+ progress + "///" + mCompleteSize + "///" + currentLength + "///" + (int) (mCompleteSize / currentLength * 100) + "////" + length);
                        if (limit % 30 == 0 && progress <= 100) {
                            sendProgressMessage(progress);
                            //handleProgressMessage(progress);
                        } else if(progress == 100){
                            sendFinishMessage();
                            isDownLoading = false;
                        }
                        limit++;
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
                sendFailureMessage("IO流异常");
            } finally {
                try {
                    if (inputStream != null)
                        inputStream.close();
                    if (randomAccessFile != null)
                        randomAccessFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private void handleFinishMessage() {
            mListener.onFinished(mCompleteSize);
        }

        private void handleFailreMessage(String errorMessage) {
            mListener.onFailure(errorMessage);
        }

        private void handleSuccessMessage() {

        }

        private void handleProgressMessage(int progress) {
            mListener.onProgressChanged(progress);
        }

        private void handleStartMessage() {
            mListener.onStarted();
        }


    }
}
