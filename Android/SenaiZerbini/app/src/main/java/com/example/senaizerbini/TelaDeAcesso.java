package com.example.senaizerbini;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TelaDeAcesso extends AppCompatActivity {

    private Button btlogin,btCadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_de_acesso);

        IniciarComponentes();

        btlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(TelaDeAcesso.this, TelaLogin.class);
                startActivity(intent);

            }
        });
        btCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaDeAcesso.this, TelaCadastro.class);
                startActivity(intent);
            }
        });
    }
    public void IniciarComponentes(){

        btlogin = findViewById(R.id.btlogin);
        btCadastrar = findViewById(R.id.btCadastrar);
    }
}