package com.example.astrid.turismo.models;

public class Product {
    private String description;
    private String nameArticle;
    private String precioArticle;
    private String urlImg;


    public Product() {
    }

    public Product(String description, String nameArticle, String precioArticle, String urlImg) {
        this.description = description;
        this.nameArticle = nameArticle;
        this.precioArticle = precioArticle;
        this.urlImg = urlImg;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNameArticle() {
        return nameArticle;
    }

    public void setNameArticle(String nameArticle) {
        this.nameArticle = nameArticle;
    }

    public String getPrecioArticle() {
        return precioArticle;
    }

    public void setPrecioArticle(String precioArticle) {
        this.precioArticle = precioArticle;
    }

    public String getUrlImg() {
        return urlImg;
    }

    public void setUrlImg(String urlImg) {
        this.urlImg = urlImg;
    }
}
