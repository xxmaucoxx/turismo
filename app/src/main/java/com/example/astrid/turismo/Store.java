package com.example.astrid.turismo;


public class Store {

    private String nameStore;
    private String descriptionStore;
    private String imgPortada;

    public Store() {

    }

    public Store(String nameStore, String descriptionStore, String imgPortada) {
        this.nameStore = nameStore;
        this.descriptionStore = descriptionStore;
        this.imgPortada = imgPortada;
    }

    public String getNameStore() {
        return nameStore;
    }

    public void setNameStore(String nameStore) {
        this.nameStore = nameStore;
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
}
