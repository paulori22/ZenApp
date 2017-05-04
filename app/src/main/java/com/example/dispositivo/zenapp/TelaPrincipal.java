package com.example.dispositivo.zenapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.design.widget.CoordinatorLayout;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.github.brnunes.swipeablerecyclerview.SwipeableRecyclerViewTouchListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class TelaPrincipal extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,ClickRecyclerView_Interface {

    int del = 0;
    int id = 0;
    Tarefa retorna;
    private FirebaseUser usuario;
    private FirebaseDatabase data;
    private Firebase bd;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    RecyclerTesteAdapter adapter;
    private List<Tarefa> tarefasListas = new ArrayList<>();
    private FloatingActionButton floatingActionButton;
    private RecyclerView.OnItemTouchListener onItemTouchListener;

    // UI references.

    private TextView NomeView;
    private TextView EmailView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_principal);

        setaRecyclerView();
        setaButtons();
        setatouch();
        listenersButtons();
        listenersSwipeable();

        usuario = FirebaseAuth.getInstance().getCurrentUser();
        data = FirebaseDatabase.getInstance();
        bd = new Firebase(usuario,data);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//comentando para nao sobreescrever o floatbutton do recyclerviewr
/*
        FloatingActionButton add_tarefa = (FloatingActionButton) findViewById(R.id.add_tarefa);
        add_tarefa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usuario = FirebaseAuth.getInstance().getCurrentUser();
                data = FirebaseDatabase.getInstance();
                Firebase firebase = new Firebase(usuario,data);
                //Criar classe e passar para o firesebase
                //firebase.cadastrarTarefaDiaria("06-04-2017","Estudar para prova",10);

                Intent CadastrarTarefa = new Intent(getApplicationContext(),CadastrarTarefa.class);
                startActivity(CadastrarTarefa);
            }
        });
        */

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.tela_principal, menu);
        NomeView = (TextView) findViewById(R.id.nome_usuario);
        NomeView.setText("Bem-vindo usuário");
        EmailView = (TextView) findViewById(R.id.email_usuario);
        EmailView.setText(usuario.getEmail());
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        } else if (id == R.id.nav_logout) {
            FirebaseAuth.getInstance().signOut();
            Intent Login = new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(Login);
    }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onCustomClick(Object object) {
        Tarefa tarefa = (Tarefa) object;
        String nome = tarefa.getTitulo();
        String Descricao = tarefa.getDescricao();
        Toast.makeText(TelaPrincipal.this, nome , Toast.LENGTH_SHORT).show();
    }

    public void setaRecyclerView() {

        //Aqui é instanciado o Recyclerview
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_recyclerteste);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        adapter = new RecyclerTesteAdapter(this, tarefasListas, this);
        mRecyclerView.setAdapter(adapter);
    }

    public void setaButtons() {

        floatingActionButton = (FloatingActionButton) findViewById(R.id.add_tarefa);


    }



    public void setatouch() {

        onItemTouchListener = (RecyclerView.OnItemTouchListener) findViewById(R.id.card_view);


    }

    public void listenersButtons() {

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Tarefa tarefanew = new Tarefa();
                tarefanew.setTitulo("Tarefa" + id);
                tarefanew.setDescricao("Descricao" + id);
                tarefanew.setId(String.valueOf(id));
                bd.cadastrarTarefaDiaria(tarefanew);
                bd.retornaTarefaDiaria();
                id++;

                //Adiciona a pessoa1 e avisa o adapter que o conteúdo
                //da lista foi alterado
                tarefasListas.add(tarefanew);
                adapter.notifyDataSetChanged();

            }
        });


    }

    public void listenersSwipeable() {
        SwipeableRecyclerViewTouchListener swipeTouchListener =
                new SwipeableRecyclerViewTouchListener(mRecyclerView,
                        new SwipeableRecyclerViewTouchListener.SwipeListener() {
                            @Override
                            public boolean canSwipeLeft(int position) {
                                return true;
                            }

                            @Override
                            public boolean canSwipeRight(int position) {
                                return true;
                            }

                            @Override
                            public void onDismissedBySwipeLeft(RecyclerView recyclerView, int[] reverseSortedPositions) {

                                for (final int position : reverseSortedPositions) {
                                    Toast.makeText(TelaPrincipal.this, tarefasListas.get(position).getTitulo() + " swiped left", Toast.LENGTH_SHORT).show();
                                    Snackbar snackbar = Snackbar
                                            .make(mRecyclerView, "Desfazer Delete", Snackbar.LENGTH_LONG)
                                            .setAction("UNDO", new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    Snackbar snackbar1 = Snackbar.make(mRecyclerView, "Operacao de delete desfeita", Snackbar.LENGTH_SHORT);
                                                    snackbar1.show();
                                                    tarefasListas.add(del,retorna);
                                                    adapter.notifyDataSetChanged();

                                                }
                                            });
                                    snackbar.show();
                                    retorna=tarefasListas.get(position);
                                    del=position;
                                    //Log.e("Deletar tarefa","id = "+ position + "Tarefa: " + tarefasListas.get(position).getTitulo());
                                    bd.removerTarefaDiaria(tarefasListas.get(position));
                                    tarefasListas.remove(position);
                                    adapter.notifyItemRemoved(position);
                                }
                                adapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onDismissedBySwipeRight(RecyclerView recyclerView, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {
                                    Toast.makeText(TelaPrincipal.this, tarefasListas.get(position).getTitulo() + " swiped right", Toast.LENGTH_SHORT).show();
                                    Snackbar snackbar = Snackbar
                                            .make(mRecyclerView, "Desfazer Delete", Snackbar.LENGTH_LONG)
                                            .setAction("UNDO", new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    Snackbar snackbar1 = Snackbar.make(mRecyclerView, "Operacao de delete desfeita", Snackbar.LENGTH_SHORT);
                                                    snackbar1.show();
                                                    tarefasListas.add(del,retorna);
                                                    adapter.notifyDataSetChanged();

                                                }
                                            });
                                    snackbar.show();
                                    retorna=tarefasListas.get(position);
                                    del=position;
                                    Log.e("Deletar tarefa","id = "+ position + "Tarefa: " + tarefasListas.get(position).getTitulo());
                                    bd.removerTarefaDiaria(tarefasListas.get(position));
                                    tarefasListas.remove(position);
                                    adapter.notifyItemRemoved(position);
                                }
                                adapter.notifyDataSetChanged();
                            }

                        });

        mRecyclerView.addOnItemTouchListener(swipeTouchListener);
    }


}
