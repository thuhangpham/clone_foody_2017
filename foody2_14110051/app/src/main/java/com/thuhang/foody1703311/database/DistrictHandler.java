package com.thuhang.foody1703311.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.thuhang.foody1703311.models.District;

import java.util.ArrayList;
import java.util.List;

import static com.thuhang.foody1703311.util.Constant.DATABASE_NAME;
import static com.thuhang.foody1703311.util.Constant.TB_DISTRICT;
import static com.thuhang.foody1703311.util.Constant.VERSION;

/**
 * Created by ThuHang on 3/24/2017.
 */

public class DistrictHandler extends SQLiteOpenHelper {
    SQLiteDatabase dataBase;
    private Context context;
    public DistrictHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        MyDatabaseAdapter myDatabaseAdapter =new MyDatabaseAdapter(context.getApplicationContext());
        myDatabaseAdapter.open();
        this.context=context;
        dataBase = myDatabaseAdapter.getMyDatabase();
    }
    public List<Object> getAllDistrictByCity(int idCity) {
        List<Object> list = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TB_DISTRICT + " where CITYID = " + idCity +" order by name";
        Cursor cursor = dataBase.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                District district= new District(cursor.getString(0), cursor.getString(2));
                StreetHandler streetHandler= new StreetHandler(context,DATABASE_NAME,null,VERSION);
                district.setStreets(streetHandler.getStreetByDistrict(Integer.parseInt(district.getDistrictId())));
                district.setStreetCount(district.getStreets().size());
                // Adding mn to list
                list.add(district);
            } while (cursor.moveToNext());
        }
       cursor.close();
        // return contact list
        return list;
    }
    public int getIdByName(String name){
        String selectQuery = "SELECT id FROM " + TB_DISTRICT + " where name like '%" + name+"%'";
        Cursor cursor = dataBase.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()){
            return cursor.getInt(0);
        }
        return  -1;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
