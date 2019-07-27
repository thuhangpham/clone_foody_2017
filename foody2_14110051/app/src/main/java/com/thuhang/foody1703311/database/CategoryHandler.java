package com.thuhang.foody1703311.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.thuhang.foody1703311.models.Categories;

import java.util.ArrayList;
import java.util.List;

import static com.thuhang.foody1703311.util.Constant.TB_CATEGORY;

/**
 * Created by ThuHang on 3/24/2017.
 */

public class CategoryHandler extends SQLiteOpenHelper {
    SQLiteDatabase dataBase;
    public CategoryHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        MyDatabaseAdapter myDatabaseAdapter = new MyDatabaseAdapter(context.getApplicationContext());
        myDatabaseAdapter.open();
        dataBase = myDatabaseAdapter.getMyDatabase();
    }
    public List<Categories> getAllCategory() {
        dataBase.beginTransaction();
        List<Categories> list = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TB_CATEGORY;
        Cursor cursor = dataBase.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Categories category = new Categories(cursor.getString(0), cursor.getString(2), cursor.getString(1));
                // Adding to list
                list.add(category);
            } while (cursor.moveToNext());
        }
        cursor.close();
        dataBase.endTransaction();
        return list;
    }
//    public Categories getCategoryById(int cateId) {
//        Categories c = null;
//        // Select All Query
//        String selectQuery = "SELECT * FROM " + CATEGORY + " where id = " + cateId;
//        Cursor cursor = dataBase.rawQuery(selectQuery, null);
//        // looping through all rows and adding to list
//        if (cursor.moveToFirst()) {
//                c = new Categories(cursor.getString(0), cursor.getBlob(2), cursor.getString(1));
//        }
//        return c;
//    }
    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
