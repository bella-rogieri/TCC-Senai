package com.example.senaizerbini;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class TelaCadastro extends AppCompatActivity {

    private CircleImageView fotoUSuario;
    private EditText edit_nome, edit_senha, edit_email, edit_curso, edit_turma, edit_periodo;
    private Button bt_cadastrar,btSelecionarFoto;
    private TextView mensagemErro;


    private Uri selecionarUri;
    private  String usuarioID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_cadastro);

        IniciarComponentes();

        edit_nome.addTextChangedListener(cadastroTextWatcher);
        edit_email.addTextChangedListener(cadastroTextWatcher);
        edit_senha.addTextChangedListener(cadastroTextWatcher);
        edit_curso.addTextChangedListener(cadastroTextWatcher);
        edit_turma.addTextChangedListener(cadastroTextWatcher);
        edit_periodo.addTextChangedListener(cadastroTextWatcher);

        bt_cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CadastrarUsuario(v);
            }
        });
        btSelecionarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SelecionarFotoDaGAleria();
            }
        });

    }

    public void CadastrarUsuario(View view) {

        String email = edit_email.getText().toString();
        String senha = edit_senha.getText().toString();

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    SalvarDadosUsuario();

                    Snackbar snackbar = Snackbar.make(view, "Cadastro realizado com sucesso", Snackbar.LENGTH_INDEFINITE)
                            .setAction("Ok", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                  finish();
                                }
                            });
                    snackbar.show();
                }else {
                    String erro;

                    try {
                        throw task.getException();

                    }catch(FirebaseAuthWeakPasswordException e){
                        erro = "Coloque uma senha com no mínimo 6 caracteres!";
                    }catch(FirebaseAuthInvalidCredentialsException e){
                        erro = "Digite um e-mail válido!";
                    }catch(FirebaseAuthUserCollisionException e){
                        erro = "E-mail já cadastrado!";
                    }catch(FirebaseNetworkException e){
                        erro = "Sem conexão com a Internet!";
                    }
                    catch(Exception e){
                        erro = "Erro ao cadastrar o usuário";
                    }
                    mensagemErro.setText(erro);
                }
            }
        });
    }

    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        selecionarUri = data.getData();

                        try {
                            fotoUSuario.setImageURI(selecionarUri);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            }
    );

    public void SelecionarFotoDaGAleria() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        activityResultLauncher.launch(intent);
    }

    public void SalvarDadosUsuario() {

        String nomeArquivo = UUID.randomUUID().toString();
        final StorageReference reference = FirebaseStorage.getInstance().getReference("/imagens/" + nomeArquivo);
        reference.putFile(selecionarUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                     String foto = uri.toString();
                     //Iniciar banco de dados - firestore
                        String nome = edit_nome.getText().toString();
                        String curso = edit_curso.getText().toString();
                        String turma = edit_turma.getText().toString();
                        String periodo = edit_periodo.getText().toString();
                        FirebaseFirestore db = FirebaseFirestore.getInstance();

                        Map<String, Object> usuarios = new HashMap<>();
                        usuarios.put("nome",nome);
                        usuarios.put("curso",curso);
                        usuarios.put("turma",turma);
                        usuarios.put("periodo",periodo);
                        usuarios.put("foto",foto);
                        usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();

                        DocumentReference documentReference = db.collection("Usuarios").document(usuarioID);
                        documentReference.set(usuarios).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Log.i("db", "Sucesso ao salvar os dados!");
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.i("db_error", "erro ao salvar os dados!" + e.toString());
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    public void IniciarComponentes(){
        fotoUSuario = findViewById(R.id.fotoUsuario);
        btSelecionarFoto = findViewById(R.id.bt_selecionarFoto);
        bt_cadastrar = findViewById(R.id.btCadastrarTelaCadastro);
        edit_nome = findViewById(R.id.editNome);
        edit_senha = findViewById(R.id.editSenha);
        edit_email = findViewById(R.id.editEmail);
        edit_curso = findViewById(R.id.editCurso);
        edit_turma = findViewById(R.id.editTurma);
        edit_periodo = findViewById(R.id.editPeriodo);
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
                String curso = edit_curso.getText().toString();
                String turma = edit_turma.getText().toString();
                String periodo = edit_periodo.getText().toString();

                if (!nome.isEmpty() && !email.isEmpty() && !senha.isEmpty() && !curso.isEmpty() && !turma.isEmpty() && !periodo.isEmpty()){
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
