package com.example.senaizerbini;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.Query;

public class Tela_contatos extends AppCompatActivity {

    FloatingActionButton btAdicionarContatos;
    RecyclerView recyclerView;
    ContatoAdapter contatoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_contatos);

        IniciarComponentes();
        ConfigurarRecyclerView();

        btAdicionarContatos.setOnClickListener((v) -> startActivity(new Intent(Tela_contatos.this,TelaAddContatos.class)));
    }


    public void IniciarComponentes() {
        btAdicionarContatos = findViewById(R.id.btAdicionarContatos);
        recyclerView = findViewById(R.id.recyclerContatos);
    }

    public void ConfigurarRecyclerView() {
        Query query = Contatos.getCollectionReferenceForContatos().orderBy("nome",Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Contatos> options = new FirestoreRecyclerOptions.Builder<Contatos>().setQuery(query,Contatos.class).build();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        contatoAdapter = new ContatoAdapter(options,this);
        recyclerView.setAdapter(contatoAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        contatoAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        contatoAdapter.stopListening();
    }

    @Override
    protected void onResume() {
        super.onResume();
        contatoAdapter.notifyDataSetChanged();
    }
}