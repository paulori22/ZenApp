package com.example.dispositivo.zenapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Lucius-PC on 08/04/2017.
 */

public class RecyclerTesteAdapter extends RecyclerView.Adapter<RecyclerTesteAdapter.RecyclerTesteViewHolder> {

    public static ClickRecyclerView_Interface clickRecyclerViewInterface;
    Context mctx;
    private List<Tarefa> mList;
    private String idtaref = "";
    private String tipotarefa;


    public RecyclerTesteAdapter(Context ctx, List<Tarefa> list, ClickRecyclerView_Interface clickRecyclerViewInterface) {
        this.mctx = ctx;
        this.mList = list;
        this.clickRecyclerViewInterface = clickRecyclerViewInterface;
    }

    @Override
    public RecyclerTesteViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.itemlist_itemdalista, viewGroup, false);
        return new RecyclerTesteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerTesteViewHolder viewHolder, int i) {
        Tarefa tarefa = mList.get(i);

        viewHolder.nometarefa.setText(tarefa.getTitulo());
        viewHolder.desctarefa.setText(tarefa.getDescricao());
        //viewHolder.timertarefa.setText(tarefa.get);
        viewHolder.tagtarefa.setText(tarefa.getTag());
        viewHolder.idtarefa = tarefa.getId();
        idtaref = tarefa.getId();
        tipotarefa = tarefa.getTipo();


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    protected class RecyclerTesteViewHolder extends RecyclerView.ViewHolder {

        protected TextView nometarefa;
        protected TextView desctarefa;
        protected TextView tagtarefa;
        protected TextView timertarefa;
        protected String idtarefa;

        public RecyclerTesteViewHolder(final View itemView) {
            super(itemView);

            nometarefa = (TextView) itemView.findViewById(R.id.itemnometarefa);
            tagtarefa = (TextView) itemView.findViewById(R.id.itemtagtarefa);
            //timertarefa = (TextView) itemView.findViewById(R.id.itemtimertarefa);
            desctarefa = (TextView) itemView.findViewById(R.id.itemdesctarefa);

            ImageButton editTarefaButton = (ImageButton) itemView.findViewById(R.id.imageButton_edittarefa);
            ImageButton detalheTarefaButton = (ImageButton) itemView.findViewById(R.id.imageButton_detalhetarefa);

            editTarefaButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent EditTarefa = new Intent(mctx.getApplicationContext(), EditarTarefa.class);
                    Bundle b = new Bundle();
                    b.putStringArray("tarefainfo",new String[]{nometarefa.getText().toString(),tagtarefa.getText().toString(),desctarefa.getText().toString(),idtaref,tipotarefa});
                    EditTarefa.putExtras(b);
                    //EditTarefa.putExtra("com.example.dispositivo.zenapp.id_tarefa",String.valueOf(mList.size()));
                    mctx.startActivity(EditTarefa);
                }
            });

            detalheTarefaButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    LayoutInflater dialoginflater = LayoutInflater.from(v.getContext());
                    final View dialogview = dialoginflater.inflate(R.layout.layout_detalhesdialog,null);

                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setView(dialogview);

                    TextView nometarefadetail = (TextView) dialogview.findViewById(R.id.textNomeTarefaDetalhe);
                    TextView tagdetail = (TextView) dialogview.findViewById(R.id.textTagDetalhe);
                    TextView descdetail = (TextView)dialogview.findViewById(R.id.textDescDetalhe);

                    nometarefadetail.setText(nometarefa.getText());
                    tagdetail.setText(tagtarefa.getText());
                    descdetail.setText(desctarefa.getText());

                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialog, int id)
                        {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });

            //Setup the click listener
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    clickRecyclerViewInterface.onCustomClick(mList.get(getLayoutPosition()));


                }
            });

        }
    }
}


