package com.example.dispositivo.zenapp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;

public class CadastrarTarefa extends AppCompatActivity {

    private FirebaseUser usuario;
    private FirebaseDatabase data;
    private Firebase bd;
    private String id;
    private final String tipo_diario = "TDIARIA/";
    private final String tipo_semanal = "TSEMANAL/";
    private ArrayList<Object> items = new ArrayList<>();

    String nomeTarefa,desc,tagtype,tarefatype;
    CadastrarTarefaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cadastrar_tarefa);

        Bundle b = this.getIntent().getExtras();
        String[] array = b.getStringArray("com.example.dispositivo.zenapp.id_tarefa");

        tarefatype = array[1];
        id = array[0];

        items.add(new TextTextModel("Nome da Tarefa","Tarefa"));
        items.add(new TextTextModel("Tag","Desativado"));
        if(tarefatype.equals(tipo_diario))
        {
            items.add(new TextTextModel("Tipo da tarefa","Diário"));
        }
        else
        {
            items.add(new TextTextModel("Tipo da tarefa","Semanal"));
        }
        items.add(new TextText2Model("Descrição","Desativado"));



        usuario = FirebaseAuth.getInstance().getCurrentUser();
        data = FirebaseDatabase.getInstance();
        bd = new Firebase(usuario,data);

        final RecyclerView rvContacts = (RecyclerView) findViewById(R.id.rvContacts);
        Button buttoncancelar = (Button) findViewById(R.id.buttoncancelar);
        Button buttonsalvar = (Button) findViewById(R.id.buttonsalvar);

        rvContacts.setAdapter(new CadastrarTarefaAdapter(items));
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
                //tarefatype = adapter.getTarefaType();
                if(!nomeTarefa.equals(""))
                {
                    Tarefa novaTarefa = new Tarefa(id,nomeTarefa,desc,tagtype,tarefatype);
                    if(tarefatype.equals(tipo_diario))
                    {
                        bd.cadastrarTarefaDiaria(novaTarefa);
                        Log.e("ID = ",id);
                    }
                    else if(tarefatype.equals(tipo_semanal))
                    {
                        bd.cadastrarTarefaSemanal(novaTarefa);
                        Log.e("ID = ",id);
                    }

                    finish();
                }
                else
                {
                    Snackbar snackbar = Snackbar.make(rvContacts, "Insira um nome para a tarefa", Snackbar.LENGTH_LONG);
                    View snackbarview = snackbar.getView();
                    TextView tx = (TextView) snackbarview.findViewById(android.support.design.R.id.snackbar_text);
                    tx.setGravity(Gravity.CENTER_HORIZONTAL);
                    tx.setTextAlignment(snackbarview.TEXT_ALIGNMENT_CENTER);
                    tx.setTextColor(Color.YELLOW);
                    snackbar.show();
                }

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
