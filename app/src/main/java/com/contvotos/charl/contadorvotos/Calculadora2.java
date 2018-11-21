package com.contvotos.charl.contadorvotos;

import java.util.ArrayList;
import java.util.Arrays;

class Calculadora2 {

    private static final int SUMA_CINCO = 5, SUMA_NUEVE = 9, SUMA_TRECE = 13;
    private static final int RESTA_NO_SINDICATOS = 0;

    /**
     * Paso 1
     * @param representantes array de colegios (tecnicos, especialistas y otros)
     * @return true si la suma es correcta, false si no lo es
     */
     boolean compruebaSumaColegios(Colegio[] representantes) {
        boolean res = false;
        int aux = 0;
        for (Colegio c : representantes) {
            if (c != null)
                aux += c.getRepresentantes();
        }
        if (aux == SUMA_CINCO || aux == SUMA_NUEVE || aux == SUMA_TRECE)
            res = true;

        return res;
    }

    /**
     * Paso 4
     * @param sindicato sindicato al que calcular ratios
     * @param votosTotales numero de votos totales
     * @param votosBlancos numero de votos en blanco
     * @param votosNulos numero de votos nulos
     * @param colegios array con los colegios (tecnicos, especialistas)
     */
    static void calculaRatiosSindicato(Sindicato sindicato, int votosTotales, int votosBlancos, int votosNulos, Colegio[] colegios) {
        int numVotosValidos = votosTotales - votosBlancos - votosNulos;

        // 0 => tecnicos
        // 1 => especialistas
        for (int i = 0; i < colegios.length; i++) {
            if(numVotosValidos != 0)
                sindicato.getRatios()[i] = ((colegios[i].getRepresentantes() * 1.0f) / numVotosValidos * 1.0f) * (sindicato.getVotos() * 1.0f);
        }
    }

    /**
     * Paso 3
     * @param listaSindicatos array con los sindicatos
     * @return numero de votos totales entre todos los sindicatos
     */
    static int calculaTotalVotos(Sindicato[] listaSindicatos) {
        int res = 0;
        for (Sindicato s :
                listaSindicatos) {
            res += s.getVotos();
        }
        return res;
    }

    /**
     * Paso 5.1
     * Extrae los numeros de los ratios y las guarda en array
     * @param sindicato sindicato al que extraer los enteros
     */
    static void extraeNumerosDeRatios(Sindicato sindicato) {
        for (int i = 0; i < sindicato.getRatios().length; i++) {
            String aux = String.valueOf(sindicato.getRatios()[i]);

            if (aux.length() < 4)
                aux += "0";

            byte j = 0;
            byte k = 0;
            while (k != 3 && sindicato.getRatios()[i] != 0.0) {
                char c = aux.charAt(j);
                if (c != '.' && c != ',') {
                    sindicato.getNumerosRatios()[i][k] = Integer.valueOf(String.valueOf(aux.charAt(j)));
                    k++;
                }
                j++;
            }
        }
    }

    /**
     * Paso 5.2 asigna los valores de la parte entera como representantes
     * @param s sindicato al que tratar
     */
    static void asignaVotosASindicato(Sindicato s) {
        for (int i = 0; i < s.getNumerosRatios().length; i++) {
            s.getElegidos()[i] = s.getNumerosRatios()[i][0];
        }
    }

    /**
     * Paso 5.3
     * Comprueba si la suma de todos los enteros extraidos son
     * el total de representantes necesarios
     * @param listaSindicatos lista de los sindicatos a tratar
     * @return el numero de representantes extra que hay que sacar de
     * los decimales
     */
    private static int compruebaTotalEnteros(Sindicato[] listaSindicatos, int maxRepresentantesColegio, int colegio){
        int sumaTotal=0;
        for(int i = 0; i < listaSindicatos.length-RESTA_NO_SINDICATOS; i++){

            sumaTotal += listaSindicatos[i].getNumerosRatios()[colegio][0];

        }
        if(sumaTotal == maxRepresentantesColegio){
            return 0;
        }else{
            return maxRepresentantesColegio - sumaTotal;
        }
    }

    static void asignaVotos(Sindicato[] sindicatos, Colegio[] colegios) throws Exception {
        final int PRIMER_DECIMAL = 1, SEGUNDO_DECIMAL = 2, PARTE_ENTERA = 0;

        for (int i = 0; i < colegios.length; i++) {
            if(colegios[i].getRepresentantes() != 0){


                int maxRepresentantesExtra = compruebaTotalEnteros(sindicatos, colegios[i].getRepresentantes(), i);
                ordenaPorColegioYDecimal(sindicatos, i, PRIMER_DECIMAL);

                // COmprobar si da una vuelta extra el bucle
                for (int j = 0; j < maxRepresentantesExtra; j++) {

                    int decimalActual = sindicatos[j].getNumerosRatios()[i][PRIMER_DECIMAL];
                    int decimalSiguiente = sindicatos[j + 1].getNumerosRatios()[i][PRIMER_DECIMAL];
                    if(decimalActual == decimalSiguiente){


                        //region Si se repiten
                        Sindicato[] listaRepetidos = extraeSindicatosRepetidos(sindicatos, i, PRIMER_DECIMAL, decimalActual);

                        if(listaRepetidos.length == (maxRepresentantesExtra - j)){
                            Sindicato[] aux = Arrays.copyOf(listaRepetidos, listaRepetidos.length);
                            ordenaPorColegioYDecimal(aux, i, SEGUNDO_DECIMAL);

                            for (int k = 0; k < aux.length-1; k++) {
                                int decimalActualSegundo = aux[k].getNumerosRatios()[i][SEGUNDO_DECIMAL];
                                int decimalSiguienteSegundo = aux[k+1].getNumerosRatios()[i][SEGUNDO_DECIMAL];
                                if(decimalActualSegundo == decimalSiguienteSegundo){
                                    throw new UnsupportedOperationException("ERROR, segundos decimales repetidos.");
                                }else{
                                    aux[k].getElegidos()[i]++;
                                }
                            }
                            aux[aux.length-1].getElegidos()[i]++;
                            break;
                        }else if(listaRepetidos.length < (maxRepresentantesExtra - j)){
                            Sindicato[] aux = Arrays.copyOf(listaRepetidos, listaRepetidos.length);
                            ordenaPorColegioYDecimal(aux, i, SEGUNDO_DECIMAL);

                            for (int k = 0; k < aux.length-1; k++) {
                                int decimalActualSegundo = aux[k].getNumerosRatios()[i][SEGUNDO_DECIMAL];
                                int decimalSiguienteSegundo = aux[k+1].getNumerosRatios()[i][SEGUNDO_DECIMAL];
                                if(decimalActualSegundo == decimalSiguienteSegundo){
                                    throw new UnsupportedOperationException("ERROR, segundos decimales repetidos.");
                                }else{
                                    aux[k].getElegidos()[i]++;
                                }
                            }
                            aux[aux.length-1].getElegidos()[i]++;


                        }else if(listaRepetidos.length > (maxRepresentantesExtra - j)){
                            Sindicato[] aux = Arrays.copyOf(listaRepetidos, listaRepetidos.length);
                            ordenaPorColegioYDecimal(aux, i, SEGUNDO_DECIMAL);

                            for (int k = 0; k < (maxRepresentantesExtra - j); k++) {
                                int decimalActualSegundo = aux[k].getNumerosRatios()[i][SEGUNDO_DECIMAL];
                                int decimalSiguienteSegundo = aux[k+1].getNumerosRatios()[i][SEGUNDO_DECIMAL];
                                if(decimalActualSegundo == decimalSiguienteSegundo){
                                    throw new UnsupportedOperationException("ERROR, segundos decimales repetidos.");
                                }else{
                                    aux[k].getElegidos()[i]++;
                                }
                            }
                            break;
                        }
                        //endregion
                    }
                    else{

                        //region Si no se repiten
                        sindicatos[j].getElegidos()[i]++;
                        //endregion

                    }
                }
            }
        }
    }

    /**
     * Extrae un array con los sindicatos con decimales repetidos, para asignarlos aparte
     * @param sindicatos
     * @param posDecimal
     * @param numeroQueSeRepite este numero es para que no saque todos los repetidos, sino solo el que quiero
     * @return array con los sindicatos que repiten decimales
     */
    private static Sindicato[] extraeSindicatosRepetidos(Sindicato[] sindicatos,int colegio, int posDecimal, int numeroQueSeRepite) {
        ArrayList<Sindicato> aux = new ArrayList<>();
        for (int i = 0; i < sindicatos.length-1; i++) {
            if(sindicatos[i].getNumerosRatios()[colegio][posDecimal] == numeroQueSeRepite){
                aux.add(sindicatos[i]);
            }
        }

        return aux.toArray(new Sindicato[0]);
    }

    private static void ordenaPorColegioYDecimal(Sindicato[] sindicatos, int colegio, int decimal) {
        int n = sindicatos.length - RESTA_NO_SINDICATOS;
        Sindicato aux;

        for (int i = 0; i < n; i++) {
            for (int j = 1; j < (n - i); j++) {
                if (sindicatos[j - 1].compareTo(sindicatos[j], colegio, decimal) > 0) {
                    aux = sindicatos[j - 1];
                    sindicatos[j - 1] = sindicatos[j];
                    sindicatos[j] = aux;
                }
            }
        }
    }

    void calcular(Sindicato[] listaSindicatos, Colegio[] listaColegios){
        for (Sindicato s : listaSindicatos) {
            Calculadora2.calculaRatiosSindicato(
                    s,
                    Calculadora2.calculaTotalVotos(listaSindicatos),
                    listaSindicatos[6].getVotos(),
                    listaSindicatos[7].getVotos(),
                    listaColegios
            );

            Calculadora2.extraeNumerosDeRatios(s);
            Calculadora2.asignaVotosASindicato(s);
        }

        try {
            asignaVotos(Arrays.copyOf(listaSindicatos, listaSindicatos.length - 2), listaColegios);
        }catch (Exception e){

        }
    }
}
