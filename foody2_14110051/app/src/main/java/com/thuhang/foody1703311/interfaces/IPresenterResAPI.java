package com.thuhang.foody1703311.interfaces;

/**
 * Created by thuha on 5/12/2017.
 */

public interface IPresenterResAPI {
    void getRestaurantAPI(int cityId, int cateId, int districtId,int streetId, int typeId, int cateTypeId);
    void getRestaurantAPIByLoadMore(int cityId, int cateId, int districtId,int streetId, int typeId, int cateTypeId, int offset);
}