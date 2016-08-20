package com.listenergao.mytest.download;

import java.io.Serializable;

/**
 * Created by ListenerGao on 2016/8/20.
 * <p/>
 * 文件信息
 */
public class FileInfo implements Serializable{
    /**
     * 文件Id
     */
    private int id;
    /**
     * 下载的url
     */
    private String url;
    /**
     * 文件名称
     */
    private String fileName;
    /**
     * 文件长度
     */
    private int length;
    /**
     * 已下载进度
     */
    private int finished;

    public FileInfo() {
    }

    public FileInfo(int id, String url, String fileName, int length, int finished) {
        this.id = id;
        this.url = url;
        this.fileName = fileName;
        this.length = length;
        this.finished = finished;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getFinished() {
        return finished;
    }

    public void setFinished(int finished) {
        this.finished = finished;
    }

    @Override
    public String toString() {
        return "FileInfo{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", fileName='" + fileName + '\'' +
                ", length=" + length +
                ", finished=" + finished +
                '}';
    }
}
