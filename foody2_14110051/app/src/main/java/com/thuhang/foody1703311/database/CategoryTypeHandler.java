package com.thuhang.foody1703311.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.thuhang.foody1703311.models.CategoryType;

import java.util.ArrayList;
import java.util.List;

import static com.thuhang.foody1703311.util.Constant.DATABASE_NAME;
import static com.thuhang.foody1703311.util.Constant.TB_CATEGORYTYPE;
import static com.thuhang.foody1703311.util.Constant.VERSION;

/**
 * Created by ThuHang on 4/16/2017.
 */

public class CategoryTypeHandler extends SQLiteOpenHelper {
    SQLiteDatabase dataBase;
    public CategoryTypeHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        MyDatabaseAdapter myDatabaseAdapter = new MyDatabaseAdapter(context.getApplicationContext(), DATABASE_NAME, null, VERSION);
        myDatabaseAdapter.open();
        dataBase = myDatabaseAdapter.getMyDatabase();
    }
    public List<Object> getALlCateType(){
        List<Object> objectList = new ArrayList<>();
        String query = "select * from "+ TB_CATEGORYTYPE;
        Cursor c = dataBase.rawQuery(query,null);
        if(c.moveToFirst()){
            do{
                CategoryType categoryType = new CategoryType(c.getInt(0), c.getString(1));
                objectList.add(categoryType);
            }while (c.moveToNext());
        }
        c.close();
        return objectList;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
