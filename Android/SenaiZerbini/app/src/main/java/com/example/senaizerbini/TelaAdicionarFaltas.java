package com.example.senaizerbini;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Locale;

public class TelaAdicionarFaltas extends AppCompatActivity {

    private EditText disciplina, faltasAdd,faltas, aulasPrevistas;
    private Button addDisciplina;
    private ImageButton btImageAddFaltas;
    private TextView disciplinaTexto, textFaltas;
    private String stringDisciplina, stringFaltas, addfaltas, stringAulasPrevistas,stringPrevistasCalculo, docId;
    private boolean edicaoFalta = false;
    private int totalFaltas, aulasCalculo;
    private int previstas = (int) 0.75;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_adicionar_faltas);
        IniciarComponentes();

        stringDisciplina = getIntent().getStringExtra("disciplina");
        stringFaltas = getIntent().getStringExtra("faltas");
        stringPrevistasCalculo = getIntent().getStringExtra("previstasCalculo");
        addfaltas = getIntent().getStringExtra("addFaltas");
        stringAulasPrevistas = getIntent().getStringExtra("aulasPrevistas");
        docId = getIntent().getStringExtra("docId");

        disciplina.setText(stringDisciplina);
        faltas.setText(stringFaltas);
        aulasPrevistas.setText(stringAulasPrevistas);
        textFaltas.setText(stringPrevistasCalculo);
        faltasAdd.setText(addfaltas);

        if (docId != null && !docId.isEmpty()){
            edicaoFalta = true;
            disciplinaTexto.setText("Edite a Disciplina");
        }

        addDisciplina.setOnClickListener((v)-> salvarDisciplina());
        SomarFaltas();
    }


    public void salvarDisciplina() {

        String disciplin = disciplina.getText().toString();
        String falts = faltas.getText().toString();
        String previstsCalculo = textFaltas.getText().toString();
        String previstas = aulasPrevistas.getText().toString();

        String addFalts = faltasAdd.getText().toString();


        if (disciplin == null  || disciplin.isEmpty()) {
            disciplina.setError("Preencha a disciplina");
            return;
        }
        Faltas faltas = new Faltas();
        faltas.setDisciplina(disciplin);
        faltas.setFaltas(falts);
        faltas.setAulasPrevistas(previstas);
        faltas.setPrevistasCalculo(previstsCalculo);
        faltas.setAddFaltas(addFalts);

        salvarFaltasFirebase(faltas);
    }

      void salvarFaltasFirebase(Faltas faltas) {
        DocumentReference documentReference;

        if (edicaoFalta){
            documentReference = Notas.getCollectionReferenceForFaltas().document(docId);
        }else{
            documentReference = Notas.getCollectionReferenceForFaltas().document();
        }



         documentReference.set(faltas).addOnCompleteListener(new OnCompleteListener<Void>() {
             @Override
             public void onComplete(@NonNull Task<Void> task) {
                 if (task.isSuccessful()) {
                     Toast.makeText(TelaAdicionarFaltas.this, "Adicionado com sucesso", Toast.LENGTH_SHORT).show();
                     finish();
                 }else{
                     Toast.makeText(TelaAdicionarFaltas.this, "Erro ao salvar", Toast.LENGTH_SHORT).show();
                 }
             }
         });

    }

    public void SomarFaltas() {

        btImageAddFaltas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                double  calculo, calculo2;

                   totalFaltas = Integer.parseInt(faltas.getText().toString());
                    aulasCalculo = Integer.parseInt(aulasPrevistas.getText().toString());
                    calculo = (aulasCalculo * 0.75);
                    calculo2 = (aulasCalculo - calculo);

                    int faltasNova = Integer.parseInt(faltasAdd.getText().toString());
                   totalFaltas =  totalFaltas + faltasNova;
                    faltas.setText(totalFaltas + "");
                    textFaltas.setText(calculo2 + "");
                    faltasAdd.setText(null);

            }
        });
    }

    public void IniciarComponentes() {
        disciplina = findViewById(R.id.editAddDisciplina);
        faltas = findViewById(R.id.editFaltas);
        faltasAdd = findViewById(R.id.editAddFaltas);
        aulasPrevistas = findViewById(R.id.editAulasprevistas);
        textFaltas = findViewById(R.id.textFaltas);
        addDisciplina = findViewById(R.id.btAddDisciplina);
        btImageAddFaltas = findViewById(R.id.btImageButtonAddFaltas);
        disciplinaTexto = findViewById(R.id.textAddFaltas);
    }
}