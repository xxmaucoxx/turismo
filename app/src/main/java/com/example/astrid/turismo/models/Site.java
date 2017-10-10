package com.example.astrid.turismo.models;


public class Site {
    private String direccion;
    private String latitud;
    private String longitud;
    private String pais;
    private String provincia;
    private String region;


    public Site() {

    }

    public Site(String direccion, String latitud, String longitud, String pais, String provincia, String region) {
        this.direccion = direccion;
        this.latitud = latitud;
        this.longitud = longitud;
        this.pais = pais;
        this.provincia = provincia;
        this.region = region;
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

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
}
