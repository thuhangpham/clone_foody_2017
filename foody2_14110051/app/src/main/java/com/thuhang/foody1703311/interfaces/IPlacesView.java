package com.thuhang.foody1703311.interfaces;

        import com.thuhang.foody1703311.models.Restaurant;

        import java.util.List;

/**
 * Created by thuha on 5/12/2017.
 */

public interface IPlacesView extends IView{
    void configRestaurant(List<Restaurant> items);
}
