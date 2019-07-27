package com.thuhang.foody1703311.util;

/**
 * Created by ThuHang on 3/18/2017.
 */

public class Constant {
    public static int         TAB_PLACES =0,
                              TAB_FOOD =1;
    // Database Name
    public static final String DATABASE_NAME = "Foody.sqlite";
    // table name
    public static final String TB_CITY= "CITY",
                               TABLE_RESTAURANT="ITEM",
                               TB_CATEGORYTYPE = "CATEGORYTYPE",
                               TB_CATEGORY = "CATEGORY",
                               TB_DISTRICT = "DISTRICT",
                               TB_TYPE="TYPE",
                               TB_REVIEW="REVIEW";
    public static String       STR_CITY_ID = "CITYID",
                               STR_CATEGORY_ID = "categoryid",
                               STR_TYPE_ID = "typeid",
                               STR_DISTRICT_ID = "districtid";
    //
    public static String TAB_PLACES_CONTENT = "tab_places_content",
                         TAB_PLACES_CATE = "tab_paces_cate",
                         TAB_PLACES_TYPE_CATE="tab_places_type_cate",
                         TAB_PLACES_CITY="tab_places_city";
    public static String TAB_FOOD_CONTENT = "tab_food_content",
                         TAB_FOOD_CATE = "tab_food_cate",
                         TAB_FOOD_TYPE_CATE="tab_food_type_cate",
                         TAB_FOOD_CITY="tab_food_city";
    public static int LIMIT=4;
    public static int VERSION = 1;

}
