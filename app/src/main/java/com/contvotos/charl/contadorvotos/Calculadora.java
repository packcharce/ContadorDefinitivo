package com.contvotos.charl.contadorvotos;

class Calculadora {

    private static final int SUMA_CINCO = 5, SUMA_NUEVE = 9, SUMA_TRECE = 13;
    private static final int RESTA_NO_SINDICATOS = 2;

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

    /**
     * Comprueba si hay mas decimales repetidos por colegio
     * que el maximo que debieran ser, resultado de @compruebaTotalEnteros
     *
     * O sea, si hay en total 3 de los enteros y hacen falta 2 mas, que los haya
     * antes de que empiecen las repeticiones
     *
     * @param sindicatos lista de sindicatos
     * @param colegioAComprobar el colegio que hay que comprobar
     * @param posDecimal que decimal hay que comprobar si repetido
     * @return true si hay repetidos
     */
    private static boolean compruebaRepetidosPorColegio(Sindicato[] sindicatos, int colegioAComprobar, int posDecimal, int numMaxDeRepetidos) {
        int aux;
        int aux2;
        int auxMaxRepetidos=0;
        for (int i = 0; i < sindicatos.length-1-RESTA_NO_SINDICATOS; i++) {
            aux = sindicatos[i].getNumerosRatios()[colegioAComprobar][posDecimal];
            for(int j = i+1; j < sindicatos.length-RESTA_NO_SINDICATOS; j++){
                aux2 = sindicatos[j].getNumerosRatios()[colegioAComprobar][posDecimal];
                if(aux == aux2){
                    auxMaxRepetidos++;
                }
            }
        }
        return auxMaxRepetidos >= numMaxDeRepetidos;
    }

    /**
     *
     * Metodo que hace va asignando los votos restantes hasta completar
     * ordenando los sindicatos por el decimal asignado de mayor a menor.
     *
     * @param sindicatos
     * @param listaColegios
     */
    static void asignaVotosASindicatoPorDecimales(Sindicato[] sindicatos, Colegio[] listaColegios) {
        // Cambiar
        final int POS_DECIMAL = 1;

        for(int i =0; i<listaColegios.length; i++) {
            int totalVotosExtra = compruebaTotalEnteros(sindicatos, listaColegios[i].getRepresentantes(), i);


            //region Codigo antiguo
            /*
             * Preguntar que pasa si hay repetidos: si se reparten hasta llegar a la repeticion
             * o directamente se salta al segundo decimal
             */
            // Comprueba si hay decimales repetidos
            boolean hayRepetidos = compruebaRepetidosPorColegio(sindicatos, i, POS_DECIMAL, totalVotosExtra);

            /*
             * Aqui si no hay suficientes repetidos como para que se tenga
             * que comprobar el primer decimal
             */
            if (!hayRepetidos) {

                // Si no se repite ninguno, se ordenan de mayor a menor
                // y se asignan hasta llegar al tope de tecnicos/especialistas
                ordenaPorColegioYDecimal(sindicatos, i, POS_DECIMAL);
                if (sindicatos.length >= listaColegios[i].getRepresentantes()) {
                    for (int j = 0; j < totalVotosExtra; j++) {
                        sindicatos[j].getElegidos()[i] += 1;
                    }
                } else {
                    for (Sindicato sindicato : sindicatos) {
                        sindicato.getElegidos()[i] += 1;
                    }
                    for (int j = 0; j < listaColegios[i].getRepresentantes() - sindicatos.length; j++) {
                        sindicatos[j].getElegidos()[i] += 1;
                    }
                }

            }

            // Si hay decimales repetidos, y no dan para asignar todos los votos
            else {
                asignaSiHayRepetidosPrimerDecimal(sindicatos, listaColegios, POS_DECIMAL, i, totalVotosExtra);
            }
            //endregion

            if (sindicatos.length >= listaColegios[i].getRepresentantes()) {
                for (int j = 0; j < totalVotosExtra; j++) {
                    try {
                        if (sindicatos[j].getNumerosRatios()[i][POS_DECIMAL] != sindicatos[j + 1].getNumerosRatios()[i][POS_DECIMAL]) {
                            sindicatos[j].getElegidos()[i] += 1;
                        }else{
                            asignaSiHayRepetidosSegundoDecimal(sindicatos, listaColegios, POS_DECIMAL+1, i, totalVotosExtra-j);
                        }
                    }catch (IndexOutOfBoundsException iex){
                        sindicatos[j].getElegidos()[i] += 1;
                    }
                }
            } else {
                for (Sindicato sindicato : sindicatos) {
                    sindicato.getElegidos()[i] += 1;
                }
                for (int j = 0; j < listaColegios[i].getRepresentantes() - sindicatos.length; j++) {
                    sindicatos[j].getElegidos()[i] += 1;
                }
            }
        }
    }

    private static void asignaSiHayRepetidosPrimerDecimal(Sindicato[] sindicatos, Colegio[] listaColegios, int POS_DECIMAL, int i, int totalVotosExtra) {

        /*
         * Ordeno por el primer decimal de mayor a menor
         */
        ordenaPorColegioYDecimal(sindicatos, i, POS_DECIMAL);

        /*
         * Compruebo si puedo asignar los votos o se va a quedar alguno sin
         * poder repartir
         */
        if(compruebaRepetidosPorColegio(sindicatos,i,POS_DECIMAL,totalVotosExtra)){
            throw new UnsupportedOperationException("Hay dos coincidencias de decimales");
        }
        if (sindicatos.length >= listaColegios[i].getRepresentantes()) {
            for (int j = 0; j < totalVotosExtra; j++) {
                try {
                    if (sindicatos[j].getNumerosRatios()[i][POS_DECIMAL] != sindicatos[j + 1].getNumerosRatios()[i][POS_DECIMAL]) {
                        sindicatos[j].getElegidos()[i] += 1;
                    }else{
                        asignaSiHayRepetidosSegundoDecimal(sindicatos, listaColegios, POS_DECIMAL+1, i, totalVotosExtra-j);
                    }
                }catch (IndexOutOfBoundsException iex){
                    sindicatos[j].getElegidos()[i] += 1;
                }
            }
        } else {
            for (Sindicato sindicato : sindicatos) {
                sindicato.getElegidos()[i] += 1;
            }
            for (int j = 0; j < listaColegios[i].getRepresentantes() - sindicatos.length; j++) {
                sindicatos[j].getElegidos()[i] += 1;
            }
        }
    }

    private static void asignaSiHayRepetidosSegundoDecimal(Sindicato[] sindicatos, Colegio[] listaColegios, int POS_DECIMAL, int i, int totalVotosExtra) {
        // Compruebo si hay repeticiones en los segundos decimales, para lanzar error
        boolean hayRepetidos2 = compruebaRepetidosPorColegio(sindicatos, i, POS_DECIMAL, totalVotosExtra);
        if (hayRepetidos2) {
            if(listaColegios[i].getRepresentantes() != 0)
                throw new UnsupportedOperationException("Hay dos coincidencias de decimales");
        } else {
            ordenaPorColegioYDecimal(sindicatos, i, POS_DECIMAL);
            if (sindicatos.length >= listaColegios[i].getRepresentantes()) {
                for (int j = 0; j < totalVotosExtra; j++) {
                    sindicatos[j].getElegidos()[i] += 1;
                }
            } else {
                for (Sindicato sindicato : sindicatos) {
                    sindicato.getElegidos()[i] += 1;
                }
                for (int j = 0; j < listaColegios[i].getRepresentantes() - sindicatos.length; j++) {
                    sindicatos[j].getElegidos()[i] += 1;
                }
            }
        }
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

}
