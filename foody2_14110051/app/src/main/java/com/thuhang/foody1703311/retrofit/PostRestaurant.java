package com.thuhang.foody1703311.retrofit;

import android.util.Log;

import com.thuhang.foody1703311.interfaces.IBaseView;
import com.thuhang.foody1703311.models.Restaurant;

import org.json.JSONObject;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by thuha on 5/14/2017.
 */

public class PostRestaurant{
    private IMyService mService;
    private IBaseView mBaseView;
    public PostRestaurant(IBaseView baseView){
        mService = ApiUtils.getService();
        this.mBaseView = baseView;
    }
    public void postRestaurant(Restaurant res){
        mBaseView.showLoading();
        HashMap<String, String> map = new HashMap<>();
        map.put("name", res.getName());
        map.put("address", res.getAddress());
        map.put("cityid", String.valueOf(res.getCityId()));
        map.put("img", res.getImg());
        map.put("lat", res.getLatitude()+"");
        map.put("lng", res.getLongitude()+"");
        map.put("districtid", String.valueOf(res.getDistrictId()));
        map.put("typeid", String.valueOf(res.getTypeId()));
        map.put("catetypeid", String.valueOf(res.getCatetypeid()));
        map.put("cateid", String.valueOf(res.getCateId()));
        map.put("streetid","3262");
        // finally, execute the request
        Call<JSONObject> call = mService.uploadItem(map);
        call.enqueue(new Callback<JSONObject>() {
            @Override
            public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                if(response.isSuccessful())
                    mBaseView.hideLoading("Thêm quán ăn thành công!");
                }
                @Override
                public void onFailure(Call<JSONObject> call, Throwable t) {
                    mBaseView.hideLoading("Lỗi kết nối đến server.");
                    Log.e("Upload error:", t.getMessage());
                }
            });
        }
}
