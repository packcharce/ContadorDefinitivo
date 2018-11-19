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
    Button btn_calcular;

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

        btn_calcular = findViewById(R.id.btn_calcular);
        btn_calcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rellenaColegios();

                Calculadora2 c = new Calculadora2();
                if(c.compruebaSumaColegios(colegios.toArray(new Colegio[0]))) {
                    //muestraDatos();
                    c.calcular(listaSindicatos.toArray(new Sindicato[0]), colegios.toArray(new Colegio[0]));
                    muestraDatos();
                }else{
                    Toast.makeText(v.getContext(), "Error, la suma de los colegios no concuerda", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_mas_ccoo:
                    listaSindicatos.get(0).setVotos(listaSindicatos.get(0).getVotos()+1);
                    ((EditText)findViewById(R.id.num_votos_ccoo)).setText(String.valueOf(listaSindicatos.get(0).getVotos()));
                break;
                case R.id.btn_mas_ugt:
                    listaSindicatos.get(1).setVotos(listaSindicatos.get(1).getVotos()+1);
                    ((EditText)findViewById(R.id.num_votos_ccoo)).setText(String.valueOf(listaSindicatos.get(1).getVotos()));
                break;
                case R.id.btn_mas_uso:
                    listaSindicatos.get(2).setVotos(listaSindicatos.get(2).getVotos()+1);
                    ((EditText)findViewById(R.id.num_votos_uso)).setText(String.valueOf(listaSindicatos.get(2).getVotos()));
                break;
                case R.id.btn_mas_csif:
                    listaSindicatos.get(3).setVotos(listaSindicatos.get(3).getVotos()+1);
                    ((EditText)findViewById(R.id.num_votos_csif)).setText(String.valueOf(listaSindicatos.get(3).getVotos()));
                break;
                case R.id.btn_mas_ind:
                    listaSindicatos.get(4).setVotos(listaSindicatos.get(4).getVotos()+1);
                    ((EditText)findViewById(R.id.num_votos_ind)).setText(String.valueOf(listaSindicatos.get(4).getVotos()));
                break;
                case R.id.btn_mas_suc:
                    listaSindicatos.get(5).setVotos(listaSindicatos.get(5).getVotos()+1);
                    ((EditText)findViewById(R.id.num_votos_suc)).setText(String.valueOf(listaSindicatos.get(5).getVotos()));
                break;
                case R.id.btn_mas_bnc:
                    listaSindicatos.get(6).setVotos(listaSindicatos.get(6).getVotos()+1);
                    ((EditText)findViewById(R.id.num_votos_bnc)).setText(String.valueOf(listaSindicatos.get(6).getVotos()));
                break;
                case R.id.btn_mas_nulo:
                    listaSindicatos.get(7).setVotos(listaSindicatos.get(7).getVotos()+1);
                    ((EditText)findViewById(R.id.num_votos_nulo)).setText(String.valueOf(listaSindicatos.get(7).getVotos()));
                break;



                case R.id.btn_menos_ccoo:
                    if(listaSindicatos.get(0).getVotos() > 0)
                        listaSindicatos.get(0).setVotos(listaSindicatos.get(0).getVotos()-1);
                    ((EditText)findViewById(R.id.num_votos_ccoo)).setText(String.valueOf(listaSindicatos.get(0).getVotos()));
                break;
                case R.id.btn_menos_ugt:
                    if(listaSindicatos.get(1).getVotos() > 0)
                    listaSindicatos.get(1).setVotos(listaSindicatos.get(1).getVotos()-1);
                    ((EditText)findViewById(R.id.num_votos_ugt)).setText(String.valueOf(listaSindicatos.get(1).getVotos()));
                break;
                case R.id.btn_menos_uso:
                    if(listaSindicatos.get(2).getVotos() > 0)
                    listaSindicatos.get(2).setVotos(listaSindicatos.get(2).getVotos()-1);
                    ((EditText)findViewById(R.id.num_votos_uso)).setText(String.valueOf(listaSindicatos.get(2).getVotos()));
                break;
                case R.id.btn_menos_csif:
                    if(listaSindicatos.get(3).getVotos() > 0)
                    listaSindicatos.get(3).setVotos(listaSindicatos.get(3).getVotos()-1);
                    ((EditText)findViewById(R.id.num_votos_csif)).setText(String.valueOf(listaSindicatos.get(3).getVotos()));
                break;
                case R.id.btn_menos_ind:
                    if(listaSindicatos.get(4).getVotos() > 0)
                    listaSindicatos.get(4).setVotos(listaSindicatos.get(4).getVotos()-1);
                    ((EditText)findViewById(R.id.num_votos_ind)).setText(String.valueOf(listaSindicatos.get(4).getVotos()));
                break;
                case R.id.btn_menos_suc:
                    if(listaSindicatos.get(5).getVotos() > 0)
                    listaSindicatos.get(5).setVotos(listaSindicatos.get(5).getVotos()-1);
                    ((EditText)findViewById(R.id.num_votos_suc)).setText(String.valueOf(listaSindicatos.get(5).getVotos()));
                break;
                case R.id.btn_menos_bnc:
                    if(listaSindicatos.get(6).getVotos() > 0)
                    listaSindicatos.get(6).setVotos(listaSindicatos.get(6).getVotos()-1);
                    ((EditText)findViewById(R.id.num_votos_bnc)).setText(String.valueOf(listaSindicatos.get(6).getVotos()));
                break;
                case R.id.btn_menos_nulos:
                    if(listaSindicatos.get(7).getVotos() > 0)
                    listaSindicatos.get(7).setVotos(listaSindicatos.get(7).getVotos()-1);
                    ((EditText)findViewById(R.id.num_votos_nulo)).setText(String.valueOf(listaSindicatos.get(7).getVotos()));
                break;
            }
        }
    };

    private void asignaClickListeners(){
        findViewById(R.id.btn_mas_ccoo).setOnClickListener(listener);
        findViewById(R.id.btn_mas_ugt).setOnClickListener(listener);
        findViewById(R.id.btn_mas_uso).setOnClickListener(listener);
        findViewById(R.id.btn_mas_csif).setOnClickListener(listener);
        findViewById(R.id.btn_mas_ind).setOnClickListener(listener);
        findViewById(R.id.btn_mas_suc).setOnClickListener(listener);
        findViewById(R.id.btn_mas_bnc).setOnClickListener(listener);
        findViewById(R.id.btn_mas_nulo).setOnClickListener(listener);

        findViewById(R.id.btn_menos_ccoo).setOnClickListener(listener);
        findViewById(R.id.btn_menos_ugt).setOnClickListener(listener);
        findViewById(R.id.btn_menos_uso).setOnClickListener(listener);
        findViewById(R.id.btn_menos_csif).setOnClickListener(listener);
        findViewById(R.id.btn_menos_ind).setOnClickListener(listener);
        findViewById(R.id.btn_menos_suc).setOnClickListener(listener);
        findViewById(R.id.btn_menos_bnc).setOnClickListener(listener);
        findViewById(R.id.btn_menos_nulos).setOnClickListener(listener);
    }

    private void muestraDatos() {
        StringBuilder sb = new StringBuilder();
        for (Sindicato s: listaSindicatos) {
            System.out.println(s.toString());
            sb.append(
                    String.format(
                            Locale.getDefault(),
                            "%s:, Tecnicos: %d, Especialistas: %d, Otros: %d\n",
                            s.getNombre(),
                            s.getElegidos()[0],
                            s.getElegidos()[1],
                            s.getElegidos()[2]
                    )
            );
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
        EditText otro = findViewById(R.id.txt_otros);
        colegios.clear();
        colegios.add(new Colegio( "Tecnicos",Integer.parseInt(tec.getText().toString())));
        colegios.add(new Colegio( "Especialistas",Integer.parseInt(esp.getText().toString())));
        colegios.add(new Colegio( "Otros",Integer.parseInt(otro.getText().toString())));
    }

    private void rellenaSindicatosValor(){
        listaSindicatos.get(0).setVotos(
                Integer.parseInt(((EditText)findViewById(R.id.num_votos_ccoo)).getText().toString()));
        listaSindicatos.get(1).setVotos(
                Integer.parseInt(((EditText)findViewById(R.id.num_votos_ugt)).getText().toString()));
        listaSindicatos.get(2).setVotos(
                Integer.parseInt(((EditText)findViewById(R.id.num_votos_uso)).getText().toString()));
        listaSindicatos.get(3).setVotos(
                Integer.parseInt(((EditText)findViewById(R.id.num_votos_csif)).getText().toString()));
        listaSindicatos.get(4).setVotos(
                Integer.parseInt(((EditText)findViewById(R.id.num_votos_ind)).getText().toString()));
        listaSindicatos.get(5).setVotos(
                Integer.parseInt(((EditText)findViewById(R.id.num_votos_suc)).getText().toString()));
        listaSindicatos.get(6).setVotos(
                Integer.parseInt(((EditText)findViewById(R.id.num_votos_bnc)).getText().toString()));
        listaSindicatos.get(7).setVotos(
                Integer.parseInt(((EditText)findViewById(R.id.num_votos_nulo)).getText().toString()));
    }
}
