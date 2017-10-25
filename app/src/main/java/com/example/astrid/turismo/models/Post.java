package com.example.astrid.turismo.models;


public class Post {
    private String key;
    private String name;
    private String categoryStore;
    private String closeStore;
    private String openStore;
    private String img;
    private double upfecha;
    private String imgPost;
    private String decripcion;
    private String idStore;

    public Post() {
        
    }

    public Post(String key, String name, String categoryStore, String closeStore, String openStore, String img, double upfecha, String imgPost, String decripcion, String idStore) {
        this.key = key;
        this.name = name;
        this.categoryStore = categoryStore;
        this.closeStore = closeStore;
        this.openStore = openStore;
        this.img = img;
        this.upfecha = upfecha;
        this.imgPost = imgPost;
        this.decripcion = decripcion;
        this.idStore = idStore;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getOpenStore() {
        return openStore;
    }

    public void setOpenStore(String openStore) {
        this.openStore = openStore;
    }

    public double getUpfecha() {
        return upfecha;
    }

    public void setUpfecha(double upfecha) {
        this.upfecha = upfecha;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getImgPost() {
        return imgPost;
    }

    public void setImgPost(String imgPost) {
        this.imgPost = imgPost;
    }

    public String getDecripcion() {
        return decripcion;
    }

    public void setDecripcion(String decripcion) {
        this.decripcion = decripcion;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getIdStore() {
        return idStore;
    }

    public void setIdStore(String idStore) {
        this.idStore = idStore;
    }
}
