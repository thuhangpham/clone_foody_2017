package com.thuhang.foody1703311.models;

import android.provider.ContactsContract;

import java.util.List;

/**
 * Created by ThuHang on 3/24/2017.
 */

public class District {
    private int areasCount;
    private ContactsContract.CommonDataKinds.Photo coverPhoto;
    private String districtId;
    private String districtName;
    private int mResCount;
    private int placeCount;
    private int streetCount;
    private List<Street> streets;

    public District(String districtId) {
        this.districtId = districtId;
    }
    public District(String districtId, String districtName) {
        this.districtId = districtId;
        this.districtName = districtName;
    }
    public int getAreasCount() {
        return areasCount;
    }

    public void setAreasCount(int areasCount) {
        this.areasCount = areasCount;
    }

    public ContactsContract.CommonDataKinds.Photo getCoverPhoto() {
        return coverPhoto;
    }

    public void setCoverPhoto(ContactsContract.CommonDataKinds.Photo coverPhoto) {
        this.coverPhoto = coverPhoto;
    }

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public int getmResCount() {
        return mResCount;
    }

    public void setmResCount(int mResCount) {
        this.mResCount = mResCount;
    }

    public int getPlaceCount() {
        return placeCount;
    }

    public void setPlaceCount(int placeCount) {
        this.placeCount = placeCount;
    }

    public int getStreetCount() {
        return streetCount;
    }

    public void setStreetCount(int streetCount) {
        this.streetCount = streetCount;
    }

    public List<Street> getStreets() {
        return streets;
    }

    public void setStreets(List<Street> streets) {
        this.streets = streets;
    }
}
