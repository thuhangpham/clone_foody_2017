package com.thuhang.foody1703311.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.thuhang.foody1703311.models.TypeCate;

import java.util.ArrayList;
import java.util.List;

import static com.thuhang.foody1703311.util.Constant.TB_TYPE;

/**
 * Created by ThuHang on 4/1/2017.
 */

public class TypeCateHandler extends SQLiteOpenHelper{
    private SQLiteDatabase dataBase;
    private Context mContext;
    public TypeCateHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
        MyDatabaseAdapter myDatabaseAdapter =new MyDatabaseAdapter(context.getApplicationContext());
        myDatabaseAdapter.open();
        dataBase = myDatabaseAdapter.getMyDatabase();
    }
    public List<Object> getTypeCateByType(int typeId) {
        List<Object> list = new ArrayList<>();
        list.add("Danh mục");
        // Select All Query
        String selectQuery = "SELECT * FROM " + TB_TYPE +" where categoryid = "+typeId;
        Cursor cursor = dataBase.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                TypeCate cate = new TypeCate(cursor.getInt(0),cursor.getString(2), cursor.getString(1), cursor.getInt(3));
                // Adding to list
                list.add(cate);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return list;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
