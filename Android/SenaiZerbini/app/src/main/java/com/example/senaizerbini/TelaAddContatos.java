package com.example.senaizerbini;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;

public class TelaAddContatos extends AppCompatActivity {

    private EditText nomeEditText, conteudoEditText;
    private ImageButton btSalvarConteudo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_add_contatos);

        IniciarComponentes();

        btSalvarConteudo.setOnClickListener( (v) -> SalvarDados());


    }

    public void IniciarComponentes(){
        nomeEditText = findViewById(R.id.tituloContatos);
        conteudoEditText = findViewById(R.id.contato);
        btSalvarConteudo = findViewById(R.id.btSalvarContatos);
    }

    public void SalvarDados() {
        String nome = nomeEditText.getText().toString();
        String conteudo = conteudoEditText.getText().toString();

        if (nome == null || nome.isEmpty()) {
            nomeEditText.setError("Preencha o nome");
            return;
        }
        Contatos contatos = new Contatos();
        contatos.setNome(nome);
        contatos.setConteudo(conteudo);

        SalvarContatosFireBase(contatos);
    }

    public void SalvarContatosFireBase(Contatos contatos){
        DocumentReference documentReference;
        documentReference = Contatos.getCollectionReferenceForContatos().document();

        documentReference.set(contatos).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
              if (task.isSuccessful()) {
                  Toast.makeText(TelaAddContatos.this, "Contato salvo com sucesso",Toast.LENGTH_SHORT).show();
                  finish();
              }else {
                  Toast.makeText(TelaAddContatos.this, "Falha no salvamento", Toast.LENGTH_SHORT).show();
              }
            }
        });

    }
}