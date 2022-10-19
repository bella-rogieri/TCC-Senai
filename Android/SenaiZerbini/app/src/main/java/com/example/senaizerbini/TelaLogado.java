package com.example.senaizerbini;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class TelaLogado extends AppCompatActivity {

    private ImageView image_anotacao, image_contatos, image_frequencia, image_faltas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_logado);

        image_anotacao = findViewById(R.id.imgAnotacoes);
        image_contatos = findViewById(R.id.imgContatos);
        image_frequencia = findViewById(R.id.imgFrequencia);
        image_faltas = findViewById(R.id.imgFaltas);


        image_anotacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaLogado.this, TelaAnotacao.class);
                startActivity(intent);
            }
        });

        image_contatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaLogado.this, Tela_contatos.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int itemID = item.getItemId();

        if (itemID == R.id.perfil) {

        }else if (itemID == R.id.exit) {
            Toast.makeText(TelaLogado.this, "Usuário Deslogado", Toast.LENGTH_SHORT).show();
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(TelaLogado.this, TelaLogin.class);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}