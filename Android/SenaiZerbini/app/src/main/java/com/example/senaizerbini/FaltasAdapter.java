package com.example.senaizerbini;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class FaltasAdapter extends FirestoreRecyclerAdapter<Faltas, FaltasAdapter.FaltasViewHolder> {

    Context context;


    public FaltasAdapter(@NonNull FirestoreRecyclerOptions<Faltas> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull FaltasViewHolder holder, int position, @NonNull Faltas faltas) {
        holder.disciplina.setText(faltas.disciplina);
        holder.faltas.setText(faltas.faltas);
        holder.previstasCalculo.setText(faltas.previstasCalculo);
        holder.addFaltas.setText(faltas.addFaltas);
        holder.aulasPrevistas.setText(faltas.aulasPrevistas);


        holder.itemView.setOnClickListener((v) -> {
            Intent intent = new Intent(context, TelaAdicionarFaltas.class);
            intent.putExtra("disciplina", faltas.disciplina);
            intent.putExtra("faltas", faltas.faltas);
            intent.putExtra("previstasCalculo", faltas.previstasCalculo);
            intent.putExtra("addFaltas", faltas.addFaltas);
            intent.putExtra("aulasPrevistas", faltas.aulasPrevistas);

            String docId = this.getSnapshots().getSnapshot(position).getId();
            intent.putExtra("docId", docId);
            context.startActivity(intent);
        });
    }

    @NonNull
    @Override
    public FaltasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_faltas, parent,false);
        return new FaltasViewHolder(view);
    }

    class FaltasViewHolder extends RecyclerView.ViewHolder {

       private TextView disciplina, faltas, addFaltas, aulasPrevistas, previstasCalculo;

        public FaltasViewHolder(@NonNull View itemView) {
            super(itemView);

            disciplina = itemView.findViewById(R.id.recyclerDisciplina);
            faltas = itemView.findViewById(R.id.recyclerFaltas);
            previstasCalculo = itemView.findViewById(R.id.recyclerprevistasCalculo);
            addFaltas = itemView.findViewById(R.id.recyclerAddFaltas);
            aulasPrevistas = itemView.findViewById(R.id.recyclerAulasPrevistas);

        }
    }

}
