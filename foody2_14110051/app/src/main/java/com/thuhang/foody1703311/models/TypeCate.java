package com.thuhang.foody1703311.models;

/**
 * Created by ThuHang on 4/1/2017.
 */

public class TypeCate {
    private int id;
    private String img;
    private String name;
    private int typeCateId;
    public TypeCate()
    {}
    public TypeCate(int id, String img, String name, int typeCateId) {
        this.id = id;
        this.img = img;
        this.name = name;
        this.typeCateId = typeCateId;
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

    public int getTypeCateId() {
        return typeCateId;
    }

    public void setTypeCateId(int typeCateId) {
        this.typeCateId = typeCateId;
    }
}
