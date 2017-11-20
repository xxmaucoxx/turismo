package com.example.astrid.turismo.models;

public class Mark {
    private String key;
    private String color;
    private String categoryStore;
    private String closeStore;
    private String imgProfile;
    private String nameStore;
    private String openStore;
    private String direccion;
    private String latitud;
    private String longitud;

    public Mark() {

    }
    public Mark(String key, String color, String categoryStore, String closeStore, String imgProfile, String nameStore, String openStore, String direccion, String latitud, String longitud) {
        this.key = key;
        this.color = color;
        this.categoryStore = categoryStore;
        this.closeStore = closeStore;
        this.imgProfile = imgProfile;
        this.nameStore = nameStore;
        this.openStore = openStore;
        this.direccion = direccion;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
