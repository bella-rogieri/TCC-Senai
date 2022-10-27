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

public class ContatoAdapter extends FirestoreRecyclerAdapter <Contatos, ContatoAdapter.ContatoViewHolder>{
    Context context;

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public ContatoAdapter(@NonNull FirestoreRecyclerOptions<Contatos> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull ContatoViewHolder holder, int position, @NonNull Contatos model) {
        holder.nome.setText(model.nome);
        holder.telefoneEmail.setText(model.conteudo);

    }

    @NonNull
    @Override
    public ContatoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_contatos,parent,false);
        return new ContatoViewHolder(view);
    }

    public class ContatoViewHolder extends RecyclerView.ViewHolder {

        TextView nome, telefoneEmail;

        public ContatoViewHolder(@NonNull View itemView) {
            super(itemView);

            nome = itemView.findViewById(R.id.nome);
            telefoneEmail = itemView.findViewById(R.id.nomeTelefoneEmail);
        }
    }
}
