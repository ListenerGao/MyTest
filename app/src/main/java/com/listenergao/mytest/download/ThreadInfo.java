package com.listenergao.mytest.download;

/**
 * Created by ListenerGao on 2016/8/20.
 * <p/>
 * 线程下载信息
 */
public class ThreadInfo {
    /**
     * 线程Id
     */
    private int id;
    /**
     * 下载url,和下载文件的url一致
     */
    private String url;
    /**
     * 线程下载的开始位置
     */
    private int start;
    /**
     * 线程下载结束的位置
     */
    private int end;
    /**
     * 已下载进度
     */
    private int finished;

    public ThreadInfo() {
    }

    public ThreadInfo(int id, String url, int start, int end, int finished) {
        this.id = id;
        this.url = url;
        this.start = start;
        this.end = end;
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

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public int getFinished() {
        return finished;
    }

    public void setFinished(int finished) {
        this.finished = finished;
    }

    @Override
    public String toString() {
        return "ThreadInfo{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", start=" + start +
                ", end=" + end +
                ", finished=" + finished +
                '}';
    }
}
