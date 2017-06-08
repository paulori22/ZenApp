package com.example.dispositivo.zenapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import static com.example.dispositivo.zenapp.R.id.buttoncancelar;


public class EditarTarefa extends AppCompatActivity {

    private FirebaseUser usuario;
    private FirebaseDatabase data;
    private Firebase bd;
    private String id;
    private ArrayList<Object> items = new ArrayList<>();
    String nomeTarefa,desc,tagtype,tarefatype;
    int timer;
    EditarTarefaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_cadastrar_tarefa);

        Bundle b = this.getIntent().getExtras();
        final String[] array = b.getStringArray("tarefainfo");
        id = array[3];

        items.add(new TextTextModel("Nome da Tarefa",array[0]));
        items.add(new TextTextModel("Tag",array[1]));
        items.add(new TextTextModel("Tipo da tarefa","Diário"));
        items.add(new TextText2Model("Descrição",array[2]));

        usuario = FirebaseAuth.getInstance().getCurrentUser();
        data = FirebaseDatabase.getInstance();
        bd = new Firebase(usuario,data);

        final RecyclerView rvContacts = (RecyclerView) findViewById(R.id.rvContacts);
        Button buttoncancelar = (Button) findViewById(R.id.buttoncancelar);
        Button buttonsalvar = (Button) findViewById(R.id.buttonsalvar);

        rvContacts.setAdapter(new EditarTarefaAdapter(items));
        adapter = (EditarTarefaAdapter) rvContacts.getAdapter();

        RecyclerView.LayoutManager layout = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        rvContacts.setLayoutManager(layout);

        rvContacts.addItemDecoration(new SimpleDividerItemDecoration(this));

        buttonsalvar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                nomeTarefa = adapter.getNomeTarefa();
                desc = adapter.getDesc();
                tagtype = adapter.getTag();
                tarefatype = adapter.getTarefaType();
                if(!nomeTarefa.matches(""))
                {
                    Tarefa novaTarefa = new Tarefa(id,nomeTarefa,desc,tagtype);
                    if(tarefatype.equals("Diário"))
                    {
                        bd.cadastrarTarefaDiaria(novaTarefa);
                        Log.e("ID = ",id);
                    }
                    else if(tarefatype.equals("Semanal"))
                    {
                        bd.cadastrarTarefaSemanal(novaTarefa);
                        Log.e("ID = ",id);
                    }
                    finish();
                }
                else
                {
                    Snackbar snackbar = Snackbar.make(rvContacts, "Insira um nome para a tarefa", Snackbar.LENGTH_SHORT);
                    View snackbarview = snackbar.getView();
                    TextView tx = (TextView) snackbarview.findViewById(android.support.design.R.id.snackbar_text);
                    tx.setGravity(Gravity.CENTER_HORIZONTAL);
                    tx.setTextAlignment(snackbarview.TEXT_ALIGNMENT_CENTER);
                    tx.setTextColor(Color.YELLOW);
                    snackbar.show();
                }
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
