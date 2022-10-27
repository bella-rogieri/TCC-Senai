package com.example.senaizerbini;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditarPerfilUsuario extends AppCompatActivity {

    private CircleImageView imagemUsuario;
    private EditText editarNome;
    private Button btSelecionarFoto, btAtualizarDados;
    private Uri selecionarUri;
    private String usuarioID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil_usuario);

        IniciarComponentes();

        btSelecionarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelecionarFotoGaleria();
            }
        });

        btAtualizarDados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = editarNome.getText().toString();

                if (nome.isEmpty()) {
                    Snackbar snackbar = Snackbar.make(v,"Preencha todos os campos",Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }else{
                    AtualizarDadosPerfil(v);
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
                            imagemUsuario.setImageURI(selecionarUri);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            }
    );

    public void SelecionarFotoGaleria() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        activityResultLauncher.launch(intent);
    }

    public void AtualizarDadosPerfil(View view) {

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
                        String nome = editarNome.getText().toString();
                        FirebaseFirestore db = FirebaseFirestore.getInstance();

                        Map<String, Object> usuarios = new HashMap<>();
                        usuarios.put("nome",nome);
                        usuarios.put("foto",foto);
                        usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();

                        db.collection("Usuarios").document(usuarioID)
                                .update("nome",nome,"foto",foto).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Snackbar snackbar = Snackbar.make(view,"Sucesso ao atualizar os dados",Snackbar.LENGTH_INDEFINITE).setAction("Ok", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                finish();
                                            }
                                        });
                                        snackbar.show();
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
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

    }

    public void IniciarComponentes() {
        imagemUsuario = findViewById(R.id.fotoPerfilUsuario);
        editarNome = findViewById(R.id.edit_nome);
        btSelecionarFoto = findViewById(R.id.btMudarFoto);
        btAtualizarDados = findViewById(R.id.btAtualizarDados);
    }
}