package com.example.senaizerbini;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;

public class TelaFrequencia extends AppCompatActivity {

    private  RecyclerView recyclerViewFrequencia;
    private FrequenciaAdapter frequenciaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_frequencia);

        recyclerViewFrequencia = findViewById(R.id.recyclerTelaFrequencia);
        ConfigurarRecyclerFrequancia();
    }

    public void ConfigurarRecyclerFrequancia() {

        Query query = Notas.getCollectionReferenceForFaltas();
        FirestoreRecyclerOptions<Frequencia> options = new FirestoreRecyclerOptions.Builder<Frequencia>().setQuery(query,Frequencia.class).build();
        recyclerViewFrequencia.setLayoutManager(new LinearLayoutManager(this));
        frequenciaAdapter = new FrequenciaAdapter(options, this);
        recyclerViewFrequencia.setAdapter(frequenciaAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        frequenciaAdapter.startListening();

    }

    @Override
    protected void onStop() {
        super.onStop();
        frequenciaAdapter.stopListening();
    }

    @Override
    protected void onResume() {
        super.onResume();
        frequenciaAdapter.notifyDataSetChanged();
    }
}