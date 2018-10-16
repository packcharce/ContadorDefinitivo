package com.contvotos.charl.contadorvotos;

class Calculadora {

    private static final int SUMA_CINCO = 5, SUMA_NUEVE = 9, SUMA_TRECE = 13;


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

    static void calculaRatiosSindicato(Sindicato sindicato, int votosTotales, int votosBlancos, int votosNulos, Colegio[] colegios) {
        int numVotosValidos = votosTotales - votosBlancos - votosNulos;

        // 0 => tecnicos
        // 1 => especialistas
        // 2 => otros
        for (int i = 0; i < colegios.length; i++)
            sindicato.getRatios()[i] = ((colegios[i].getRepresentantes() * 1.0f) / numVotosValidos * 1.0f) * (sindicato.getVotos() * 1.0f);
    }

    static int calculaTotalVotos(Sindicato[] listaSindicatos) {
        int res = 0;
        for (Sindicato s :
                listaSindicatos) {
            res += s.getVotos();
        }
        return res;
    }

    static void extraeEnterosDeRatios(Sindicato sindicato) {
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

    static void asignaVotosASindicato(Sindicato s) {
        for (int i = 0; i < s.getNumerosRatios().length; i++) {
            s.getElegidos()[i] = s.getNumerosRatios()[i][0];
        }
    }

    private boolean compruebaRepetidosPorColegio(Sindicato[] sindicatos, int colegioAComprobar, int posDecimal) {
        int aux = -1;
        for (Sindicato s : sindicatos) {
            int aux2 = s.getNumerosRatios()[colegioAComprobar][posDecimal];
            if (aux == aux2)
                return true;
            aux = aux2;
        }
        return false;
    }

    private void asignaVotosASindicatoPorDecimales(Sindicato[] sindicatos, int maxColegio, final int COLEGIO_A_COMPROBAR) {
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
