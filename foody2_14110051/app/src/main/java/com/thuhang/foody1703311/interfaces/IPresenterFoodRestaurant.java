package com.thuhang.foody1703311.interfaces;

/**
 * Created by ThuHang on 4/5/2017.
 */

public interface IPresenterFoodRestaurant {
    void getAllFoodRestaurantById(int cityId, int cateId, int districtId,int streetId, int typeCateId, int offset);
    void getAllFoodRestaurantByIdLoadMore(int cityId, int cateId, int districtId,int streetId, int typeCateId, int offset);
}
