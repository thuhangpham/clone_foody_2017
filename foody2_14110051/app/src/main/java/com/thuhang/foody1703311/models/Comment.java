package com.thuhang.foody1703311.models;

/**
 * Created by ThuHang on 4/2/2017.
 */

public class Comment {
    private String date;
    private String fromOrigin;
    private String id;
    private boolean isLiked;
    private int mLikeCount;
    private String text;
    private String userName;

    public void setFromOrigin(String from) {
        this.fromOrigin = from;
    }

    public String getFromOrigin() {
        return this.fromOrigin;
    }

    public String getDate() {
        return this.date;
    }

    public String getId() {
        return this.id;
    }

    public String getText() {
        return this.text;
    }



    public void setDate(String date) {
        this.date = date;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getLikeCount() {
        return this.mLikeCount;
    }

    public void setLikeCount(int likeCount) {
        this.mLikeCount = likeCount;
    }

    public boolean isLiked() {
        return this.isLiked;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setLiked(boolean isLiked) {
        this.isLiked = isLiked;
    }
}
