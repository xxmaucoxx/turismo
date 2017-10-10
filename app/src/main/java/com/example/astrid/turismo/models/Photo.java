package com.example.astrid.turismo.models;


public class Photo {
    private String description;
    private String namePhoto;
    private String urlImg;

    public Photo() {

    }

    public Photo(String description, String namePhoto, String urlImg) {
        this.description = description;
        this.namePhoto = namePhoto;
        this.urlImg = urlImg;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNamePhoto() {
        return namePhoto;
    }

    public void setNamePhoto(String namePhoto) {
        this.namePhoto = namePhoto;
    }

    public String getUrlImg() {
        return urlImg;
    }

    public void setUrlImg(String urlImg) {
        this.urlImg = urlImg;
    }
}
