package com.contvotos.charl.contadorvotos;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
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
        // each data item is just a string in this case
        TextView nombreSindicato;
        EditText numVotos;
        Button anhade, quita;
        private WeakReference<ClickListener> listenerRef;
        SindicatoViewHolder(View v, ClickListener clickListener) {
            super(v);

            listenerRef = new WeakReference<>(clickListener);
            nombreSindicato = v.findViewById(R.id.nombre_sind);
            anhade = v.findViewById(R.id.btn_mas);
            quita = v.findViewById(R.id.btn_menos);
            numVotos = v.findViewById(R.id.num_votos);

            anhade.setOnClickListener(this);
            quita.setOnClickListener(this);

            numVotos.setOnClickListener(this);
        }




        @Override
        public void onClick(View v) {
            int numVotosAux = Integer.parseInt(numVotos.getText().toString());
            switch (v.getId()) {
                case R.id.btn_mas:
                    numVotosAux++;
                    numVotos.setText(String.valueOf(numVotosAux));
                    break;
                case R.id.btn_menos:
                    numVotosAux--;
                    numVotos.setText(String.valueOf(numVotosAux));
                    break;
                case R.id.num_votos:
                    listaSindicatos.get(getAdapterPosition()).setVotos(Integer.parseInt(numVotos.getText().toString()));
                    break;
            }
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
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ((SindicatoViewHolder) viewHolder).nombreSindicato.setText(String.valueOf(listaSindicatos.get(i).getNombre()));
        //((SindicatoViewHolder) viewHolder).numVotos.setText(String.valueOf(listaSindicatos.get(i).getVotos()));
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
