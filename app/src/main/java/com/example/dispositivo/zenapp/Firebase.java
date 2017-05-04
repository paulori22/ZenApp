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

/**
 * Created by aluno on 06/04/17.
 */

public class Firebase {
    private FirebaseDatabase database;
    private FirebaseUser user;
    private List<Tarefa> tarefas = new ArrayList<>();

    Firebase(FirebaseUser user, FirebaseDatabase db) {
        database = db;
        this.user = user;
    }

    public void cadastrarTarefaDiaria(Tarefa t) {
        database.getReference("TDIARIA/" + user.getUid()).child(t.getId()).child("Titulo").setValue(t.getTitulo());
        database.getReference("TDIARIA/" + user.getUid()).child(t.getId()).child("Descricao").setValue(t.getDescricao());
        database.getReference("TDIARIA/" + user.getUid()).child(t.getId()).child("Tag").setValue(t.getTag());
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

    public void retornaTarefaDiaria()
    {
    /*    //final DatabaseReference aux = (DatabaseReference) database.getReference("TDIARIA/" + IDUser + "06-04-2017").orderByChild("Titulo").toString();
        DatabaseReference ref = database.getReference("TDIARIA/" + user.getUid()).child("06-04-2017");
        Query query = ref.orderByChild("Titulo").limitToFirst(1);
        Log.d("Teste recv", query.);
    */
        DatabaseReference myRef = database.getReference("TDIARIA/" + user.getUid());



        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {



                for(DataSnapshot datachild: dataSnapshot.getChildren()) {
                    Tarefa novo = datachild.getValue(Tarefa.class);
                    novo.setId(datachild.getKey());
                    //datachild.getChildren()
                    long count = datachild.getChildrenCount();

                    for (DataSnapshot child: datachild.getChildren())
                    {
                        //novo.setDescricao(child.getKey());
                        //novo.setTitulo(child.getKey());
                    }
                    Log.e("DATABASE","Count filhos = " + String.valueOf(count) + "id = " +novo.getId() + " titulo = " + novo.getTitulo() + " descricao = " + novo.getTitulo());
                    tarefas.add(novo);


                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                //return NULL;
            }
        });
    }


}
