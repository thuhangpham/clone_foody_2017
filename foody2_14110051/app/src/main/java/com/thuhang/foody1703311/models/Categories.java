package com.thuhang.foody1703311.models;

/**
 * Created by ThuHang on 3/21/2017.
 */

public class Categories {
    private String id;
    private String image;
    private String title;
    public Categories(String id, String image, String title) {
        this.id = id;
        this.image = image;
        this.title = title;
    }
    public Categories(String image, String title) {
        this.image = image;
        this.title = title;
    }
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
