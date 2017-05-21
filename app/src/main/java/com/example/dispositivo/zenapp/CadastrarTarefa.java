package com.example.dispositivo.zenapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import static com.example.dispositivo.zenapp.R.id.buttoncancelar;


public class CadastrarTarefa extends AppCompatActivity {

    private FirebaseUser usuario;
    private FirebaseDatabase data;
    private Firebase bd;
    private String id;


    private ArrayList<Object> getSampleArrayList()
    {
        ArrayList<Object> items = new ArrayList<>();
        items.add(new TextTextModel("Nome da Tarefa","Tarefa"));
        items.add(new TextTextModel("Tag","Desativado"));
        items.add(new TextTextModel("Timer","Desativado"));
        items.add(new TextText2Model("Descrição","Desativado"));
        return items;
    }
    String nomeTarefa,desc,tagtype;
    int timer;
    CadastrarTarefaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_cadastrar_tarefa);

        id = getIntent().getStringExtra("com.example.dispositivo.zenapp.id_tarefa");

        //Log.e("ID = ",this.id);


        usuario = FirebaseAuth.getInstance().getCurrentUser();
        data = FirebaseDatabase.getInstance();
        bd = new Firebase(usuario,data);

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2); // Attaching the layout to the toolbar object
        //setSupportActionBar(toolbar);
        RecyclerView rvContacts = (RecyclerView) findViewById(R.id.rvContacts);
        Button buttoncancelar = (Button) findViewById(R.id.buttoncancelar);
        Button buttonsalvar = (Button) findViewById(R.id.buttonsalvar);

        rvContacts.setAdapter(new CadastrarTarefaAdapter(getSupportFragmentManager(),getSampleArrayList()));
        adapter = (CadastrarTarefaAdapter) rvContacts.getAdapter();

        RecyclerView.LayoutManager layout = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        rvContacts.setLayoutManager(layout);

        rvContacts.addItemDecoration(new SimpleDividerItemDecoration(this));

        buttonsalvar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                nomeTarefa = adapter.getNomeTarefa();
                desc = adapter.getDesc();
                tagtype = adapter.getTag();
                timer = adapter.getTimer();

                Tarefa novaTarefa = new Tarefa(id,nomeTarefa,desc,tagtype);

                bd.cadastrarTarefaDiaria(novaTarefa);

                Log.e("ID = ",id);

                finish();
            }
        });

        buttoncancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
