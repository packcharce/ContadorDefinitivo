package com.contvotos.charl.contadorvotos;

public class Colegio {

    // Son los que tienen que sumar 5,9,etc.
    private int representantes;

    Colegio() {
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
