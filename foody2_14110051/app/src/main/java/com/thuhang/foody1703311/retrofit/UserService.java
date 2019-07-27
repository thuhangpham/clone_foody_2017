package com.thuhang.foody1703311.retrofit;

import android.util.Log;

import com.thuhang.foody1703311.interfaces.IBaseView;
import com.thuhang.foody1703311.models.JsonRes;
import com.thuhang.foody1703311.models.User;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.thuhang.foody1703311.UserActivity.USER_ID;

/**
 * Created by thuha on 5/18/2017.
 */

public class UserService {
    private IMyService mService;
    private IBaseView mBaseView;
    public UserService(IBaseView baseView){
        mService = ApiUtils.getService();
        this.mBaseView = baseView;
    }
    public void userLogin(User user){
        mBaseView.showLoading();
        HashMap<String, String> map = new HashMap<>();
        map.put("email", user.getEmail());
        map.put("pass", user.getPassword());


        // finally, execute the request
        Call<JsonRes> call = mService.userLogin(map);
        call.enqueue(new Callback<JsonRes>() {
            @Override
            public void onResponse(Call<JsonRes> call, Response<JsonRes> response) {
                if(response.isSuccessful())
                {
                    if(response.body().getStatus()) {
                        USER_ID = response.body().getId();
                        Log.e("es", USER_ID+"");
                        mBaseView.hideLoading("Đăng nhập thành công!");
                    }
                    else
                        mBaseView.hideLoading("Email hoặc mật khẩu chưa dúng. Vui lòng thử lại.");
                }
            }
            @Override
            public void onFailure(Call<JsonRes> call, Throwable t) {
                mBaseView.hideLoading("Lỗi kết nối đến server.");
                Log.e("Login error:", t.getMessage());
            }
        });
    }
    public void createUser(User user){
        mBaseView.showLoading();
        HashMap<String, String> map = new HashMap<>();
        map.put("username", user.getUsername());
        map.put("pass", user.getPassword());
        map.put("email", user.getEmail());
        // finally, execute the request
        Call<JsonRes> call = mService.createUser(map);
        call.enqueue(new Callback<JsonRes>() {
            @Override
            public void onResponse(Call<JsonRes> call, Response<JsonRes> response) {
                if(response.isSuccessful())
                {
                    USER_ID=response.body().getId();
                    mBaseView.hideLoading("Đăng ký không thành công.");
                }
            }
            @Override
            public void onFailure(Call<JsonRes> call, Throwable t) {
                mBaseView.hideLoading("Lỗi kết nối đến server.");
                Log.e("Upload error:", t.getMessage());
            }
        });
    }
    public void updateUser(User user){
        mBaseView.showLoading();
        Call<JsonRes> call = mService.updateUser(user.getUsername(),user.getFirstname(),user.getLastname(),USER_ID);
        call.enqueue(new Callback<JsonRes>() {
            @Override
            public void onResponse(Call<JsonRes> call, Response<JsonRes> response) {
                if(response.isSuccessful()){
                    mBaseView.hideLoading("Thông tin của bạn đã được cập nhât!");
                }
                else
                    mBaseView.hideLoading("Lỗi cập nhật, vui lòng thử lại!");
            }

            @Override
            public void onFailure(Call<JsonRes> call, Throwable t) {
                mBaseView.hideLoading("Lỗi kết nối đến server!");
            }
        });
    }
}
