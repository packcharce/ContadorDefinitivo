package com.contvotos.charl.contadorvotos;

import android.support.annotation.NonNull;

public class Sindicato {
    private static final int NUMERO_DE_RATIOS = 3;

    private String nombre;
    private int votos;

    // Numeros individuales tras extraerlos de los decimales
    // [0][] => tecnicos
    // [1][] => especialistas
    // [2][] => otros
    private int[][] numerosRatios;

    // Numeros decimales con los ratios
    // 0 => tecnicos
    // 1 => especialistas
    // 2 => otros
    private float[] ratios;
    private int[] elegidos;

    Sindicato(String nombre) {
        this.votos = 0;
        int NUMERO_COLEGIOS = 2;
        this.elegidos = new int[NUMERO_COLEGIOS];
        this.nombre = nombre;
        ratios = new float[NUMERO_COLEGIOS];
        numerosRatios = new int[NUMERO_COLEGIOS][NUMERO_DE_RATIOS];
    }

    int[] getElegidos() {
        return elegidos;
    }

    int[][] getNumerosRatios() {
        return numerosRatios;
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

    float[] getRatios() {
        return ratios;
    }

    int compareTo(Sindicato sindicato, int colegio, int decimal){
        return Integer.compare(sindicato.getNumerosRatios()[colegio][decimal], this.getNumerosRatios()[colegio][decimal]);
    }

    @NonNull
    @Override
    public String toString() {
        return "Sindicato{ " +
                "nombre= '" + nombre + '\'' +
                ", votos= " + votos +
                ", elegidos tecs= "+getElegidos()[0]+
                ", elegidos esps= "+getElegidos()[1]+
                " }";
    }
}
