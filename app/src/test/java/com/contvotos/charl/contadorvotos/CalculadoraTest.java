package com.contvotos.charl.contadorvotos;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class CalculadoraTest {

    private Sindicato[] listaSindicatos = new Sindicato[8];
    private Colegio[] listaColegios = new Colegio[3];

    /**
     * Paso 1
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        listaSindicatos[0] = new Sindicato("Test 1");
        listaSindicatos[1] = new Sindicato("Test 2");
        listaSindicatos[2] = new Sindicato("Test 3");
        listaSindicatos[3] = new Sindicato("Test 4");
        listaSindicatos[4] = new Sindicato("Test 5");
        listaSindicatos[5] = new Sindicato("Test 6");
        listaSindicatos[6] = new Sindicato("Blancos");
        listaSindicatos[7] = new Sindicato("Nulos");
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

    /**
     * Paso 2
     */
    @Test
    public void compruebaSumaColegios() {
        assertFalse(Calculadora.compruebaSumaColegios(listaColegios));

        listaColegios[0] = new Colegio("Tecnicos",3);
        assertFalse(Calculadora.compruebaSumaColegios(listaColegios));

        listaColegios[1] = new Colegio("Especialistas",2);
        assertTrue(Calculadora.compruebaSumaColegios(listaColegios));

        listaColegios[2] = new Colegio();
        assertTrue(Calculadora.compruebaSumaColegios(listaColegios));

        listaColegios[2].setRepresentantes(1);
        assertFalse(Calculadora.compruebaSumaColegios(listaColegios));
    }

    /**
     * Paso 3
     */
    @Test
    public void calculaTotalVotos() {
        assertEquals(58, Calculadora.calculaTotalVotos(listaSindicatos));
        listaSindicatos[0].setVotos(6);
        assertEquals(57, Calculadora.calculaTotalVotos(listaSindicatos));
        listaSindicatos[0].setVotos(7);
    }

    /**
     * Paso 4
     */
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
        listaColegios[0] = new Colegio("Tecnicos",4);
        listaColegios[1] = new Colegio("Especialistas",5);

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


    /**
     * Paso 5
     */
    @Test
    public void extraeEnterosDeRatios() {
        Sindicato s = listaSindicatos[0];
        s.setVotos(7);
        listaColegios[0] = new Colegio("Tecnicos",4);
        listaColegios[1] = new Colegio("Especialistas",5);
        Calculadora.calculaRatiosSindicato(
                s,
                Calculadora.calculaTotalVotos(listaSindicatos),
                listaSindicatos[6].getVotos(),
                listaSindicatos[7].getVotos(),
                listaColegios
        );

        Calculadora.extraeNumerosDeRatios(s);

        assertEquals(0, s.getNumerosRatios()[0][0]);
        assertEquals(6, s.getNumerosRatios()[0][1]);
        assertEquals(2, s.getNumerosRatios()[0][2]);

        assertEquals(0, s.getNumerosRatios()[1][0]);
        assertEquals(7, s.getNumerosRatios()[1][1]);
        assertEquals(7, s.getNumerosRatios()[1][2]);
    }

    /**
     * Paso 5
     */
    @Test
    public void asignaVotosASindicato() {
        Sindicato s = listaSindicatos[2];
        s.setVotos(12);
        listaColegios[0] = new Colegio("Tecnicos",4);
        listaColegios[1] = new Colegio("Especialistas",5);
        Calculadora.calculaRatiosSindicato(
                s,
                Calculadora.calculaTotalVotos(listaSindicatos),
                listaSindicatos[6].getVotos(),
                listaSindicatos[7].getVotos(),
                listaColegios
        );

        Calculadora.extraeNumerosDeRatios(s);

        Calculadora.asignaVotosASindicato(s);

        assertEquals(1, s.getElegidos()[0]);
        assertEquals(1, s.getElegidos()[1]);
        assertEquals(0, s.getElegidos()[2]);
    }

    /**
     * Paso 5
     */
    @Test
    public void compruebaRepetidosPorColegio() {
        listaSindicatos[0].setVotos(7);
        listaSindicatos[1].setVotos(9);
        listaSindicatos[2].setVotos(12);
        listaSindicatos[3].setVotos(4);
        listaSindicatos[4].setVotos(5);
        listaSindicatos[5].setVotos(8);
        listaSindicatos[6].setVotos(5);
        listaSindicatos[7].setVotos(8);
        listaColegios[0] = new Colegio("Tecnicos",4);
        listaColegios[1] = new Colegio("Especialistas",5);

        for (Sindicato s : listaSindicatos) {
            Calculadora.calculaRatiosSindicato(
                    s,
                    Calculadora.calculaTotalVotos(listaSindicatos),
                    listaSindicatos[6].getVotos(),
                    listaSindicatos[7].getVotos(),
                    listaColegios
            );

            Calculadora.extraeNumerosDeRatios(s);
            Calculadora.asignaVotosASindicato(s);
        }

        Calculadora.asignaVotosASindicatoPorDecimales(listaSindicatos, listaColegios);
        // Tecnicos
        assertEquals(listaSindicatos[0].getElegidos()[0], 1);
        assertEquals(listaSindicatos[1].getElegidos()[0], 1);
        assertEquals(listaSindicatos[2].getElegidos()[0], 0);
        assertEquals(listaSindicatos[3].getElegidos()[0], 0);
        assertEquals(listaSindicatos[4].getElegidos()[0], 1);
        assertEquals(listaSindicatos[5].getElegidos()[0], 1);

        //Especialistas
        assertEquals(listaSindicatos[0].getElegidos()[1], 1);
        assertEquals(listaSindicatos[1].getElegidos()[1], 1);
        assertEquals(listaSindicatos[2].getElegidos()[1], 1);
        assertEquals(listaSindicatos[3].getElegidos()[1], 0);
        assertEquals(listaSindicatos[4].getElegidos()[1], 1);
        assertEquals(listaSindicatos[5].getElegidos()[1], 1);

    }

    @Test
    public void todoProceso() {
        listaSindicatos[0].setVotos(50);
        listaSindicatos[1].setVotos(39);
        listaSindicatos[2].setVotos(62);
        listaSindicatos[3].setVotos(22);
        listaSindicatos[4].setVotos(10);
        listaSindicatos[5].setVotos(5);
        listaSindicatos[6].setVotos(1);
        listaSindicatos[7].setVotos(4);
        listaColegios[0] = new Colegio("Tecnicos",4);
        listaColegios[1] = new Colegio("Especialistas", 5);

        for (Sindicato s : listaSindicatos) {
            Calculadora.calculaRatiosSindicato(
                    s,
                    Calculadora.calculaTotalVotos(listaSindicatos),
                    listaSindicatos[6].getVotos(),
                    listaSindicatos[7].getVotos(),
                    listaColegios
            );

            Calculadora.extraeNumerosDeRatios(s);
            Calculadora.asignaVotosASindicato(s);
        }

        try {
            //Calculadora.asignaVotosASindicatoPorDecimales(listaSindicatos, listaColegios);
            //fail("Expected an UnsupportedOperationException to be thrown");
            Calculadora2.asignaVotos(Arrays.copyOf(listaSindicatos, listaSindicatos.length-2), listaColegios);


            // Tecnicos
            assertEquals(listaSindicatos[0].getElegidos()[0], 1);
            assertEquals(listaSindicatos[1].getElegidos()[0], 1);
            assertEquals(listaSindicatos[2].getElegidos()[0], 1);
            assertEquals(listaSindicatos[3].getElegidos()[0], 1);
            assertEquals(listaSindicatos[4].getElegidos()[0], 0);
            assertEquals(listaSindicatos[5].getElegidos()[0], 0);

            //Especialistas
            assertEquals(listaSindicatos[0].getElegidos()[1], 1);
            assertEquals(listaSindicatos[1].getElegidos()[1], 1);
            assertEquals(listaSindicatos[2].getElegidos()[1], 2);
            assertEquals(listaSindicatos[3].getElegidos()[1], 1);
            assertEquals(listaSindicatos[4].getElegidos()[1], 0);
            assertEquals(listaSindicatos[5].getElegidos()[1], 0);
        }catch(UnsupportedOperationException uex) {
            assertThat(uex.getMessage(), is("Hay dos coincidencias de decimales"));
        }
        catch(Exception ex) {
            assertThat(ex.getMessage(), is("ERROR, segundos decimales repetidos."));
        }


    }
}