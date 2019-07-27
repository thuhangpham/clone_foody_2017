package com.thuhang.foody1703311.presenters;

import android.content.Context;
import android.util.Log;

import com.thuhang.foody1703311.R;
import com.thuhang.foody1703311.database.RestaurantHandler;
import com.thuhang.foody1703311.interfaces.IPlacesLoadmore;
import com.thuhang.foody1703311.interfaces.IPlacesView;
import com.thuhang.foody1703311.interfaces.IPresenterRestaurant;
import com.thuhang.foody1703311.models.Restaurant;
import com.thuhang.foody1703311.retrofit.ApiUtils;
import com.thuhang.foody1703311.retrofit.IMyService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.thuhang.foody1703311.util.Constant.DATABASE_NAME;
import static com.thuhang.foody1703311.util.Constant.VERSION;

/**
 * Created by ThuHang on 4/3/2017.
 */

public class PresenterRestaurant implements IPresenterRestaurant {
    private IPlacesView mIView;
    private IPlacesLoadmore mIBaseLoadMore;
    private Context mContext;
    private RestaurantHandler restaurantHandler;
    private IMyService mService;
    public PresenterRestaurant(IPlacesView mIView,Context mContext/*, IPlacesLoadmore iBaseLoadMore*/) {
        this.mIView = mIView;
        this.mContext = mContext;
        /*this.mIBaseLoadMore = iBaseLoadMore;*/
        mService = ApiUtils.getService();
        restaurantHandler = new RestaurantHandler(mContext, DATABASE_NAME, null, VERSION);
    }
    @Override
    public void getAllRestaurantById(int cityId, int cateId, int districtId,int streetId, int typeId, int cateTypeId) {
        mIView.showLoading();
        final List<Restaurant> resList = restaurantHandler.getRestaurantById(cateId,typeId,districtId,streetId,cityId,cateTypeId,0);
        if(resList.size()>0) {
            mIView.hideLoading("OK");
            mIView.configRestaurant(resList);
        }
        else
            mIView.error("Chưa có địa điểm nào.");
    }

    @Override
    public void getRestaurantByIdLoadMore(int cityId, int cateId, int districtId,int streetId, int typeId, int cateTypeId, int offset) {
        mIBaseLoadMore.getDataLoadMore(0,restaurantHandler.getRestaurantById(cateId,typeId,districtId,streetId,cityId,cateTypeId,offset));
    }
    @Override
    public void getRestaurantAPI(int cityId, int cateId, int districtId,int streetId, int typeId, int cateTypeId) {
        mIView.showLoading();
        Call<List<Restaurant>> call = mService.listItemPlaces(cityId,cateTypeId,districtId,streetId,cateId,typeId,0);
        Log.e("url", call.request().url()+"" );
        call.enqueue(new Callback<List<Restaurant>>() {
            @Override
            public void onResponse(Call<List<Restaurant>> call, Response<List<Restaurant>> response) {
                List<Restaurant> rs = response.body();
                if(rs!=null && rs.size()>0) {
                    mIView.configRestaurant(rs);
                }
                else
                    mIView.error(mContext.getString(R.string.not_found));
            }
            @Override
            public void onFailure(Call<List<Restaurant>> call, Throwable t) {
                try {
                    mIView.error(mContext.getString(R.string.er_connect));
                    throw  new InterruptedException(mContext.getString(R.string.er_connect));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void getRestaurantAPIByLoadMore(int cityId, int cateId, int districtId,int streetId, int typeId, int cateTypeId, int offset) {
//        Call<List<Restaurant>> call = mService.listItemPlaces(cityId,cateTypeId,districtId,cateId,typeId,offset);
//        Log.e("Loadmore",call.request().url()+"");
//        call.enqueue(new Callback<List<Restaurant>>() {
//            @Override
//            public void onResponse(Call<List<Restaurant>> call, Response<List<Restaurant>> response) {
//                List<Restaurant> rs = response.body();
//                if(rs!=null && rs.size()>0) {
//                      mIBaseLoadMore.getDataLoadMore(0,rs);
//                }
//            }
//            @Override
//            public void onFailure(Call<List<Restaurant>> call, Throwable t) {
//                try {
//                    throw  new InterruptedException("Error connecting with the server!");
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
    }


}
