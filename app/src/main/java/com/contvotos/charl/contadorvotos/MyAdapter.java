package com.contvotos.charl.contadorvotos;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter {

    private static final String LISTA_SINDICATOS = "listaSindicatos";
    private static ArrayList<Sindicato> listaSindicatos;

    public static class SindicatoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        // Elementos de la vista a cargar
        TextView nombreSindicato;
        EditText numVotos;
        Button anhade, quita;
        private WeakReference<ClickListener> listenerRef;
        SindicatoViewHolder(View v, ClickListener clickListener) {
            super(v);

            listenerRef = new WeakReference<>(clickListener);
            nombreSindicato = v.findViewById(R.id.nombre_sind);
            anhade = v.findViewById(R.id.btn_mas_ugt);
            quita = v.findViewById(R.id.btn_menos_ccoo);
            numVotos = v.findViewById(R.id.num_votos_ccoo);

            anhade.setOnClickListener(this);
            quita.setOnClickListener(this);

            numVotos.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int numVotosAux = 0;
            if(!numVotos.getText().toString().equals("")){
                numVotosAux = Integer.parseInt(numVotos.getText().toString());
            }
            switch (v.getId()) {
                case R.id.btn_mas_ugt:
                    if(numVotosAux < Integer.MAX_VALUE)
                    numVotosAux++;
                    numVotos.setText(String.valueOf(numVotosAux));
                    break;
                case R.id.btn_menos_ccoo:
                    if(numVotosAux > 0) {
                        numVotosAux--;
                        numVotos.setText(String.valueOf(numVotosAux));
                    }
                    break;
                case R.id.num_votos_ccoo:
                    //listaSindicatos.get(getAdapterPosition()).setVotos(numVotosAux);
                    break;
            }
            listaSindicatos.get(getAdapterPosition()).setVotos(numVotosAux);
            listenerRef.get().onPositionClicked(v, getAdapterPosition());
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v;
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());

        v = layoutInflater.inflate(R.layout.item_reciclerview, viewGroup, false);
        return new SindicatoViewHolder(v, clickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        ((SindicatoViewHolder) viewHolder).nombreSindicato.setText(String.valueOf(listaSindicatos.get(i).getNombre()));
        ((SindicatoViewHolder) viewHolder).numVotos.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().equals(""))
                    listaSindicatos.get(i).setVotos(Integer.parseInt(s.toString()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaSindicatos.size();
    }

    private final ClickListener clickListener;

    MyAdapter(Bundle b, ClickListener clickListener) {
        this.clickListener=clickListener;
        listaSindicatos = (ArrayList<Sindicato>) b.getSerializable(LISTA_SINDICATOS);
    }
}
