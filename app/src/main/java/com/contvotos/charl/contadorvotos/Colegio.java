package com.contvotos.charl.contadorvotos;

public class Colegio {

    private int representantes;

    public Colegio() {
        representantes = 0;
    }

    Colegio(int representantes) {
        this.representantes = representantes;
    }

    int getRepresentantes() {
        return representantes;
    }

    void setRepresentantes(int representantes) {
        this.representantes = representantes;
    }


    @Override
    public String toString() {
        return "Colegio{" +
                "representantes=" + representantes +
                '}';
    }
}
