package com.thuhang.foody1703311.retrofit;

import com.thuhang.foody1703311.models.JsonRes;
import com.thuhang.foody1703311.models.Restaurant;
import com.thuhang.foody1703311.models.RestaurantFood;
import com.thuhang.foody1703311.models.Review;
import com.thuhang.foody1703311.models.User;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by thuha on 5/12/2017.
 */

public interface IMyService {
    @GET("/item")
    Call<List<Restaurant>> listItemPlaces(@Query("cityid") int cityid, @Query("catetypeid") int catetypeID, @Query("districtid") int districtID, @Query("streetid") int streetID, @Query("categoryid") int categoryID, @Query("typeid")int typeid, @Query("offset") int ofsset);
    @GET("/item/itemfood")
    Call<List<RestaurantFood>> listItemFoods(@Query("cityid") int cityid, @Query("catetypeid") int catetypeID, @Query("districtid") int districtID,  @Query("streetid") int streetID,@Query("categoryid") int categoryID, @Query("typeid")int typeid, @Query("offset") int ofsset);
    @POST("/item")
    Call<JSONObject> uploadItem(
            @Body Map<String, String> RequestBody
    );
    @GET("/item")
    Call<List<Restaurant>> listItemPlacesNearBy(@Query("city") int city, @Query("catetypeid") int catetypeID, @Query("district") int district, @Query("street") int street, @Query("categoryid") int categoryID, @Query("typeid")int typeid, @Query("offset") int ofsset);


    @POST("/user")
    Call<JsonRes> createUser(@Body Map<String, String> RequestBody);
    @GET("/user/byid")
    Call<User> getUserbyId(@Query("id") int id);
    @POST("/user/login")
    Call<JsonRes> userLogin(@Body Map<String, String> RequestBody);
    @GET("/user/review")
    Call<List<Review>> listReviewByItem(@Query("itemid") int itemId);
    @GET("/user/update")
    Call<JsonRes> updateUser(@Query("username") String uname, @Query("firstname") String firstname, @Query("lastname") String lastname,@Query("id") int id);
}
