package com.thuhang.foody1703311.presenters;

import android.content.Context;
import android.os.AsyncTask;

import com.thuhang.foody1703311.database.CityHandler;
import com.thuhang.foody1703311.interfaces.ICityView;

import java.util.ArrayList;
import java.util.List;

import static com.thuhang.foody1703311.util.Constant.DATABASE_NAME;
import static com.thuhang.foody1703311.util.Constant.VERSION;

/**
 * Created by thuha on 5/13/2017.
 */

public class PresenterCity {
    private ICityView mIView;
    private CityHandler cityHandler;
    private Context mContext;
    private List<Object> cityArrayList = new ArrayList<>();
    public PresenterCity(ICityView mIView, Context mContext) {
        this.mIView = mIView;
        this.mContext = mContext;
        cityHandler = new CityHandler(mContext, DATABASE_NAME,null, VERSION);
    }
    public void getALlCity() {
        GetCates getCates = new GetCates();
        getCates.execute();
    }
    class GetCates extends AsyncTask<String, Object, List<Object>> {

        @Override
        protected List<Object> doInBackground(String... params) {
            cityArrayList  = cityHandler.getAllCity();
            return cityArrayList;
        }
        @Override
        protected void onPostExecute(List<Object> categories) {
            if(categories.size()>0)
                mIView.getCity(categories);
        }
    }
}
