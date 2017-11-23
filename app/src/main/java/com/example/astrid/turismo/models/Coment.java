package com.example.astrid.turismo.models;


public class Coment {
    private String uid;
    private String name;
    private String foto;
    private String comentario;
    private String fecha;

    public Coment() {

    }

    public Coment(String uid, String name, String foto, String comentario, String fecha) {
        this.uid = uid;
        this.name = name;
        this.foto = foto;
        this.comentario = comentario;
        this.fecha = fecha;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
