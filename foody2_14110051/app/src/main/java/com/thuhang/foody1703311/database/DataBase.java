package com.thuhang.foody1703311.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ThuHang on 3/23/2017.
 */

public class DataBase extends SQLiteOpenHelper {
    private final String KEY_ID = "id";
    public DataBase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);

    }
    public Cursor getData(String sql){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql,null);
        db.close();
        return c;
    }
    public void queryData(String sql){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sql);
        db.close();
    }
   /* public void insert(ContentValues values, String table) {
        SQLiteDatabase db = this.getWritableDatabase();
        // Inserting Row
        db.insert(table, null, values);
        //db.close(); // Closing database connection
    }*/
    // Getting Count
    public int getCount(String sql) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        cursor.close();
        // return count
        return cursor.getCount();
    }
    // Updating
    public int update(String table, ContentValues values, int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        // updating row
        return db.update(table, values, KEY_ID + "= ?", new String[]{String.valueOf(id)});
    }
    // Deleting
    public void delete(String table, int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(table, KEY_ID + "= ?",
                new String[] { String.valueOf(id) });
        db.close();
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
