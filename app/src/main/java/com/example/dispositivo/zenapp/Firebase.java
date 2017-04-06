package com.example.dispositivo.zenapp;
import android.widget.DatePicker;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
/**
 * Created by aluno on 06/04/17.
 */

public class Firebase {
    private FirebaseDatabase database;
    private FirebaseUser user;

    Firebase(FirebaseUser user,FirebaseDatabase db) {
        database = db;
        this.user=user;
    }

    public void cadastrarTarefaDiaria(String date, String titulo, int prioridade)
    {
        database.getReference("TDIARIA/" + user.getUid()).child(date).child("Titulo").setValue(titulo);
        database.getReference("TDIARIA/" + user.getUid()).child(date).child("Prioridade").setValue(prioridade);
    }

}
