package com.rocio.poma.mesa2_tecnologias_emergentes;

public class Notificaciones {
    int id;
    String categoria;
    String titulo;
    String fecha;

    public Notificaciones(){

    }

    public Notificaciones(int id, String categoria, String titulo, String fecha) {
        this.id = id;
        this.categoria = categoria;
        this.titulo = titulo;
        this.fecha = fecha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
