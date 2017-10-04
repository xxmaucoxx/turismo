package com.example.astrid.turismo.models;

import java.util.List;

public class Point {
    private String categoryStore;
    private String closeStore;
    private String descriptionStore;
    private String imgPortada;
    private String imgProfile;
    private String nameStore;
    private String openStore;


    public Point() {

    }
    public Point(String categoryStore, String closeStore, String descriptionStore, String imgPortada, String imgProfile, String nameStore, String openStore) {
        this.categoryStore = categoryStore;
        this.closeStore = closeStore;
        this.descriptionStore = descriptionStore;
        this.imgPortada = imgPortada;
        this.imgProfile = imgProfile;
        this.nameStore = nameStore;
        this.openStore = openStore;

    }

    public String getCategoryStore() {
        return categoryStore;
    }

    public void setCategoryStore(String categoryStore) {
        this.categoryStore = categoryStore;
    }

    public String getCloseStore() {
        return closeStore;
    }

    public void setCloseStore(String closeStore) {
        this.closeStore = closeStore;
    }

    public String getDescriptionStore() {
        return descriptionStore;
    }

    public void setDescriptionStore(String descriptionStore) {
        this.descriptionStore = descriptionStore;
    }

    public String getImgPortada() {
        return imgPortada;
    }

    public void setImgPortada(String imgPortada) {
        this.imgPortada = imgPortada;
    }

    public String getImgProfile() {
        return imgProfile;
    }

    public void setImgProfile(String imgProfile) {
        this.imgProfile = imgProfile;
    }

    public String getNameStore() {
        return nameStore;
    }

    public void setNameStore(String nameStore) {
        this.nameStore = nameStore;
    }

    public String getOpenStore() {
        return openStore;
    }

    public void setOpenStore(String openStore) {
        this.openStore = openStore;
    }

}
