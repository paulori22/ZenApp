package com.example.dispositivo.zenapp;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by aluno on 06/04/17.
 */

public class Firebase {
    private FirebaseDatabase database;
    private FirebaseUser user;
    private List<Tarefa> tarefas;

    Firebase(FirebaseUser user, FirebaseDatabase db) {
        database = db;
        this.user = user;
        tarefas = new ArrayList<Tarefa>();
    }

    public void cadastrarTarefaDiaria(Tarefa t) {
        /*database.getReference("TDIARIA/" + user.getUid()).child(t.getId()).child("Titulo").setValue(t.getTitulo());
        database.getReference("TDIARIA/" + user.getUid()).child(t.getId()).child("Descricao").setValue(t.getDescricao());
        database.getReference("TDIARIA/" + user.getUid()).child(t.getId()).child("Tag").setValue(t.getTag());*/
        database.getReference("TDIARIA/" + user.getUid()).child(t.getId()).setValue(t);
    }

    public void removerTarefaDiaria(Tarefa t)

    {
        database.getReference("TDIARIA/" + user.getUid()).child(t.getId()).removeValue();
    }

    public void cadastrarTarefaSemanal(Tarefa t)
    {
        database.getReference("TSEMANAL/" + user.getUid()).child(t.getId()).child("Titulo").setValue(t.getTitulo());
        database.getReference("TSEMANAL/" + user.getUid()).child(t.getId()).child("Descricao").setValue(t.getDescricao());
        database.getReference("TDIARIA/" + user.getUid()).child(t.getId() ).child("Tag").setValue(t.getTag());
    }

    public List<Tarefa> getTarefas() {
        return tarefas;
    }

    public void retornaTarefaDiaria()
    {
        DatabaseReference myRef = database.getReference("TDIARIA/" + user.getUid());



        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {



                for(DataSnapshot datachild: dataSnapshot.getChildren()) {
                    Tarefa novo = new Tarefa();
                    novo = datachild.getValue(Tarefa.class);
                    //novo.setId(datachild.getKey());

                    Log.e("PEGANDO VALOR DO BD "," id = " + novo.getId() + "  titulo = " + novo.getTitulo());
                    tarefas.add(novo);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

    }


}
