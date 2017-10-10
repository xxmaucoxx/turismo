package com.example.astrid.turismo.models;


public class Social {
    private String social;
    private String valor;

    public Social() {

    }

    public Social(String social, String valor) {
        this.social = social;
        this.valor = valor;
    }


    public String getSocial() {
        return social;
    }

    public void setSocial(String social) {
        this.social = social;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
}
