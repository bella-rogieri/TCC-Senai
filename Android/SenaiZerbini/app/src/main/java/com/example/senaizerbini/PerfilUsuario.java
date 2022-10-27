package com.example.senaizerbini;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import de.hdodenhof.circleimageview.CircleImageView;

public class PerfilUsuario extends AppCompatActivity {

    private CircleImageView fotoUsuario;
    private TextView nomeUsuario, emailUsuario, nomeCurso, nomeTurma, nomePeriodo;
    private Button bt_atualizar;
    private String UsuarioId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_usuario);

        IniciarComponente();

        bt_atualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PerfilUsuario.this, EditarPerfilUsuario.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        UsuarioId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        DocumentReference documentReference = db.collection("Usuarios").document(UsuarioId);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                if (value != null) {
                    Glide.with(getApplicationContext()).load(value.getString("foto")).into(fotoUsuario);
                    nomeUsuario.setText(value.getString("nome"));
                    nomeCurso.setText(value.getString("curso"));
                    nomeTurma.setText(value.getString("turma"));
                    nomePeriodo.setText(value.getString("periodo"));
                    emailUsuario.setText(email);
                }
            }
        });
    }

    public void IniciarComponente(){
        fotoUsuario = findViewById(R.id.fotoUsuario);
        nomeUsuario = findViewById(R.id.nomeUsuario);
        emailUsuario = findViewById(R.id.emailUsuario);
        nomeCurso = findViewById(R.id.nomeCurso);
        nomeTurma = findViewById(R.id.nomeTurma);
        nomePeriodo = findViewById(R.id.nomePeriodo);
        bt_atualizar = findViewById(R.id.btAtualizar);
    }
}