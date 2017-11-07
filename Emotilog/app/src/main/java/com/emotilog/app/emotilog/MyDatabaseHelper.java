package com.emotilog.app.emotilog;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by Chen on 2017/11/6.
 */

public class MyDatabaseHelper extends SQLiteOpenHelper {
    public static final String CREATE_ENTRY = "CREATE TABLE Entry ("
            + "id  integer PRIMARY KEY Autoincrement ,"
            + "state text ,"
            + "emoji_id integer ,"
            + "time text,"
            + "image blob )";


    private Context myContent;

    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        myContent = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建数据库的同时创建Book表
        db.execSQL(CREATE_ENTRY);
        //提示数据库创建成功
        Toast.makeText(myContent, "Database built", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}