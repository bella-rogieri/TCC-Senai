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

public class NotaAdapter extends FirestoreRecyclerAdapter <Notas, NotaAdapter.NotaViewHolder> {

    Context context;

    public NotaAdapter(@NonNull FirestoreRecyclerOptions<Notas> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull NotaViewHolder holder, int position, @NonNull Notas notas) {
        holder.tituloTextView.setText(notas.title);
        holder.conteudoTextView.setText(notas.content);
        holder.timestampTextView.setText(Notas.timestampp(notas.timestamp));

        holder.itemView.setOnClickListener((v) -> {
            Intent intent = new Intent(context, TelaAddAnotacao.class);
            intent.putExtra("titulo", notas.title);
            intent.putExtra("conteudo",notas.content);
            String docId = this.getSnapshots().getSnapshot(position).getId();
            intent.putExtra("docId",docId);
            context.startActivity(intent);
        });
    }

    @NonNull
    @Override
    public NotaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_notas,parent, false);
        return new NotaViewHolder(view);
    }

    class NotaViewHolder extends RecyclerView.ViewHolder{

        TextView tituloTextView, conteudoTextView, timestampTextView;

        public NotaViewHolder(@NonNull View itemView) {
            super(itemView);
            tituloTextView = itemView.findViewById(R.id.recycler_titulo);
            conteudoTextView = itemView.findViewById(R.id.recycler_conteudo);
            timestampTextView = itemView.findViewById(R.id.recycler_time_stamp);
        }
    }
}
