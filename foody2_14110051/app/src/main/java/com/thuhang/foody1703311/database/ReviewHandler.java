package com.thuhang.foody1703311.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.thuhang.foody1703311.models.Review;

import java.util.ArrayList;
import java.util.List;

import static com.thuhang.foody1703311.util.Constant.TB_REVIEW;

/**
 * Created by thuha on 4/19/2017.
 */

public class ReviewHandler extends SQLiteOpenHelper {
    private SQLiteDatabase dataBase;
    private Context mContext;
    private  MyDatabaseAdapter myDatabaseAdapter;
    public ReviewHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        myDatabaseAdapter =new MyDatabaseAdapter(context.getApplicationContext());
        myDatabaseAdapter.open();
        dataBase = myDatabaseAdapter.getMyDatabase();
        mContext = context;
    }
    public void insertReview(Object o){
        Review review = (Review)o;
        if(getCount(review.getComment())==0) {
            Log.e("ínert", review.getName());
            ContentValues values = new ContentValues();
            values.put("NAME", review.getName());
            values.put("COMMENT", review.getComment());
            values.put("COMMENTTRIM", "");
            values.put("AVATAR", review.getAvatar());
            values.put("RATING", review.getName());
            values.put("ITEMID", review.getItemId());
            values.put("REVIEWURL", "");
            dataBase.insert("REVIEW", null, values);
        }
    }
    public int getCount(String cmt) {
        Cursor c = null;
        try {
            String query = "select count(*) from " + TB_REVIEW +"  where comment = ?";
            c = dataBase.rawQuery(query, new String[]{cmt});
            if (c.moveToFirst()) {
                return c.getInt(0);
            }
            return 0;
        }
        finally {
            if (c != null) {
                c.close();
            }
        }
    }
    public List<Object> getReviewByItemId(int itemId) {
        List<Object> list = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TB_REVIEW +" where itemid = "+itemId+" limit 2";
        Cursor cursor = dataBase.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Review review = new Review();
                review.setId(cursor.getInt(0));
                review.setName(cursor.getString(1));
                review.setRating(cursor.getDouble(2));
                review.setComment(cursor.getString(4));
                review.setAvatar(cursor.getString(5));
                review.setItemId(cursor.getInt(6));
                // Adding mn to list
                list.add(review);
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
