package com.contvotos.charl.contadorvotos;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CalculadoraTest {

    private Sindicato[] listaSindicatos = new Sindicato[8];
    private Colegio[] listaColegios = new Colegio[3];

    @Before
    public void setUp() throws Exception {
        listaSindicatos[0] = new Sindicato("Test 1");
        listaSindicatos[1] = new Sindicato("Test 2");
        listaSindicatos[2] = new Sindicato("Test 3");
        listaSindicatos[3] = new Sindicato("Test 4");
        listaSindicatos[4] = new Sindicato("Test 5");
        listaSindicatos[5] = new Sindicato("Test 6");
        listaSindicatos[6] = new Sindicato("Test 7");
        listaSindicatos[7] = new Sindicato("Test 8");
        listaSindicatos[0].setVotos(7);
        listaSindicatos[1].setVotos(9);
        listaSindicatos[2].setVotos(12);
        listaSindicatos[3].setVotos(4);
        listaSindicatos[4].setVotos(5);
        listaSindicatos[5].setVotos(8);
        listaSindicatos[6].setVotos(5);
        listaSindicatos[7].setVotos(8);

        listaColegios[0] = new Colegio();
        listaColegios[1] = new Colegio();
        listaColegios[2] = new Colegio();
    }

    @Test
    public void compruebaSumaColegios() {
        assertFalse(Calculadora.compruebaSumaColegios(listaColegios));

        listaColegios[0] = new Colegio(3);
        assertFalse(Calculadora.compruebaSumaColegios(listaColegios));

        listaColegios[1] = new Colegio(2);
        assertTrue(Calculadora.compruebaSumaColegios(listaColegios));

        listaColegios[2] = new Colegio();
        assertTrue(Calculadora.compruebaSumaColegios(listaColegios));

        listaColegios[2].setRepresentantes(1);
        assertFalse(Calculadora.compruebaSumaColegios(listaColegios));
    }

    @Test
    public void calculaRatiosSindicato() {
        listaSindicatos[0].setVotos(7);
        listaSindicatos[1].setVotos(9);
        listaSindicatos[2].setVotos(12);
        listaSindicatos[3].setVotos(4);
        listaSindicatos[4].setVotos(5);
        listaSindicatos[5].setVotos(8);
        listaSindicatos[6].setVotos(5);
        listaSindicatos[7].setVotos(8);
        listaColegios[0] = new Colegio(4);
        listaColegios[1] = new Colegio(5);

        for (Sindicato s :
                listaSindicatos) {
            Calculadora.calculaRatiosSindicato(
                    s,
                    Calculadora.calculaTotalVotos(listaSindicatos),
                    listaSindicatos[6].getVotos(),
                    listaSindicatos[7].getVotos(),
                    listaColegios
            );
        }

        //region Sind 1
        assertEquals(
                0.62f,
                listaSindicatos[0].getRatios()[0],
                0.01f
        );
        assertEquals(
                0.77f,
                listaSindicatos[0].getRatios()[1],
                0.01f
        );
        //endregion

        //region Sind 2
        assertEquals(
                0.8f,
                listaSindicatos[1].getRatios()[0],
                0.01f
        );
        assertEquals(
                1.0f,
                listaSindicatos[1].getRatios()[1],
                0.01f
        );
        //endregion

        //region Sind 3
        assertEquals(
                1.06f,
                listaSindicatos[2].getRatios()[0],
                0.01f
        );
        assertEquals(
                1.33f,
                listaSindicatos[2].getRatios()[1],
                0.01f
        );
        //endregion

        //region Sind 4
        assertEquals(
                0.35f,
                listaSindicatos[3].getRatios()[0],
                0.01f
        );
        assertEquals(
                0.44f,
                listaSindicatos[3].getRatios()[1],
                0.01f
        );
        //endregion
    }

    @Test
    public void calculaTotalVotos() {
        assertEquals(58, Calculadora.calculaTotalVotos(listaSindicatos));
        listaSindicatos[0].setVotos(6);
        assertEquals(57, Calculadora.calculaTotalVotos(listaSindicatos));
        listaSindicatos[0].setVotos(7);
    }
}