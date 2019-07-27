package com.thuhang.foody1703311.interfaces;

import com.thuhang.foody1703311.models.Categories;

import java.util.List;

/**
 * Created by binhb on 04/03/2017.
 */

public interface IView extends IBaseView{
    void configListCate(List<Categories> categories);
    void configListTypeCate(List<Object> types);
    void configListDistrict(List<Object> districts);
    void error(String err);
}
