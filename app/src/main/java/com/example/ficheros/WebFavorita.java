package com.example.ficheros;

public class WebFavorita {

    private String logo,enlace,id,nombre;

    public WebFavorita(String logo,String nombre,String id,String enlace){
        this.logo=logo;
        this.enlace=enlace;
        this.id = id;
        this.nombre = nombre;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getEnlace() {
        return enlace;
    }

    public void setEnlace(String enlace) {
        this.enlace = enlace;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "WebFavorita{" +
                "logo='" + logo + '\'' +
                ", enlace='" + enlace + '\'' +
                ", id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
