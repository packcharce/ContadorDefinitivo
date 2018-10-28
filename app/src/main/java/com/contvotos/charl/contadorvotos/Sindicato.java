package com.contvotos.charl.contadorvotos;

public class Sindicato {
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
        this.elegidos = new int[3];
        this.nombre = nombre;
        ratios = new float[3];
        numerosRatios = new int[ratios.length][3];
    }

    public int[] getElegidos() {
        return elegidos;
    }

    public void setElegidos(int[] elegidos) {
        this.elegidos = elegidos;
    }

    int[][] getNumerosRatios() {
        return numerosRatios;
    }

    public void setNumerosRatios(int[][] numerosRatios) {
        this.numerosRatios = numerosRatios;
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

    float[] getRatios() {
        return ratios;
    }

    public void setRatios(float[] ratios) {
        this.ratios = ratios;
    }

    int compareTo(Sindicato sindicato, int colegio, int decimal){
        return Integer.compare(sindicato.getNumerosRatios()[colegio][decimal], this.getNumerosRatios()[colegio][decimal]);
    }

    @Override
    public String toString() {
        return "Sindicato{" +
                "nombre='" + nombre + '\'' +
                ", votos=" + votos +
                '}';
    }
}
