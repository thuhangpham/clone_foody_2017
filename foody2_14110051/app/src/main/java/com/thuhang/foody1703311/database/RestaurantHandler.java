package com.thuhang.foody1703311.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.thuhang.foody1703311.models.Restaurant;

import java.util.ArrayList;
import java.util.List;

import static com.thuhang.foody1703311.util.Constant.LIMIT;
import static com.thuhang.foody1703311.util.Constant.STR_DISTRICT_ID;
import static com.thuhang.foody1703311.util.Constant.TABLE_RESTAURANT;

/**
 * Created by ThuHang on 3/24/2017.
 */

public class RestaurantHandler extends SQLiteOpenHelper {
    private SQLiteDatabase dataBase;

    private Context mContext;
    private  MyDatabaseAdapter myDatabaseAdapter;
    public RestaurantHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        myDatabaseAdapter =new MyDatabaseAdapter(context.getApplicationContext());
        myDatabaseAdapter.open();
        dataBase = myDatabaseAdapter.getMyDatabase();
        mContext = context;
    }
   public List<Restaurant> getRestaurantById(int cateId, int typeId,int districtId,int streetId, int cityId, int cateTypeId, int offset) {

        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_RESTAURANT +
                   " where districtid = " + districtId +
                        " and typeid = " + typeId +
                        " and categoryid = "+ cateId+
                        " and cateTypeId = "+ cateTypeId+
                " limit "+LIMIT+" offset " +offset;
       if(districtId<0 && typeId>0)
                selectQuery = "select * from " +TABLE_RESTAURANT +
                   " where typeid = " + typeId +
                        " and categoryid = "+ cateId+
                        " and CITYID = "+cityId +
                        " and cateTypeId = "+ cateTypeId+
                        " limit "+LIMIT+" offset " +offset;
       else if(typeId<0 && districtId<0)
                selectQuery = "select * from " +TABLE_RESTAURANT + " where"+
                        " categoryid = "+ cateId+
                        " and CITYID = "+ cityId+
                        " and cateTypeId = "+ cateTypeId+
                        " limit "+LIMIT+" offset " +offset;
       else if(typeId<0 && districtId>0)
                selectQuery = "select * from " +TABLE_RESTAURANT + " where "+
                        STR_DISTRICT_ID +" = " +districtId+
                        " and categoryid = "+ cateId+
                        " and cateTypeId = "+ cateTypeId+
                        " limit "+LIMIT+" offset " +offset;
       Log.i("query", selectQuery);
       Cursor cursor = dataBase.rawQuery(selectQuery, null);
        // return contact list
        return getRestaurant(cursor);
    }
    private List<Restaurant> getRestaurant(Cursor cursor){
        List<Restaurant> list = new ArrayList<>();
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Restaurant restaurant = new Restaurant();
                restaurant.setId(cursor.getInt(10));
                restaurant.setItemId(cursor.getInt(0));
                restaurant.setAddress(cursor.getString(1));
                restaurant.setName(cursor.getString(2));
                restaurant.setTotalReviews(cursor.getInt(3));
                restaurant.setImg(cursor.getString(4));
                restaurant.setDistrictId(cursor.getInt(5));
                restaurant.setAveragePoint(cursor.getDouble(6));
                restaurant.setCateId(cursor.getInt(7));
                restaurant.setTypeId(cursor.getInt(8));
                restaurant.setTotalPhoto(cursor.getInt(9));
                // Adding mn to list
                list.add(restaurant);
            } while (cursor.moveToNext());
        }
        cursor.close();
        // return contact list
        return list;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
