package com.listenergao.mytest.download;

import java.util.List;

/**
 * Created by ListenerGao on 2016/8/20.
 *
 * 数据库访问接口
 */
public interface ThreadDao {
    /**
     * 插入线程信息
     * @param threadInfo
     */
    void insertThread(ThreadInfo threadInfo);

    /**
     * 删除线程信息(根据下载的url,将线程信息全部删除)
     * @param url
     */
    void deleteThread(String url);

    /**
     * 更新线程下载进度
     * @param url
     * @param threadId
     * @param finished
     */
    void updateThread(String url,int threadId,int finished);

    /**
     * 查询文件下载的线程信息
     * @param url
     * @return
     */
    List<ThreadInfo> queryThreads(String url);

    /**
     * 查询线程信息是否存在
     * @param url
     * @param threadId
     * @return
     */
    boolean isExistsThread(String url,int threadId);

}
