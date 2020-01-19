package com.dsaproject.piterarmstrong_android.models;

public class Objeto {

    private String nombre;
    private int coste;

    public Objeto(String nombre, int coste) {
        this.nombre = nombre;
        this.coste = coste;
    }

    public Objeto() {
        //Empty constructor
    }

    //----------------------Methods----------------------//
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCoste() {
        return coste;
    }

    public void setCoste(int coste) {
        this.coste = coste;
    }
    //---------------------------------------------------//
}
