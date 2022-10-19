package com.example.senaizerbini;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class TelaCadastro extends AppCompatActivity {

    private CircleImageView fotoUSuario;
    private EditText edit_nome, edit_senha, edit_email;
    private Button bt_cadastrar,btSelecionarFoto;
    private TextView mensagemErro;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_cadastro);

        IniciarComponentes();
        edit_nome.addTextChangedListener(cadastroTextWatcher);
        edit_email.addTextChangedListener(cadastroTextWatcher);
        edit_senha.addTextChangedListener(cadastroTextWatcher);

    }
    public void IniciarComponentes(){
        fotoUSuario = findViewById(R.id.fotoUsuario);
        btSelecionarFoto = findViewById(R.id.bt_selecionarFoto);
        bt_cadastrar = findViewById(R.id.btCadastrarTelaCadastro);
        edit_nome = findViewById(R.id.editNome);
        edit_senha = findViewById(R.id.editSenha);
        edit_email = findViewById(R.id.editEmail);
        mensagemErro = findViewById(R.id.txt_mensagemErroTelaCadastro);

    }


        TextWatcher cadastroTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String nome = edit_nome.getText().toString();
                String email = edit_email.getText().toString();
                String senha = edit_senha.getText().toString();

                if (!nome.isEmpty() && !email.isEmpty() && !senha.isEmpty()){
                    bt_cadastrar.setEnabled(true);
                    bt_cadastrar.setBackgroundColor(getResources().getColor(R.color.red));
                }else{

                    bt_cadastrar.setEnabled(false);
                    bt_cadastrar.setBackgroundColor(getResources().getColor(R.color.light_gray));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        };
    }
