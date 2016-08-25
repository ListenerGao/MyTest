package com.listenergao.mytest.download;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ListenerGao on 2016/8/20.
 * <p/>
 * 单例模式
 */
public class DBHelper extends SQLiteOpenHelper {
    /**
     * 数据库名称
     */
    private static final String DB_NAME = "download.db";
    /**
     * 数据库版本号
     */
    private static final int VERSION = 1;
    /**
     * 创建表的sql命令
     */
    private static final String SQL_CREATE = "create table thread_info(_id integer primary key autoincrement," +
            "thread_id integer,url text,start integer,end integer,finished integer)";
    /**
     * 删除表的sql命令
     */
    private static final String SQL_DROP = "drop table if exists thread_info";
    private static volatile DBHelper dbHelper;

    private DBHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    /**
     * 得到单例DBHelper对象
     * <p/>
     * 使用饿汉式-双重校验锁
     *
     * @param context
     * @return
     */
    public static DBHelper getInstanceDBHelper(Context context) {
        if (dbHelper == null) {     //先判断DBHelper是否实例化,如果已经实例化,就不需要再执行synchronized (DBHelper.class)获取锁对象,从而提高性能.
            synchronized (DBHelper.class) {
                if (dbHelper == null) {
                    dbHelper = new DBHelper(context);
                }
            }
        }
        return dbHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DROP);
        db.execSQL(SQL_CREATE);
    }
}
