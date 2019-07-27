package com.thuhang.foody1703311.presenters;

import android.util.Log;

import com.thuhang.foody1703311.interfaces.IPresenterUser;
import com.thuhang.foody1703311.models.User;
import com.thuhang.foody1703311.retrofit.ApiUtils;
import com.thuhang.foody1703311.retrofit.IMyService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by thuha on 5/18/2017.
 */

public class PresenterUser {
    private IPresenterUser iPresenterUser;
    private IMyService myService;
    public PresenterUser(IPresenterUser iPresenterUser){
        this.iPresenterUser = iPresenterUser;
        myService= ApiUtils.getService();
    }
    public void getUserById(int id){
        Call<User> call = myService.getUserbyId(id);
        Log.e("aa",call.request().url()+"");
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.e("qq", response.body().getUsername());
                if(response.isSuccessful())
                    iPresenterUser.getUserById(response.body());
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("qqa", "errr"+t.getMessage());
            }
        });
    }
}
