package com.thuhang.foody1703311.presenters;

import android.content.Context;
import android.util.Log;

import com.thuhang.foody1703311.R;
import com.thuhang.foody1703311.database.RestaurantFoodHandler;
import com.thuhang.foody1703311.interfaces.IFoodLoadMore;
import com.thuhang.foody1703311.interfaces.IFoodView;
import com.thuhang.foody1703311.interfaces.IPresenterFoodAPI;
import com.thuhang.foody1703311.interfaces.IPresenterFoodRestaurant;
import com.thuhang.foody1703311.models.RestaurantFood;
import com.thuhang.foody1703311.retrofit.ApiUtils;
import com.thuhang.foody1703311.retrofit.IMyService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.thuhang.foody1703311.util.Constant.DATABASE_NAME;
import static com.thuhang.foody1703311.util.Constant.VERSION;

/**
 * Created by ThuHang on 4/5/2017.
 */

public class PresenterFoodRestaurant implements IPresenterFoodRestaurant, IPresenterFoodAPI {
    private IFoodView mIView;
    private Context mContext;
    private IFoodLoadMore mIBaseLoadmore;
    private IMyService mIMyService;
    private RestaurantFoodHandler restaurantHandler;

    public PresenterFoodRestaurant(IFoodView mIView, Context mContext/*, IFoodLoadMore iBaseLoadMore*/) {
        this.mIView = mIView;
        this.mContext = mContext;
        /*this.mIBaseLoadmore = iBaseLoadMore;*/
        mIMyService = ApiUtils.getService();
        restaurantHandler =  new RestaurantFoodHandler(mContext,DATABASE_NAME,null,VERSION);
    }
    @Override
    public void getAllFoodRestaurantById(int cityId, int cateId, int districtId,int streetId, int typeCateId, int offset) {
        List<RestaurantFood> resList = restaurantHandler.getRestaurantFoodById(cateId,typeCateId,districtId,cityId,0);
        if(resList.size()>0)
            mIView.configRestaurant(resList);
        else
            mIView.error(mContext.getString(R.string.not_found));
    }

    @Override
    public void getAllFoodRestaurantByIdLoadMore(int cityId, int cateId, int districtId,int streetId, int typeCateId, int offset) {
        mIBaseLoadmore.getDataLoadMore(1, restaurantHandler.getRestaurantFoodById(cateId,typeCateId,districtId,cityId,offset));
    }

    @Override
    public void getResFoodAPI(int cityId, int cateId, int districtId,int streetId, int typeId, int cateTypeId) {
        mIView.showLoading();
        Call<List<RestaurantFood>> call = mIMyService.listItemFoods(cityId,cateTypeId,districtId,streetId,cateId,typeId,0);
        Log.e("urlfood", call.request().url()+"" );
        call.enqueue(new Callback<List<RestaurantFood>>() {
            @Override
            public void onResponse(Call<List<RestaurantFood>> call, Response<List<RestaurantFood>> response) {
                List<RestaurantFood> rs = response.body();
                if(rs!=null && rs.size()>0) {
                    mIView.configRestaurant(rs);
                }
                else
                    mIView.error(mContext.getString(R.string.not_found));
            }
            @Override
            public void onFailure(Call<List<RestaurantFood>> call, Throwable t) {
                mIView.error(mContext.getString(R.string.er_connect));
                try {
                    throw  new InterruptedException("Error connecting with the server!");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void getResFoodAPIByLoadMore(int cityId, int cateId, int districtId,int streetId, int typeId, int cateTypeId, int offset) {
       /* Call<List<RestaurantFood>> call = mIMyService.listItemFoods(cityId,cateTypeId,districtId,cateId,typeId,offset);
        Log.e("urlfood", call.request().url()+"" );
        call.enqueue(new Callback<List<RestaurantFood>>() {
            @Override
            public void onResponse(Call<List<RestaurantFood>> call, Response<List<RestaurantFood>> response) {
                List<RestaurantFood> rs = response.body();
                if(rs!=null && rs.size()>0) {
                    mIBaseLoadmore.getDataLoadMore(1,rs);
                }
            }
            @Override
            public void onFailure(Call<List<RestaurantFood>> call, Throwable t) {
                mIView.error(mContext.getString(R.string.er_connect));
                try {
                    throw  new InterruptedException("Error connecting with the server!");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });*/

    }
}
