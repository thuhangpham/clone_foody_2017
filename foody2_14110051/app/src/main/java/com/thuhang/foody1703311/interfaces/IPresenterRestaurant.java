package com.thuhang.foody1703311.interfaces;

/**
 * Created by ThuHang on 4/3/2017.
 */

public interface IPresenterRestaurant extends IPresenterResAPI{
    void getAllRestaurantById(int cityId, int cateId, int districtId,int streetId, int typeId, int cateTypeId);
    void getRestaurantByIdLoadMore(int cityId, int cateId, int districtId,int streetId, int typeId, int cateTypeId, int offset);
}
