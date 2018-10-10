package com.contvotos.charl.contadorvotos;

public class Sindicato {
    private int id;
    private String nombre;
    private int votos;


    Sindicato() {
    }

    public Sindicato(String nombre) {
        this.votos = 0;
        this.nombre = nombre;
    }

    int getVotos() {
        return votos;
    }

    void setVotos(int votos) {
        this.votos = votos;
    }

     String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sindicato sindicato = (Sindicato) o;
        return id == sindicato.id;
    }

    @Override
    public String toString() {
        return "Sindicato{" +
                "nombre='" + nombre + '\'' +
                ", votos=" + votos +
                '}';
    }
}
