package com.contvotos.charl.contadorvotos;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    public static final String LISTA_SINDICATOS = "listaSindicatos";
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    ArrayList<Sindicato> listaSindicatos;
    Button btn_calcular;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listaSindicatos = new ArrayList<>();
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

        btn_calcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void rellenaSindicatos() {
        String[] listaNombres = getResources().getStringArray(R.array.listaNombres);
        for(String s : listaNombres)
            listaSindicatos.add(new Sindicato(s));
    }
}
