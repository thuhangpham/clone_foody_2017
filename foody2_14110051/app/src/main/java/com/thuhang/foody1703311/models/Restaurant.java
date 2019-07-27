package com.thuhang.foody1703311.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ThuHang on 3/24/2017.
 */

public class Restaurant {
    @SerializedName("ID")
    @Expose
    private Integer itemId;
    @SerializedName("ADDRESS")
    @Expose
    private String address;
    @SerializedName("NAME")
    @Expose
    private String name;
    @SerializedName("TOTALREVIEWS")
    @Expose
    private Integer totalReviews;
    @SerializedName("IMG")
    @Expose
    private String img;
    @SerializedName("DISTRICTID")
    @Expose
    public Integer districtId;
    @SerializedName("AVGRATING")
    @Expose
    private Double averagePoint;
    @SerializedName("CATEGORYID")
    @Expose
    private Integer cateId;
    @SerializedName("TYPEID")
    @Expose
    private Integer typeId;
    @SerializedName("TOTALPICTURES")
    @Expose
    private Integer totalPhoto;
    @SerializedName("RESTAURANTID")
    @Expose
    private Integer id;
    @SerializedName("CITYID")
    @Expose
    private Integer cityId;
    @SerializedName("CATETYPEID")
    @Expose
    private Integer catetypeid;
    @SerializedName("LATITUDE")
    @Expose
    private String latitude;
    @SerializedName("LONGITUDE")
    @Expose
    private String longitude;
    private List<Review> reviewList  = new ArrayList<>();

    public List<Review> getReviewList() {
        return reviewList;
    }

    public void setReviewList(List<Review> reviewList) {
        this.reviewList = reviewList;
    }

    public Restaurant() {
    }

    public Restaurant(int id, int cityId, String name, String address,
                      Double averagePoint, int typeId, int cateId,
                      int totalPhoto, int totalReviews, int itemId, String img, int districtId) {
        this.id = id;
        this.cityId = cityId;
        this.name = name;
        this.address = address;
        this.averagePoint = averagePoint;
        this.typeId = typeId;
        this.cateId = cateId;
        this.totalPhoto = totalPhoto;
        this.totalReviews = totalReviews;
        this.itemId = itemId;
        this.img = img;
        this.districtId = districtId;
    }

    public int getDistrictId() {
        return districtId;
    }

    public void setDistrictId(int districtId) {
        this.districtId = districtId;
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

    public Double getAveragePoint() {
        return averagePoint;
    }

    public void setAveragePoint(Double averagePoint) {
        this.averagePoint = averagePoint;
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

    public int getTotalPhoto() {
        return totalPhoto;
    }

    public void setTotalPhoto(int totalPhoto) {
        this.totalPhoto = totalPhoto;
    }

    public int getTotalReviews() {
        return totalReviews;
    }

    public void setTotalReviews(int totalReviews) {
        this.totalReviews = totalReviews;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Integer getCatetypeid() {
        return catetypeid;
    }

    public void setCatetypeid(Integer catetypeid) {
        this.catetypeid = catetypeid;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String  getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
