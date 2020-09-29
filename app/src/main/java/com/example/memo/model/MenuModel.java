package com.example.memo.model;

public class MenuModel {
    public int icon;
    public String menuName, url;
    public boolean hasChildren, isGroup;

    public MenuModel(int icon, String menuName, boolean isGroup, boolean hasChildren, String url) {
        this.icon=icon;
        this.menuName = menuName;
        this.url = url;
        this.isGroup = isGroup;
        this.hasChildren = hasChildren;
    }
    public MenuModel(String menuName, boolean isGroup, boolean hasChildren, String url) {
        this.menuName = menuName;
        this.url = url;
        this.isGroup = isGroup;
        this.hasChildren = hasChildren;
    }
}