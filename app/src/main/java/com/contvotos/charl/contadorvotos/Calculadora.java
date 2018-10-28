package com.contvotos.charl.contadorvotos;

class Calculadora {

    private static final int SUMA_CINCO = 5, SUMA_NUEVE = 9, SUMA_TRECE = 13;


    /**
     * Paso 1
     * @param representantes array de colegios (tecnicos, especialistas y otros)
     * @return true si la suma es correcta, false si no lo es
     */
    static boolean compruebaSumaColegios(Colegio[] representantes) {
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
        // 2 => otros
        for (int i = 0; i < colegios.length; i++)
            sindicato.getRatios()[i] = ((colegios[i].getRepresentantes() * 1.0f) / numVotosValidos * 1.0f) * (sindicato.getVotos() * 1.0f);
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
    static int compruebaTotalEnteros(Sindicato[] listaSindicatos, int maxColegio){
        int sumaTotal=0;
        for(Sindicato s: listaSindicatos){
            sumaTotal += s.getNumerosRatios()[0][0];
        }
        if(sumaTotal == maxColegio){
            return 0;
        }else{
            return maxColegio - sumaTotal;
        }
    }

    /**
     * Comprueba si hay decimales repetidos por colegio
     * @param sindicatos lista de sindicatos
     * @param colegioAComprobar el colegio que hay que comprobar
     * @param posDecimal que decimal hay que comprobar si repetido
     * @return true si hay repetidos
     */
    private boolean compruebaRepetidosPorColegio(Sindicato[] sindicatos, int colegioAComprobar, int posDecimal) {
        int aux = -1;
        int aux2 = -1;
        for (int i = 0; i < sindicatos.length-1; i++) {
            aux = sindicatos[i].getNumerosRatios()[colegioAComprobar][i];
            for(int j = 1; j < sindicatos.length; j++){
                aux2 = sindicatos[i].getNumerosRatios()[colegioAComprobar][j];

                if(aux == aux2){
                    return true;
                }
            }
        }
        return false;
    }

    private void asignaVotosASindicatoPorDecimales(Sindicato[] sindicatos, int maxColegio, int COLEGIO_A_COMPROBAR) {
        final int POS_DECIMAL = 0;

        // Comprueba si hay decimales repetidos
        if (compruebaRepetidosPorColegio(sindicatos, COLEGIO_A_COMPROBAR, POS_DECIMAL)) {

            // Si no se repite ninguno, se ordenan de mayor a menor
            // y se asignan hasta llegar al tope de tecnicos/especialistas
            ordenaPorColegioYDecimal(sindicatos, COLEGIO_A_COMPROBAR, POS_DECIMAL);
            if(sindicatos.length >= maxColegio) {
                for (int i = 0; i < maxColegio; i++) {
                    sindicatos[i].getElegidos()[COLEGIO_A_COMPROBAR] += 1;
                }
            }else{
                for (Sindicato sindicato : sindicatos) {
                    sindicato.getElegidos()[COLEGIO_A_COMPROBAR] += 1;
                }
                for (int j = 0; j < maxColegio-sindicatos.length; j++) {
                    sindicatos[j].getElegidos()[COLEGIO_A_COMPROBAR] += 1;
                }
            }

        }

        // Si hay decimales repetidos
        else {
            // Compruebo si hay repeticiones en los segundos decimales, para lanzar error
            if(compruebaRepetidosPorColegio(sindicatos, COLEGIO_A_COMPROBAR, POS_DECIMAL+1)){
                throw new UnsupportedOperationException("Hay dos coincidencias de decimales");
            }
            else{

            }
        }
    }

    private void ordenaPorColegioYDecimal(Sindicato[] sindicatos, int colegio, int decimal) {
        int n = sindicatos.length;
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

}
