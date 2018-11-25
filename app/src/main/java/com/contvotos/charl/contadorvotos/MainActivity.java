package com.contvotos.charl.contadorvotos;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {


    public static final String LISTA_SINDICATOS = "listaSindicatos";
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    ArrayList<Sindicato> listaSindicatos;
    ArrayList<Colegio> colegios;
    Button btn_calcular, btn_calcular_tecs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        setContentView(R.layout.main2);

        listaSindicatos = new ArrayList<>();
        colegios = new ArrayList<>();
        rellenaSindicatos();

        Bundle pasaDatos = new Bundle();


        mRecyclerView = findViewById(R.id.recyclerPrinc);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        pasaDatos.putSerializable(LISTA_SINDICATOS, listaSindicatos);

        RecyclerView.Adapter mAdapter = new MyAdapter(pasaDatos,  new ClickListener() {
            @Override
            public void onPositionClicked(View v, int position) {
            }
        });

        mRecyclerView.setAdapter(mAdapter);

        btn_calcular_tecs = findViewById(R.id.btn_calcular_tecs);
        btn_calcular = findViewById(R.id.btn_calcular_esp);
        btn_calcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(!areColegiosVacios()) {
                    rellenaColegios();

                    Calculadora2 c = new Calculadora2();
                    //if(c.compruebaSumaColegios(colegios.toArray(new Colegio[0]))) {
                    //muestraDatos();
                    try {
                        c.calcular(listaSindicatos.toArray(new Sindicato[0]), colegios.toArray(new Colegio[0]));
                        muestraDatos(2);
                    } catch (UnsupportedOperationException ex) {
                        Toast.makeText(v.getContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
                    }

                }else{
                    Toast.makeText(v.getContext(), getString(R.string.error_sin_representantes), Toast.LENGTH_LONG).show();
                }
                //}else{
                //   Toast.makeText(v.getContext(), "Error, la suma de los colegios no concuerda", Toast.LENGTH_LONG).show();
                //}
            }
        });
        btn_calcular_tecs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!areColegiosVacios()) {
                    rellenaColegios();

                    Calculadora2 c = new Calculadora2();
//                if(c.compruebaSumaColegios(colegios.toArray(new Colegio[0]))) {
                    //muestraDatos();
                    try {
                        c.calcular(listaSindicatos.toArray(new Sindicato[0]), colegios.toArray(new Colegio[0]));
                        muestraDatos(1);
                    }catch (UnsupportedOperationException ex){
                        Toast.makeText(v.getContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(v.getContext(), getString(R.string.error_sin_representantes), Toast.LENGTH_LONG).show();
                }
//                }else{
//                    Toast.makeText(v.getContext(), "Error, la suma de los colegios no concuerda", Toast.LENGTH_LONG).show();
//                }
            }
        });
    }

    /**
     * Comprueba si estan vacio algun colegio
     * @return true si esta vacio algun colegio
     */
    private boolean areColegiosVacios() {
        return ((EditText)findViewById(R.id.txt_tecnicos)).getText().toString().equals("")
                || ((EditText)findViewById(R.id.txt_especialistas)).getText().toString().equals("");
    }

    private void muestraDatos(int colegio) {
        StringBuilder sb = new StringBuilder();
        switch (colegio){
            case 1:
                for (Sindicato s: listaSindicatos) {
                    System.out.println(s.toString());
                    sb.append(
                            String.format(
                                    Locale.getDefault(),
                                    "%s: Tecnicos:\t %d\n",
                                    s.getNombre(),
                                    s.getElegidos()[0]
                            )
                    );
                }
                break;
            case 2:
                for (Sindicato s: listaSindicatos) {
                    System.out.println(s.toString());
                    sb.append(
                            String.format(
                                    Locale.getDefault(),
                                    "%s: Especialistas:\t %d\n",
                                    s.getNombre(),
                                    s.getElegidos()[1]
                            )
                    );
                }
                break;
        }
        for (Colegio c : colegios)
            System.out.println(c.toString());
        ((TextView)findViewById(R.id.resultado)).setText(sb.toString());
    }

    private void rellenaSindicatos() {
        String[] listaNombres = getResources().getStringArray(R.array.listaNombres);
        listaSindicatos.clear();
        for(String s : listaNombres)
            listaSindicatos.add(new Sindicato(s));
    }

    private void rellenaColegios(){
        EditText tec = findViewById(R.id.txt_tecnicos);
        EditText esp = findViewById(R.id.txt_especialistas);
        colegios.clear();
        colegios.add(new Colegio( "Tecnicos",Integer.parseInt(tec.getText().toString())));
        colegios.add(new Colegio( "Especialistas",Integer.parseInt(esp.getText().toString())));
    }
}
