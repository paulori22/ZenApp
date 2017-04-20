package com.example.dispositivo.zenapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;


public class CadastrarTarefa extends AppCompatActivity {

    private ArrayList<Object> getSampleArrayList()
    {
        ArrayList<Object> items = new ArrayList<>();
        items.add(new TextButtonModel("textbut1","but1111", "aaaa"));
        items.add(new TextTextModel("ushaushuahs","text222"));
        items.add(new TextTextModel("voxe e xafado","lixemburg"));
        items.add(new TextButtonModel("textbut1","but1111", "bbbb"));

        return items;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cadastrar_tarefa);

        RecyclerView rvContacts = (RecyclerView) findViewById(R.id.rvContacts);

        rvContacts.setAdapter(new CadastrarTarefaAdapter(getSupportFragmentManager(),getSampleArrayList()));

        RecyclerView.LayoutManager layout = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        rvContacts.setLayoutManager(layout);
    }
}
