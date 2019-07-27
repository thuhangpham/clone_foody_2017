package com.thuhang.foody1703311.models;

/**
 * Created by ThuHang on 3/21/2017.
 */

public class CategoriesHomeMenu {
    private int id;
    private int imageDrawable;
    private String title;


    public CategoriesHomeMenu(int imageDrawable, String title) {
        this.imageDrawable = imageDrawable;
        this.title = title;
    }

    public CategoriesHomeMenu(int id, int image, String title) {
        this.id = id;
        this.imageDrawable = image;
        this.title = title;
    }
    public int getImageDrawable() {
        return imageDrawable;
    }

    public void setImageDrawable(int imageDrawable) {
        this.imageDrawable = imageDrawable;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
