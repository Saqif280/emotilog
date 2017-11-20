package com.emotilog.app.emotilog;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chen on 2017/11/6.
 * this activity is for SQLite helper
 */

public class MyDatabaseHelper extends SQLiteOpenHelper implements BaseColumns {
    public static final int DATABASE_VERSION = 1;

    // Database Name
    public static final String DATABASE_NAME = "entries.db";

    // Contacts table name
    public static final String TABLE_ENTRYS = "entries";

    private Context myContent;
    private Context con;

    //Columns names
    private static final String _ID = BaseColumns._ID;
    private static final String KEY_TEXT = "text";
    private static final String KEY_PHOTO = "photo";
    private static final String KEY_DATE_TIME = "date";
    private static final String KEY_FEALING = "fealing";
    private static final String KEY_LOCATION = "location";
    private static final String KEY_SHAKESCORE = "shakescore";

    /*public static final String CREATE_ENTRY = "CREATE TABLE Entry ("
            + "id  integer PRIMARY KEY Autoincrement ,"
            + "state text ,"
            + "emoji_id integer ,"
            + "time text,"
            + "image blob )";
*/


    public MyDatabaseHelper(Context context, String name,
                            SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
        myContent=context;
    }

    public MyDatabaseHelper(Context context) {
        super(context, "entries.db", null, 1);
        con = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //create the table

        String CREATE_TABLE_ENTRY = "CREATE TABLE " + TABLE_ENTRYS  + "("
                + _ID  + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + KEY_TEXT + " TEXT, "
                + KEY_PHOTO+ " BLOB, "
                + KEY_DATE_TIME + " TEXT, "
                + KEY_FEALING + " INTEGER,"
                + KEY_SHAKESCORE + " INTEGER,"
                + KEY_LOCATION+ " TEXT )";
        Log.e("string create table","ok");
        db.execSQL(CREATE_TABLE_ENTRY);
        //Toast.makeText(myContent, "Database built", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
                          int newVersion) {//update the database
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ENTRYS);
        onCreate(db);
    }

    public int addEntry(Entry entry) {//add entry
        SQLiteDatabase db = this.getWritableDatabase();
        Log.e("my tag","adding entry with fealing"+entry.getFealing());
        ContentValues values = new ContentValues();
        values.put(KEY_TEXT, entry.getText());
        values.put(KEY_PHOTO, entry.getPhoto());
        values.put(KEY_DATE_TIME, entry.getDate_Time());
        values.put(KEY_FEALING, entry.getFealing());
        values.put(KEY_SHAKESCORE, entry.getShakescore());
        values.put(KEY_LOCATION, entry.getLocation());
        // Inserting Row
        long entry_id=db.insert(TABLE_ENTRYS, null, values);
        // Closing database connection
        db.close();
        return  (int)entry_id;
    }

    public Entry getEntry(int id) {//get entry from table
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor =  db.rawQuery( "select * from "+TABLE_ENTRYS+" where "+_ID+"="+id+"", null );
        //Cursor cursor = db.query(TABLE_ENTRYS, null, null, null, null, null, null, null);
        Entry e= new Entry();
        while(cursor.moveToNext()) {
            e.ID = cursor.getInt(cursor.getColumnIndex(_ID));
            e.TEXT = cursor.getString(cursor.getColumnIndex(KEY_TEXT));
            e.PHOTO = cursor.getBlob(cursor.getColumnIndex(KEY_PHOTO));
            e.DATE_TIME = cursor.getString(cursor.getColumnIndex(KEY_DATE_TIME));
            //Log.e("get entry",""+ cursor.getInt(cursor.getColumnIndex(KEY_FEALING)));
            e.FEALING = cursor.getInt(cursor.getColumnIndex(KEY_FEALING));
            e.SHAKESCORE = cursor.getInt(cursor.getColumnIndex(KEY_SHAKESCORE));
            e.LOCATION = cursor.getString(cursor.getColumnIndex(KEY_LOCATION));

        }
        // Closing database connection
        db.close();
        return e;
    }
    public List<Entry> getAllEntry() {//get all entry by cursor
        List<Entry> entryList = new ArrayList<Entry>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_ENTRYS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Entry entry = new Entry();
                entry.ID=(Integer.parseInt(cursor.getString(0)));
                entry.TEXT=(cursor.getString(1));
                entry.PHOTO=(cursor.getBlob(2));
                entry.DATE_TIME=(cursor.getString(3));
                entry.FEALING=(cursor.getInt(4));
                entry.SHAKESCORE=(cursor.getInt(5));
                entry.LOCATION=(cursor.getString(6));
                //Log.e("what",""+entryList.size());
                // Adding contact to list
                entryList.add(entry);
            } while (cursor.moveToNext());
        }
        // return entry list
        return entryList;
    }
}