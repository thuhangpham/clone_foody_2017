package com.thuhang.foody1703311.models;

/**
 * Created by ThuHang on 4/16/2017.
 */

public class CategoryType {
    public static int CATE_TYPE_ID = 1;
    private int id;
    private String name;

    public CategoryType(int id, String name) {
        this.id = id;
        this.name = name;
    }

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
}
