package com.example.dispositivo.zenapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.brnunes.swipeablerecyclerview.SwipeableRecyclerViewTouchListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig;

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
    private String tela_atual;
    private View mProgressView;
    private View mTarefaForm;
    private int numeroTarefasDiario;
    private int numeroTarefasSemanal;
    private Button button0;
    private Button button1;
    private Button button2;

    private String SHOWCASE_ID = "teste";

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
        dica();

        this.setTitle("Tarefas Diárias");


        usuario = FirebaseAuth.getInstance().getCurrentUser();
        data = FirebaseDatabase.getInstance();
        bd = new Firebase(usuario,data);
        mProgressView = findViewById(R.id.tarefas_progress);
        mTarefaForm = findViewById(R.id.recycler_recyclerteste);

        filtro(tipo_diario);

        tela_atual = tipo_diario;

        contaNumeroTarefasDiaria();
        contaNumeroTarefasSemanal();


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
        Log.e("NUM Tarefas Diario:",String.valueOf(numeroTarefasDiario));
        Log.e("NUM Tarefas Semanal:",String.valueOf(numeroTarefasSemanal));

    }

    @Override
    public void onResume() {
        super.onResume();
        contaNumeroTarefasDiaria();
        contaNumeroTarefasSemanal();
        filtro(tela_atual);
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
        button0 = (Button) findViewById(R.id.buttonmenu);
        button1 = (Button) findViewById(R.id.buttonpontos);
        button2 = (Button) findViewById(R.id.buttonslide);



    }



    public void setatouch() {

        onItemTouchListener = (RecyclerView.OnItemTouchListener) findViewById(R.id.card_view);


    }

    public void listenersButtons() {

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent CadastrarTarefa = new Intent(getApplicationContext(), CadastrarTarefa.class);
                Bundle b = new Bundle();
                b.putStringArray("com.example.dispositivo.zenapp.id_tarefa",new String[]{String.valueOf(tarefasListas.size()),tela_atual});
                CadastrarTarefa.putExtras(b);
                //CadastrarTarefa.putExtra("com.example.dispositivo.zenapp.id_tarefa",String.valueOf(tarefasListas.size()));
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

    public void contaNumeroTarefasDiaria() {

        showProgress(true);

        DatabaseReference myRef = data.getReference(tipo_diario + usuario.getUid());

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                numeroTarefasDiario = (int) dataSnapshot.getChildrenCount();

                showProgress(false);
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });


    }

    public void contaNumeroTarefasSemanal() {

        showProgress(true);

        DatabaseReference myRef = data.getReference(tipo_semanal + usuario.getUid());

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                numeroTarefasSemanal = (int) dataSnapshot.getChildrenCount();

                showProgress(false);
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });


    }


    public void moveTarefa(Tarefa t)
    {
        if(tela_atual.equals(tipo_diario))
        {

            bd.removerTarefaDiaria(t);
            t.setId(String.valueOf(numeroTarefasSemanal));
            bd.cadastrarTarefaSemanal(t);

        }
        else
        {
            bd.removerTarefaSemanal(t);
            t.setId(String.valueOf(numeroTarefasDiario));
            bd.cadastrarTarefaDiaria(t);
        }


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
                                    //Toast.makeText(TelaPrincipal.this, tarefasListas.get(position).getTitulo() + " swiped left", Toast.LENGTH_SHORT).show();
                                    Snackbar snackbar = Snackbar
                                            .make(mRecyclerView, "Desfazer mover tarefa", Snackbar.LENGTH_LONG)
                                            .setAction("Desfazer", new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    Snackbar snackbar1 = Snackbar.make(mRecyclerView, "Operacao de mover a tarefa desfeita", Snackbar.LENGTH_SHORT);
                                                    snackbar1.show();
                                                    tarefasListas.add(del,retorna);
                                                    if(tela_atual.equals(tipo_diario))
                                                    {
                                                        bd.cadastrarTarefaDiaria(retorna);
                                                        retorna.setId(String.valueOf(del));
                                                        bd.removerTarefaSemanal(retorna);
                                                    }

                                                    else
                                                    {
                                                        bd.cadastrarTarefaDiaria(retorna);
                                                        retorna.setId(String.valueOf(del));
                                                        bd.removerTarefaDiaria(retorna);
                                                    }

                                                    filtro(tela_atual);

                                                }
                                            });
                                    snackbar.show();
                                    retorna=tarefasListas.get(position);
                                    del=position;
                                    moveTarefa(retorna);
                                    filtro(tela_atual);
                                }
                                filtro(tela_atual);
                            }

                            @Override
                            public void onDismissedBySwipeRight(RecyclerView recyclerView, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {
                                    //Toast.makeText(TelaPrincipal.this, tarefasListas.get(position).getTitulo() + " swiped right", Toast.LENGTH_SHORT).show();
                                    Snackbar snackbar = Snackbar
                                            .make(mRecyclerView, "Desfazer remover tarefa", Snackbar.LENGTH_LONG)
                                            .setAction("Desfazer", new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    Snackbar snackbar1 = Snackbar.make(mRecyclerView, "Operacao de remover tarefa desfeita", Snackbar.LENGTH_SHORT);
                                                    snackbar1.show();
                                                        tarefasListas.add(del,retorna);
                                                    if(tela_atual.equals(tipo_diario))
                                                        bd.cadastrarTarefaDiaria(retorna);
                                                    else
                                                        bd.cadastrarTarefaSemanal(retorna);

                                                    filtro(tela_atual);

                                                }
                                            });
                                    snackbar.show();
                                    retorna=tarefasListas.get(position);
                                    del=position;
                                    Log.e("Deletar tarefa","id = "+ position + "Tarefa: " + tarefasListas.get(position).getTitulo());
                                    if(tela_atual.equals(tipo_diario))
                                        bd.removerTarefaDiaria(tarefasListas.get(position));
                                    else
                                        bd.removerTarefaSemanal(tarefasListas.get(position));
                                    tarefasListas.remove(position);
                                    filtro(tela_atual);
                                }
                                filtro(tela_atual);
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

    public void dica() {

        ShowcaseConfig config = new ShowcaseConfig();
        config.setDelay(1000); // half second between each showcase view

        MaterialShowcaseSequence sequence = new MaterialShowcaseSequence(this, SHOWCASE_ID);

        sequence.setConfig(config);

        sequence.addSequenceItem(floatingActionButton,
                "Método ZTD:\n  3 tarefas por dia;\n  1 tarefa semanal;\nCumpra Suas metas!\nAqui você começa a usar o aplicativo.\n", "OK!");

        sequence.addSequenceItem(button0,
                "Aqui, separamos suas tarefas.\nAcesse esse menu para se organizar melhor e definir o tipo de tarefa.\n", "OK!");

        sequence.addSequenceItem(button1,
                "Aqui, classificamos suas tarefas.", "OK!");

        sequence.addSequenceItem(button2,
                "Ao cadastrar uma tarefa, ela aparecerá nesta tela.\nDeslize para a esquerda para mudar o tipo de tarefa.\nDeslize para a direita para excluí-la.\nVamos começar?\n", "OK!");

        sequence.start();


    }


}
