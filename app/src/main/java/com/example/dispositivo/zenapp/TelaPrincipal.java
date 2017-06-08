package com.example.dispositivo.zenapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
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
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TelaPrincipal extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,ClickRecyclerView_Interface {

    int del = 0;
    Tarefa retorna;
    private FirebaseUser usuario;
    private FirebaseDatabase data;
    private Firebase bd;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    RecyclerTesteAdapter adapter;
    private List<Tarefa> tarefasListas = new ArrayList<Tarefa>();
    private FloatingActionButton floatingActionButton;
    private RecyclerView.OnItemTouchListener onItemTouchListener;
    private final String tipo_diario = "TDIARIA/";
    private final String tipo_semanal = "TSEMANAL/";
    private String tela_atual= "TDIARIA/";
    private View mProgressView;
    private View mTarefaForm;

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

        this.setTitle("Tarefas Diárias");


        usuario = FirebaseAuth.getInstance().getCurrentUser();
        data = FirebaseDatabase.getInstance();
        bd = new Firebase(usuario,data);
        mProgressView = findViewById(R.id.tarefas_progress);
        mTarefaForm = findViewById(R.id.recycler_recyclerteste);

        showProgress(true);

        filtro(tipo_diario);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
        if (id == R.id.fil_trabalho) {
            filtro(tela_atual,"Trabalho");
            return true;
        }
        else if(id == R.id.fil_estudo){
            filtro(tela_atual,"Estudo");
            return true;
        }
        else if(id == R.id.fil_fora){
            filtro(tela_atual,"Fora");
            return true;
        }
        else if(id == R.id.fil_delegado){
            filtro(tela_atual,"Delegado");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_diarias) {
            this.setTitle("Tarefas Diárias");
            tela_atual=tipo_diario;
            filtro(tipo_diario);
        } else if (id == R.id.nav_semanais) {
            this.setTitle("Tarefas Semanais");
            tela_atual=tipo_semanal;
            filtro(tipo_semanal);
        }else if (id == R.id.nav_logout) {
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

                Intent CadastrarTarefa = new Intent(getApplicationContext(), CadastrarTarefa.class);
                CadastrarTarefa.putExtra("com.example.dispositivo.zenapp.id_tarefa",String.valueOf(tarefasListas.size()));
                startActivity(CadastrarTarefa);

            }
        });


    }

    public void filtro(String type, final String tag) {

        showProgress(true);

        DatabaseReference myRef = data.getReference(type + usuario.getUid());

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tarefasListas.clear();

                for (DataSnapshot datachild : dataSnapshot.getChildren()) {
                    Tarefa novo = datachild.getValue(Tarefa.class);
                    if(novo.getTag()==tag)
                        tarefasListas.add(novo);

                }
                adapter.notifyDataSetChanged();
                showProgress(false);
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });


    }

    public void filtro(String type) {

        showProgress(true);

        DatabaseReference myRef = data.getReference(type + usuario.getUid());

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tarefasListas.clear();
                for (DataSnapshot datachild : dataSnapshot.getChildren()) {
                    Tarefa novo = datachild.getValue(Tarefa.class);
                    tarefasListas.add(novo);


                }
                adapter.notifyDataSetChanged();
                showProgress(false);
            }

            @Override
            public void onCancelled(DatabaseError error) {

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
                                            .make(mRecyclerView, "Desfazer remover tarefa", Snackbar.LENGTH_LONG)
                                            .setAction("UNDO", new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    Snackbar snackbar1 = Snackbar.make(mRecyclerView, "Operacao de remover tarefa desfeita", Snackbar.LENGTH_SHORT);
                                                    snackbar1.show();
                                                    tarefasListas.add(del,retorna);
                                                    bd.cadastrarTarefaDiaria(retorna);
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
                                            .make(mRecyclerView, "Desfazer remover tarefa", Snackbar.LENGTH_LONG)
                                            .setAction("UNDO", new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    Snackbar snackbar1 = Snackbar.make(mRecyclerView, "Operacao de remover tarefa desfeita", Snackbar.LENGTH_SHORT);
                                                    snackbar1.show();
                                                    tarefasListas.add(del,retorna);
                                                    bd.cadastrarTarefaDiaria(retorna);
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

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mTarefaForm.setVisibility(show ? View.GONE : View.VISIBLE);
            mTarefaForm.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mTarefaForm.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mTarefaForm.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }


}
