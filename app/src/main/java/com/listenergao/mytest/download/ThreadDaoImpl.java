package com.listenergao.mytest.download;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ListenerGao on 2016/8/20.
 * <p/>
 * 数据库访问接口的实现类
 * <p/>
 * 注意:因为是多线程下载,所以在 增 删 改 操作数据库时,应使用同步方法,保证同一时刻只有一个线程操作数据库,避免死锁发生的概率.
 */
public class ThreadDaoImpl implements ThreadDao {
    private DBHelper dbHelper;

    public ThreadDaoImpl(Context context) {
        dbHelper = DBHelper.getInstanceDBHelper(context);
    }

    @Override
    public synchronized void insertThread(ThreadInfo threadInfo) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL(
                "insert into thread_info(thread_id,url,start,end,finished) values(?,?,?,?,?)",
                new Object[]{threadInfo.getId(), threadInfo.getUrl(), threadInfo.getStart(), threadInfo.getEnd(), threadInfo.getFinished()}
        );
        db.close();
    }

    @Override
    public synchronized void deleteThread(String url) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL(
                "delete from  thread_info where url = ? ",
                new Object[]{url}
        );
        db.close();
    }

    @Override
    public synchronized void updateThread(String url, int threadId, int finished) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL(
                "update  thread_info set finished = ? where url = ? and thread_id = ?",
                new Object[]{finished, url, threadId}
        );
        db.close();
    }

    @Override
    public List<ThreadInfo> queryThreads(String url) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<ThreadInfo> list = new ArrayList<>();
        Cursor cursor = db.rawQuery("select * from thread_info where url = ?", new String[]{url});
        while (cursor.moveToNext()) {
            ThreadInfo threadInfo = new ThreadInfo();
            threadInfo.setId(cursor.getInt(cursor.getColumnIndex("thread_id")));
            threadInfo.setUrl(cursor.getString(cursor.getColumnIndex("url")));
            threadInfo.setStart(cursor.getInt(cursor.getColumnIndex("start")));
            threadInfo.setEnd(cursor.getInt(cursor.getColumnIndex("end")));
            threadInfo.setFinished(cursor.getInt(cursor.getColumnIndex("finished")));
            list.add(threadInfo);
        }
        cursor.close();
        db.close();
        return list;
    }

    @Override
    public boolean isExistsThread(String url, int threadId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from thread_info where url = ? and thread_id = ?", new String[]{url, threadId + ""});
        boolean exists = cursor.moveToNext();
        cursor.close();
        db.close();
        return exists;
    }
}
