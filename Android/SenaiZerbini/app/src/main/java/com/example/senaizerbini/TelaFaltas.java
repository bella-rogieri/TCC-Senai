package com.example.senaizerbini;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.Query;

public class TelaFaltas extends AppCompatActivity {

    FloatingActionButton btAddFaltas;
    RecyclerView recyclerView;
    FaltasAdapter faltasAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_faltas);

        IniciarComponentes();
        ConfigurarRecyclerView();

        btAddFaltas.setOnClickListener((v) -> startActivity(new Intent(TelaFaltas.this, TelaAdicionarFaltas.class)));
    }

    public void IniciarComponentes() {
        btAddFaltas = findViewById(R.id.btAdicionarFaltas);
        recyclerView = findViewById(R.id.recyclerFaltas);
    }

    public void ConfigurarRecyclerView() {
        Query query = Notas.getCollectionReferenceForFaltas();
        FirestoreRecyclerOptions<Faltas> options = new FirestoreRecyclerOptions.Builder<Faltas>().setQuery(query,Faltas.class).build();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        faltasAdapter = new FaltasAdapter(options,this);
        recyclerView.setAdapter(faltasAdapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        faltasAdapter.startListening();

    }

    @Override
    protected void onStop() {
        super.onStop();
        faltasAdapter.stopListening();
    }

    @Override
    protected void onResume() {
        super.onResume();
        faltasAdapter.notifyDataSetChanged();
    }
}