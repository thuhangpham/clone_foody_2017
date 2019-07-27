package com.thuhang.foody1703311.interfaces;

/**
 * Created by thuha on 5/13/2017.
 */

public interface IPresenterFoodAPI {
    void getResFoodAPI(int cityId, int cateId, int districtId,int streetId, int typeId, int cateTypeId);
    void getResFoodAPIByLoadMore(int cityId, int cateId, int districtId,int streetId, int typeId, int cateTypeId, int offset);
}
