package com.example.senaizerbini;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;

public class Notas {

    String title;
    String content;
    Timestamp timestamp;

    public Notas() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    static CollectionReference getCollectionReferenceforNotes(){
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        return FirebaseFirestore.getInstance().collection("Notas").document(currentUser.getUid()).collection("Minhas notas");
    }

    static String timestampp(Timestamp timestamp) {
       return new SimpleDateFormat("dd/MM/yyyy").format(timestamp.toDate());
    }
}


