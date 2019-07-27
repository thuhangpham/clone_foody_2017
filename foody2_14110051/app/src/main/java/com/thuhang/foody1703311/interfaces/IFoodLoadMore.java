package com.thuhang.foody1703311.interfaces;

import com.thuhang.foody1703311.models.RestaurantFood;

import java.util.List;

/**
 * Created by thuha on 5/12/2017.
 */

public interface IFoodLoadMore {
    void getDataLoadMore(int offset,List<RestaurantFood> objects);
}
