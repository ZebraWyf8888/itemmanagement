package com.example.he.studenmanagement.tools;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 单例模式(数据库操作)
 * Created by he on 2020/06/3.
 */
public class myDatabaseHelper extends SQLiteOpenHelper {
    private static myDatabaseHelper instance;
    public static final String CREATE_ADMIN = "create table admin(id integer primary key autoincrement, name text,password text)";//创建管理员表

    public static final String CREATE_ITEM = "create table item (id text  primary key,name text,endtime text,complete text,starttime text,progress text, state text,person text)";//创建项目信息表

    public static final String CREATE_PRODUCT = "create table productmanager(id integer primary key autoincrement, name text,password text)";//创建产品经理表


    private myDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    /**
     * onCreateITEM
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_ADMIN);
        db.execSQL(CREATE_PRODUCT);
        db.execSQL(CREATE_ITEM);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public static myDatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new myDatabaseHelper(context, "ItemManagement.db", null, 2);
        }
        return instance;

    }


}
