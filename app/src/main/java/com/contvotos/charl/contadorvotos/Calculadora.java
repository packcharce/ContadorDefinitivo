package com.contvotos.charl.contadorvotos;

class Calculadora {

    private static final int SUMA_CINCO = 5, SUMA_NUEVE = 9, SUMA_TRECE = 13;

    static boolean compruebaSumaColegios(Colegio[] representantes){
        boolean res = false;
        int aux = 0;
        for(Colegio c:representantes){
            if(c != null)
                aux+=c.getRepresentantes();
        }
        if(aux == SUMA_CINCO || aux == SUMA_NUEVE || aux == SUMA_TRECE)
            res = true;

        return res;
    }

    static void calculaRatiosSindicato(Sindicato sindicato, int votosTotales, int votosBlancos, int votosNulos, Colegio[] colegios){
        int numVotosValidos = votosTotales - votosBlancos - votosNulos;

        // 0 => tecnicos
        // 1 => especialistas
        // 2 => otros
        for (int i = 0; i<colegios.length; i++)
            sindicato.getRatios()[i] = ((colegios[i].getRepresentantes() * 1.0f) / numVotosValidos*1.0f) * (sindicato.getVotos() * 1.0f);
    }

    static int calculaTotalVotos(Sindicato[] listaSindicatos){
        int res = 0;
        for (Sindicato s :
                listaSindicatos) {
            res += s.getVotos();
        }
        return res;
    }

}
