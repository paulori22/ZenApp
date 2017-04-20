package com.example.dispositivo.zenapp;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wesley on 14/04/2017.
 */

public class CadastrarTarefaAdapter extends RecyclerView.Adapter<ViewHolder> {

    private List<Object> items;
    FragmentManager manager;

    private final int TEXT = 0, BUTTON = 1;

    public CadastrarTarefaAdapter(FragmentManager manager, List<Object> items)
    {
        this.manager = manager;
        this.items = items;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        switch(viewType)
        {
            case TEXT:
                View v1 = inflater.inflate(R.layout.layout_text_holder, viewGroup, false);
                viewHolder = new TextTextHolder(v1);
                break;
            case BUTTON:
                View v2 = inflater.inflate(R.layout.layout_button_holder, viewGroup, false);
                viewHolder = new TextButtonHolder(v2);
                break;
            default:
                View v = inflater.inflate(android.R.layout.simple_list_item_1, viewGroup, false);
                viewHolder = new TextButtonHolder(v);
                break;
        }
        return viewHolder;
    }

    @Override
    public int getItemViewType(int position)
    {
        if(items.get(position) instanceof TextTextModel) return TEXT;
        else return BUTTON;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {


        switch(viewHolder.getItemViewType())
        {
            case TEXT:
                TextTextHolder vh1 = (TextTextHolder) viewHolder;
                configureTextTextHolder(vh1, position);
                break;
            case BUTTON:
                TextButtonHolder vh2 = (TextButtonHolder) viewHolder;
                configureTextButtonHolder(vh2, position);
                break;
            default:
                TextButtonHolder vh = (TextButtonHolder) viewHolder;
                configureTextButtonHolder(vh,position);
                break;
        }

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if(position == 2)
                {
                    LayoutInflater dialoginflater = LayoutInflater.from(v.getContext());
                    final View dialogview = dialoginflater.inflate(R.layout.layout_textdialog,null);

                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setView(dialogview);

                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialog, int id)
                        {
                            TextTextHolder holder = (TextTextHolder) viewHolder;

                            EditText ed = (EditText) dialogview.findViewById(R.id.editText2);
                            TextView t = (TextView) v.findViewById(R.id.text2);

                            configureTextTextHolder(holder,position,ed.getText().toString());

                            t.setText(holder.getLabel2().getText());
                            dialog.dismiss();
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                else if(position == 1)
                {
                    ArrayList<CheckBoxModel> semanalist = new ArrayList<>();
                    semanalist.add(new CheckBoxModel("Todos os Dias",false));
                    semanalist.add(new CheckBoxModel("Domingo",false));
                    semanalist.add(new CheckBoxModel("Segunda",false));
                    semanalist.add(new CheckBoxModel("Terça",false));
                    semanalist.add(new CheckBoxModel("Quarta",false));
                    semanalist.add(new CheckBoxModel("Quinta",false));
                    semanalist.add(new CheckBoxModel("Sexta",false));
                    semanalist.add(new CheckBoxModel("Sábado",false));


                    LayoutInflater dialoginflater = LayoutInflater.from(v.getContext());
                    final View dialogview = dialoginflater.inflate(R.layout.layout_repetirsemana_dialog,null);

                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setView(dialogview);

                    RecyclerView repetirSemana = (RecyclerView) dialogview.findViewById(R.id.repetirSemanaList);

                    repetirSemana.setAdapter(new RepetirSemanalmenteAdapter(semanalist));

                    RecyclerView.LayoutManager layout = new LinearLayoutManager(dialogview.getContext(), LinearLayoutManager.VERTICAL, false);

                    repetirSemana.setLayoutManager(layout);

                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialog, int id)
                        {
                            dialog.dismiss();
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                }

            }
        });

    }

    private void configureTextTextHolder(TextTextHolder vh1, int position) {
        TextTextModel text = (TextTextModel) items.get(position);
        if(text != null)
        {
            vh1.getLabel1().setText(text.getText1());
            vh1.getLabel2().setText(text.getText2());
        }
    }

    private void configureTextTextHolder(TextTextHolder vh1, int position, String a) {
        TextTextModel text = (TextTextModel) items.get(position);
        if(text != null)
        {
            vh1.getLabel1().setText(text.getText1());
            vh1.getLabel2().setText(a);
        }
    }

    private void configureTextButtonHolder(TextButtonHolder vh2, int position) {
        TextButtonModel button = (TextButtonModel) items.get(position);
        if(button != null)
        {
            vh2.getLabel3().setText(button.getText1());
            vh2.getButton().setText(button.getButtontext());
            vh2.getButton2().setText(button.getButtontext2());
            vh2.setManager(manager);
        }
    }


    @Override
    public int getItemCount() {
        return this.items.size();
    }
}
