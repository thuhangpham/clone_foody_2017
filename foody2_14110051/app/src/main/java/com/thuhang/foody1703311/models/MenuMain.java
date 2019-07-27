package com.thuhang.foody1703311.models;

/**
 * Created by thuha on 4/19/2017.
 */

public class MenuMain {
    private static final MenuMain ourInstance = new MenuMain();

    public static MenuMain getInstance() {
        return ourInstance;
    }

    private MenuMain() {
    }
}
