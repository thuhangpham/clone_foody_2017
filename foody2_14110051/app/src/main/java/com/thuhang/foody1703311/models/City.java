package com.thuhang.foody1703311.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ThuHang on 3/23/2017.
 */

public class City {
    private String Id;
    private int deliveryCount;
    private List<Object> districts;
    private List<Categories> dnCategories;
    private boolean isSelected;
    private int mAreasCount;
    private String mCountryId;
    private String mDefault;
    private int mReviewCount;
    private int mStreetCount;
    private String name;
    private String totalRes;
    public City(String id, String name) {
        this.Id = id;
        this.name = name;
    }
    public City(String id) {
        this.Id = id;
    }
    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public int getDeliveryCount() {
        return deliveryCount;
    }

    public void setDeliveryCount(int deliveryCount) {
        this.deliveryCount = deliveryCount;
    }

    public List<Object> getDistricts() {
        return districts;
    }

    public void setDistricts(List<Object> districts) {
        this.districts = districts;
    }

    public List<Categories> getDnCategories() {
        return dnCategories;
    }

    public void setDnCategories(ArrayList<Categories> dnCategories) {
        this.dnCategories = dnCategories;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getmAreasCount() {
        return mAreasCount;
    }

    public void setmAreasCount(int mAreasCount) {
        this.mAreasCount = mAreasCount;
    }

    public String getmCountryId() {
        return mCountryId;
    }

    public void setmCountryId(String mCountryId) {
        this.mCountryId = mCountryId;
    }

    public String getmDefault() {
        return mDefault;
    }

    public void setmDefault(String mDefault) {
        this.mDefault = mDefault;
    }

    public int getmReviewCount() {
        return mReviewCount;
    }

    public void setmReviewCount(int mReviewCount) {
        this.mReviewCount = mReviewCount;
    }

    public int getmStreetCount() {
        return mStreetCount;
    }

    public void setmStreetCount(int mStreetCount) {
        this.mStreetCount = mStreetCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTotalRes() {
        return totalRes;
    }

    public void setTotalRes(String totalRes) {
        this.totalRes = totalRes;
    }
}
