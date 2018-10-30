package com.contvotos.charl.contadorvotos;

public class Colegio {

    // Son los que tienen que sumar 5,9,etc.
    private int representantes;
    private String nombre;

    Colegio() {
        representantes = 0;
    }

    Colegio(String nombre, int representantes) {
        this.nombre = nombre;
        this.representantes = representantes;
    }

    int getRepresentantes() {
        return representantes;
    }

    void setRepresentantes(int representantes) {
        this.representantes = representantes;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Colegio{" + "nombre: " + nombre +
                " representantes=" + representantes +
                '}';
    }
}
