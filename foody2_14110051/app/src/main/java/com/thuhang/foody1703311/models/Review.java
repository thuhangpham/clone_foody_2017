package com.thuhang.foody1703311.models;

/**
 * Created by thuha on 4/19/2017.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Review {

    @SerializedName("ID")
    @Expose
    private int id;
    @SerializedName("NAME")
    @Expose
    private String name;
    @SerializedName("RATING")
    @Expose
    private Double rating;
    @SerializedName("COMMENT")
    @Expose
    private Object commentmain;
    @SerializedName("COMMENTTRIM")
    @Expose
    private String comment;
    @SerializedName("AVATAR")
    @Expose
    private String avatar;
    @SerializedName("ITEMID")
    @Expose
    private Integer itemId;
    @SerializedName("REVIEWURL")
    @Expose
    private String reviewURL;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }
}
