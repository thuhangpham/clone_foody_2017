package com.thuhang.foody1703311.models;

/**
 * Created by ThuHang on 4/5/2017.
 */

public class Type {
    private int id;
    private String img;
    private String name;
    private int cateId;
    public Type(){}

    public Type(int id, String img, String name, int cateId) {
        this.id = id;
        this.img = img;
        this.name = name;
        this.cateId = cateId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCateId() {
        return cateId;
    }

    public void setCateId(int cateId) {
        this.cateId = cateId;
    }
}
