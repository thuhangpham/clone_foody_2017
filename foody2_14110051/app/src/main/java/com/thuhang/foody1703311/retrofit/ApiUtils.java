package com.thuhang.foody1703311.retrofit;

/**
 * Created by thuha on 5/12/2017.
 */

public class ApiUtils {
    public static final String BASE_URL ="https://api-crz.herokuapp.com/";//"http://192.168.43.235:3000/";//

    public static IMyService getService() {
        return RetrofitClient.getClient(BASE_URL).create(IMyService.class);
    }
}
