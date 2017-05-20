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
import android.widget.RadioGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


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
        items.add(new TextTextModel("Descrição","Desativado"));
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

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2); // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar);
        RecyclerView rvContacts = (RecyclerView) findViewById(R.id.rvContacts);

        rvContacts.setAdapter(new CadastrarTarefaAdapter(getSupportFragmentManager(),getSampleArrayList()));
        adapter = (CadastrarTarefaAdapter) rvContacts.getAdapter();

        RecyclerView.LayoutManager layout = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        rvContacts.setLayoutManager(layout);

        rvContacts.addItemDecoration(new SimpleDividerItemDecoration(this));


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cadastrar_tarefa, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_cancel)
        {
            finish();
        }
        if (id == R.id.action_check)
        {
            nomeTarefa = adapter.getNomeTarefa();
            desc = adapter.getDesc();
            tagtype = adapter.getTag();
            timer = adapter.getTimer();

            Tarefa novaTarefa = new Tarefa(this.id,nomeTarefa,desc,tagtype);

            bd.cadastrarTarefaDiaria(novaTarefa);

            Log.e("ID = ",this.id);

            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
