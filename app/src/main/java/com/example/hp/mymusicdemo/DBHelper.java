package com.example.hp.mymusicdemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by HP on 2018/1/13.
 */

public class DBHelper extends SQLiteOpenHelper {
    private static final String String_DB_NAME="SQLDATE";
    private static final String String_TBL_NAME="TBLDATE";
    private static final String String_CREATE_TBL="creat_TABLE" +"mydate(hailhydra)";
    private SQLiteDatabase db;


    public DBHelper(Context context) {
        super(context, String_DB_NAME, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        this.db=sqLiteDatabase;
        db.execSQL(String_CREATE_TBL);
    }

    public void insert(ContentValues values) {
        SQLiteDatabase db = getWritableDatabase();
        db.insert(String_TBL_NAME, null, values);
        db.close();
    }
    public Cursor query() {
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.query(String_TBL_NAME, null, null,
                null, null, null, null);
        return c;
    }
    public void del(int id) {
        if (db == null)
            db = getWritableDatabase();
        db.delete(String_TBL_NAME, "_id=?", new String[] { String.valueOf(id) });
    }
    public void close() {
        if (db != null)
            db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }
}
