package com.example.astrid.turismo.models;


import android.icu.util.TimeZone;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Page {
    private String categoryStore;
    private String closeStore;
    private String descriptionStore;
    private String imgPortada;
    private String imgProfile;
    private String nameStore;
    private String openStore;
    private String page;

    private static final String TAG = "MyActivity";


    public Page() {

    }
    public Page(String categoryStore, String closeStore, String descriptionStore, String imgPortada, String imgProfile, String nameStore, String openStore, String page) {
        this.categoryStore = categoryStore;
        this.closeStore = closeStore;
        this.descriptionStore = descriptionStore;
        this.imgPortada = imgPortada;
        this.imgProfile = imgProfile;
        this.nameStore = nameStore;
        this.openStore = openStore;
        this.page = page;
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

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getHoraOpen() throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        Date d = sdf.parse(this.openStore);
        SimpleDateFormat output = new SimpleDateFormat("HH:mm a");
        String formattedTime = output.format(d);
        return formattedTime;

    }
    public String getHoraClose() throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date d = sdf.parse(this.closeStore);
        SimpleDateFormat output = new SimpleDateFormat("HH:mm a");
        String formattedTime = output.format(d);
        return formattedTime;

    }

}
