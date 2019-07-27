package com.thuhang.foody1703311.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.thuhang.foody1703311.models.Street;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thuha on 5/18/2017.
 */

public class StreetHandler extends SQLiteOpenHelper {
    private SQLiteDatabase dataBase;
    private Context mContext;
    private  MyDatabaseAdapter myDatabaseAdapter;
    public StreetHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        myDatabaseAdapter =new MyDatabaseAdapter(context.getApplicationContext());
        myDatabaseAdapter.open();
        dataBase = myDatabaseAdapter.getMyDatabase();
        mContext = context;
    }
    public int countStreet(int idDistrict){
        int c=0;
        String selectQuery = "SELECT count(*) FROM street,district where district.id =street.districtId and street.districtId=" + idDistrict;
        Cursor cursor = dataBase.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        c=cursor.getInt(0);
        return c;
    }
    public List<Street> getStreetByDistrict(int idDistrict) {
        List<Street> list = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT * FROM street where districtId = " + idDistrict +" order by name";
        Cursor cursor = dataBase.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Street street = new Street();
                street.setDistrictId(cursor.getInt(1));
                street.setId(cursor.getInt(0));
                street.setName(cursor.getString(2));
                // Adding mn to list
                list.add(street);
            } while (cursor.moveToNext());
        }
        cursor.close();
        // return contact list
        return list;
    }
    public int getIdByName(String name, int id){
        String selectQuery = "SELECT id FROM street where name like '%" + name+"%' and districtid = " +id;
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
