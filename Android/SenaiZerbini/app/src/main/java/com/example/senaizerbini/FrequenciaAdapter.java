package com.example.senaizerbini;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class FrequenciaAdapter extends FirestoreRecyclerAdapter<Frequencia, FrequenciaAdapter.FrequenciaViewHolder> {

 Context context;

    public FrequenciaAdapter(@NonNull FirestoreRecyclerOptions<Frequencia> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull FrequenciaViewHolder holder, int position, @NonNull Frequencia frequencia) {

        holder.disciplinaFrequencia.setText("Disciplina: " + (frequencia.disciplina));
        holder.faltasFrequencia.setText("Faltas: " + (frequencia.faltas));
        holder.aulasPrevistas.setText("Aulas Previstas: " + (frequencia.aulasPrevistas));
    }

    @NonNull
    @Override
    public FrequenciaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_frequencia,parent, false);
        return new FrequenciaViewHolder(view);
    }

     class FrequenciaViewHolder extends RecyclerView.ViewHolder{

        TextView disciplinaFrequencia, faltasFrequencia, aulasPrevistas;

        public FrequenciaViewHolder(@NonNull View itemView) {
            super(itemView);
            disciplinaFrequencia = itemView.findViewById(R.id.frequencia);
            faltasFrequencia = itemView.findViewById(R.id.faltas);
            aulasPrevistas = itemView.findViewById(R.id.aulaPrevista);
        }
    }
}
