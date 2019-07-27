package com.thuhang.foody1703311.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.thuhang.foody1703311.models.Type;

import static com.thuhang.foody1703311.util.Constant.TB_TYPE;

/**
 * Created by ThuHang on 4/5/2017.
 */

public class TypeHandler  extends SQLiteOpenHelper {
    private SQLiteDatabase dataBase;
    public TypeHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        MyDatabaseAdapter myDatabaseAdapter =new MyDatabaseAdapter(context.getApplicationContext());
        myDatabaseAdapter.open();
        dataBase = myDatabaseAdapter.getMyDatabase();
    }
    public Type getTypeById(int typeId) {
        Type type =new Type();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TB_TYPE +" where id = "+typeId;
        Cursor cursor = dataBase.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            type = new Type(cursor.getInt(0),cursor.getString(2),cursor.getString(1), cursor.getInt(3));
        }
        cursor.close();
        return type;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
