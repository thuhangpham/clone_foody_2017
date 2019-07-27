package com.thuhang.foody1703311.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.thuhang.foody1703311.models.City;

import java.util.ArrayList;
import java.util.List;

import static com.thuhang.foody1703311.util.Constant.DATABASE_NAME;
import static com.thuhang.foody1703311.util.Constant.TB_CITY;
import static com.thuhang.foody1703311.util.Constant.VERSION;

/**
 * Created by ThuHang on 3/24/2017.
 */

public class CityHandler extends SQLiteOpenHelper {
    private SQLiteDatabase dataBase;
    private Context mContext;

    public CityHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
        MyDatabaseAdapter myDatabaseAdapter =new MyDatabaseAdapter(context.getApplicationContext());
        myDatabaseAdapter.open();
        dataBase = myDatabaseAdapter.getMyDatabase();
    }
    public int getIdByName(String name){
        String selectQuery = "SELECT id FROM " + TB_CITY + " where name = '" + name+"'";
        Cursor cursor = dataBase.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()){
            return cursor.getInt(0);
        }
        return  -1;
    }
    public List<Object> getAllCity() {
        List<Object> list = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TB_CITY;
        Cursor cursor = dataBase.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                City city = new City(cursor.getString(0), cursor.getString(1));
                DistrictHandler districtHandler = new DistrictHandler(mContext, DATABASE_NAME,null, VERSION);
                List<Object> districtList = districtHandler.getAllDistrictByCity(Integer.parseInt(city.getId()));
                city.setDistricts(districtList);
                // Adding mn to list
                list.add(city);
            } while (cursor.moveToNext());
        }
        cursor.close();
        // return contact list
        return list;
    }
    public City getCityById(int cityId) {
        City city = null;
        // Select All Query
        String selectQuery = "SELECT * FROM " + TB_CITY + " where id = " +cityId;
        Cursor cursor = dataBase.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
                city = new City(cursor.getString(0), cursor.getString(1));
        }
        cursor.close();
        return city;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
