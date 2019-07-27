package com.thuhang.foody1703311.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ThuHang on 3/24/2017.
 */

public class RestaurantFood {

    @SerializedName("ID")
    @Expose
    private int id;
    @SerializedName("ADDRESS")
    @Expose
    private String address;
    @SerializedName("NAME")
    @Expose
    private String name;
    @SerializedName("FOODNAME")
    @Expose
    private String foodName;
    @SerializedName("IMG")
    @Expose
    private String img;
    @SerializedName("DISTRICTID")
    @Expose
    private Integer districtId;
    @SerializedName("CATEGORYID")
    @Expose
    private Integer cateId;
    @SerializedName("TYPEID")
    @Expose
    private Integer typeId;
    @SerializedName("USERNAME")
    @Expose
    private String UserName;
    @SerializedName("USERAVATAR")
    @Expose
    private String useravatar;
    @SerializedName("DATECREATED")
    @Expose
    private String date;
    @SerializedName("CITYID")
    @Expose
    private Integer cityId;
    @SerializedName("CATETYPEID")
    @Expose
    private Integer catetypeid;
    public RestaurantFood(){}
    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public int getDistrictId() {
        return districtId;
    }

    public void setDistrictId(int districtId) {
        this.districtId = districtId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public int getCateId() {
        return cateId;
    }

    public void setCateId(int cateId) {
        this.cateId = cateId;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public void setDistrictId(Integer districtId) {
        this.districtId = districtId;
    }

    public void setCateId(Integer cateId) {
        this.cateId = cateId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getUseravatar() {
        return useravatar;
    }

    public void setUseravatar(String useravatar) {
        this.useravatar = useravatar;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getCatetypeid() {
        return catetypeid;
    }

    public void setCatetypeid(Integer catetypeid) {
        this.catetypeid = catetypeid;
    }
}
