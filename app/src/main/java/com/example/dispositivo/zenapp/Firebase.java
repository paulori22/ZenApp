package com.example.dispositivo.zenapp;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import android.util.Log;

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

    public void cadastrarTarefaDiaria(Tarefa t, String id)
    {
        database.getReference("TDIARIA/" + user.getUid()).child(id).child("Titulo").setValue(t.getTitulo());
        database.getReference("TDIARIA/" + user.getUid()).child(id).child("Descricao").setValue(t.getDescricao());
        database.getReference("TDIARIA/" + user.getUid()).child(id).child("Tag").setValue(t.getTag());
    }

    public void cadastrarTarefaSemanal(Tarefa t, String id)
    {
        database.getReference("TSEMANAL/" + user.getUid()).child(id).child("Titulo").setValue(t.getTitulo());
        database.getReference("TSEMANAL/" + user.getUid()).child(id).child("Descricao").setValue(t.getDescricao());
        database.getReference("TDIARIA/" + user.getUid()).child(id).child("Tag").setValue(t.getTag());
    }

    public void retornaTarefaDiaria(Tarefa tarefa)
    {
        final String errorTAG=null;
        ValueEventListener listenerTarefa = new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                Tarefa aux = dataSnapshot.getValue(Tarefa.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {
                Log.w(errorTAG, "loadPost:onCacelled", databaseError.toException());
            }
        };
    }


}
