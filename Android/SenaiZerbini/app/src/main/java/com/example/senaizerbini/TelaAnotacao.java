package com.example.senaizerbini;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.Query;

public class TelaAnotacao extends AppCompatActivity {

    FloatingActionButton bt_Anotacao;
    RecyclerView recyclerView;
    NotaAdapter notaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_anotacao);

        bt_Anotacao = findViewById(R.id.bt_tela_anotacao);
        recyclerView = findViewById(R.id.recycler_view);

        bt_Anotacao.setOnClickListener((v) -> startActivity(new Intent(TelaAnotacao.this, TelaAddAnotacao.class)));
        configurarRecyclerview();
    }

    public void configurarRecyclerview(){
        Query query = Notas.getCollectionReferenceforNotes().orderBy("timestamp", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Notas> options = new FirestoreRecyclerOptions.Builder<Notas>().setQuery(query,Notas.class).build();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        notaAdapter = new NotaAdapter(options, this);
        recyclerView.setAdapter(notaAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        notaAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        notaAdapter.stopListening();
    }

    @Override
    protected void onResume() {
        super.onResume();
        notaAdapter.notifyDataSetChanged();
    }
}