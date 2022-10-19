package com.example.senaizerbini;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;

public class TelaAddAnotacao extends AppCompatActivity {

    EditText tituloEdicao, conteudoEdicao;
    ImageButton salvarBotao;
    TextView pagina_titulo;
    String titulo,conteudo,docId;
    boolean edicaoModo = false;
    TextView deletaNota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_add_anotacao);

        tituloEdicao = findViewById(R.id.txt_notas_titulo);
        conteudoEdicao = findViewById(R.id.txt_conteudo);
        salvarBotao = findViewById(R.id.sv_notas);
        pagina_titulo = findViewById(R.id.txt_titulo);
        deletaNota = findViewById(R.id.deleteNota);

        titulo = getIntent().getStringExtra("titulo");
        conteudo = getIntent().getStringExtra("conteudo");
        docId = getIntent().getStringExtra("docId");

        if (docId != null && !docId.isEmpty()) {
            edicaoModo = true;
        }

        tituloEdicao.setText(titulo);
        conteudoEdicao.setText(conteudo);

        if (edicaoModo) {
            pagina_titulo.setText("Edite sua nota");
            deletaNota.setVisibility(View.VISIBLE);
        }


        salvarBotao.setOnClickListener((v) -> salvarNotas());
        deletaNota.setOnClickListener((v)-> deletaNotaFirebase () );
    }

    public void salvarNotas(){

        String titulo = tituloEdicao.getText().toString();
        String conteudo = conteudoEdicao.getText().toString();
        if (titulo == null || titulo.isEmpty()) {
            tituloEdicao.setError("Preencha o título");
            return;
        }
        Notas nota = new Notas();
        nota.setTitle(titulo);
        nota.setContent(conteudo);
        nota.setTimestamp(Timestamp.now());

        saveNoteFirebase(nota);
    }
    void saveNoteFirebase(Notas nota){
        DocumentReference documentReference;

        if (edicaoModo){
            //Atualizar a nota
            documentReference = Notas.getCollectionReferenceforNotes().document(docId);
        }else{
            //Cria uma nova nota
            documentReference = Notas.getCollectionReferenceforNotes().document();
        }


        documentReference.set(nota).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(TelaAddAnotacao.this, "Nota adicionada com sucesso", Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(TelaAddAnotacao.this, "Nota não foi adicionada", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void deletaNotaFirebase(){
        DocumentReference documentReference;

        documentReference = Notas.getCollectionReferenceforNotes().document(docId);

        documentReference.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(TelaAddAnotacao.this, "Nota deletada com sucesso", Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(TelaAddAnotacao.this, "Nota não foi deletada", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}