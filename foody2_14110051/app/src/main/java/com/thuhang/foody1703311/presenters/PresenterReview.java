package com.thuhang.foody1703311.presenters;

import android.util.Log;

import com.thuhang.foody1703311.interfaces.IPresenterReview;
import com.thuhang.foody1703311.models.Review;
import com.thuhang.foody1703311.retrofit.ApiUtils;
import com.thuhang.foody1703311.retrofit.IMyService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by thuha on 5/13/2017.
 */

public class PresenterReview {
    private IMyService mIMyService;
    private IPresenterReview mIPresenterReview;
    public PresenterReview(IPresenterReview iPresenterReview){
        this.mIPresenterReview = iPresenterReview;
        mIMyService = ApiUtils.getService();
    }
    public void getReviewByItem(int itemId){
        Call<List<Review>> call = mIMyService.listReviewByItem(itemId);
        Log.e("review", call.request().url()+"");
        call.enqueue(new Callback<List<Review>>() {
            @Override
            public void onResponse(Call<List<Review>> call, Response<List<Review>> response) {
                mIPresenterReview.getDataReview(response.body());
            }
            @Override
            public void onFailure(Call<List<Review>> call, Throwable t) {
                Log.e("Failure", t.getMessage());
            }
        });
    }
}
