package com.thuhang.foody1703311.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.thuhang.foody1703311.models.RestaurantFood;

import java.util.ArrayList;
import java.util.List;

import static com.thuhang.foody1703311.util.Constant.DATABASE_NAME;
import static com.thuhang.foody1703311.util.Constant.LIMIT;
import static com.thuhang.foody1703311.util.Constant.STR_DISTRICT_ID;
import static com.thuhang.foody1703311.util.Constant.TABLE_RESTAURANT;
import static com.thuhang.foody1703311.util.Constant.VERSION;

/**
 * Created by ThuHang on 4/5/2017.
 */

public class RestaurantFoodHandler extends SQLiteOpenHelper {
    private SQLiteDatabase dataBase;

    private Context mContext;
    private  MyDatabaseAdapter myDatabaseAdapter;

    public RestaurantFoodHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        myDatabaseAdapter =new MyDatabaseAdapter(context.getApplicationContext());
        myDatabaseAdapter.open();
        dataBase = myDatabaseAdapter.getMyDatabase();
        mContext = context;
    }
    public List<RestaurantFood> getRestaurantFoodById(int cateId, int typeId, int districtId, int cityId, int offset) {
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_RESTAURANT +
                " where districtid = " + districtId +
                " and typeid = " + typeId +
                " and categoryid = "+ cateId+
                " limit "+LIMIT+" offset " +offset;
        if(districtId<0 && typeId>0)
            selectQuery = "select * from " +TABLE_RESTAURANT +
                    " where typeid = " + typeId +
                    " and categoryid = "+ cateId+
                    " and CITYID = "+cityId +
                    " limit "+LIMIT+" offset " +offset;
        else if(typeId<0 && districtId<0)
            selectQuery = "select * from " +TABLE_RESTAURANT + " where"+
                    " categoryid = "+ cateId+
                    " and CITYID = "+ cityId+
                    " limit "+LIMIT+" offset " +offset;
        else if(typeId<0 && districtId>0)
            selectQuery = "select * from " +TABLE_RESTAURANT + " where "+
                    STR_DISTRICT_ID +" = " +districtId+
                    " and categoryid = "+ cateId+"" +
                    " limit "+LIMIT+" offset " +offset;
        Log.i("Food query", selectQuery);
        Log.i("Food:city,cate,ty,dis ", cityId+ " "+ cateId+"  "+ typeId+" "+ districtId);
        Cursor cursor = dataBase.rawQuery(selectQuery, null);
        // return contact list
        return getRestaurant(cursor);
    }
    private List<RestaurantFood> getRestaurant(Cursor cursor){
        List<RestaurantFood> list = new ArrayList<>();
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                RestaurantFood restaurant = new RestaurantFood();
                restaurant.setId(cursor.getInt(10));
                restaurant.setAddress(cursor.getString(1));
                restaurant.setName(cursor.getString(2));
                restaurant.setImg(cursor.getString(4));
                restaurant.setDistrictId(cursor.getInt(5));
                restaurant.setCateId(cursor.getInt(7));
                restaurant.setTypeId(cursor.getInt(8));
                TypeHandler typeHandler = new TypeHandler(mContext,DATABASE_NAME, null, VERSION);
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
