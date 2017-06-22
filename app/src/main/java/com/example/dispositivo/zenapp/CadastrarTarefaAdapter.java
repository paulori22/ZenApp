package com.example.dispositivo.zenapp;

import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CadastrarTarefaAdapter extends RecyclerView.Adapter<ViewHolder> {

    private List<Object> items;
    private String nomeTarefa = "";
    private String tagtype = "Desativado";
    private String desc = "";
    //private String tarefatype = "Diário";

    private final int TEXT = 0, TEXT2 = 1;

    public CadastrarTarefaAdapter(List<Object> items)
    {
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
            case TEXT2:
                View v2 = inflater.inflate(R.layout.layout_text2_holder, viewGroup, false);
                viewHolder = new TextText2Holder(v2);
                break;
            default:
                View v = inflater.inflate(android.R.layout.simple_list_item_1, viewGroup, false);
                viewHolder = new TextTextHolder(v);
                break;
        }
        return viewHolder;
    }

    @Override
    public int getItemViewType(int position)
    {
        if(items.get(position) instanceof TextTextModel) return TEXT;
        else return TEXT2;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {


        switch(viewHolder.getItemViewType())
        {
            case TEXT:
                TextTextHolder vh1 = (TextTextHolder) viewHolder;
                configureTextTextHolder(vh1, position);
                break;
            case TEXT2:
                TextText2Holder vh2 = (TextText2Holder) viewHolder;
                configureTextText2Holder(vh2, position);
                break;
            default:
                TextTextHolder vh = (TextTextHolder) viewHolder;
                configureTextTextHolder(vh, position);
                break;
        }


        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if(viewHolder.getAdapterPosition() == 0)
                {
                    LayoutInflater dialoginflater = LayoutInflater.from(v.getContext());
                    final View dialogview = dialoginflater.inflate(R.layout.layout_textdialog,null);

                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setView(dialogview);
                    EditText ed = (EditText) dialogview.findViewById(R.id.editTextDialog);
                    TextView nameDialog = (TextView) dialogview.findViewById(R.id.textDialog);
                    nameDialog.setText("Nome da Tarefa");
                    nameDialog.setTextColor(Color.BLACK);
                    ed.setText("");
                    ed.setHint("Nome");

                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int id)
                        {
                            EditText ed = (EditText) dialogview.findViewById(R.id.editTextDialog);
                            TextView t = (TextView) v.findViewById(R.id.text2);
                            TextTextHolder holder = (TextTextHolder) viewHolder;
                            if(ed.getText().toString().matches(""))
                            {
                                configureTextTextHolder(holder, viewHolder.getAdapterPosition(), "");
                                nomeTarefa = "";
                            }
                            else
                            {
                                configureTextTextHolder(holder, viewHolder.getAdapterPosition(), ed.getText().toString());
                                nomeTarefa = ed.getText().toString();

                            }
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
                else if(viewHolder.getAdapterPosition() == 1)
                {
                    final ArrayList<Object> tagList = new ArrayList<>();
                    tagList.add(new RadioButtonModel("Desativado",true));
                    tagList.add(new RadioButtonModel("Trabalho", false));
                    tagList.add(new RadioButtonModel("Estudo", false));
                    tagList.add(new RadioButtonModel("Fora", false));
                    tagList.add(new RadioButtonModel("Delegado", false));

                    LayoutInflater dialoginflater = LayoutInflater.from(v.getContext());
                    final View dialogview = dialoginflater.inflate(R.layout.layout_text_list_dialog,null);

                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setView(dialogview);

                    TextView nameDialog = (TextView) dialogview.findViewById(R.id.textlistText);
                    nameDialog.setText("Tag");
                    nameDialog.setTextColor(Color.BLACK);

                    final RecyclerView tag = (RecyclerView) dialogview.findViewById(R.id.textlistList);

                    tag.setAdapter(new RadioButtonListAdapter(tagList));

                    RecyclerView.LayoutManager layout = new LinearLayoutManager(dialogview.getContext(), LinearLayoutManager.VERTICAL, false);

                    tag.setLayoutManager(layout);

                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialog, int id)
                        {
                            int pos = ((RadioButtonListAdapter) tag.getAdapter()).getRadioButtonPosition();
                            RadioButtonModel rbm = (RadioButtonModel) tagList.get(pos);
                            TextView t = (TextView) v.findViewById(R.id.text2);
                            TextTextHolder holder = (TextTextHolder) viewHolder;
                            configureTextTextHolder(holder,viewHolder.getAdapterPosition(),rbm.getText());
                            t.setText(holder.getLabel2().getText());
                            tagtype = rbm.getText();
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
                /*else if(viewHolder.getAdapterPosition() == 2)
                {
                    final ArrayList<Object> tarefaTypeList = new ArrayList<>();
                    tarefaTypeList.add(new RadioButtonModel("Diário", true));
                    tarefaTypeList.add(new RadioButtonModel("Semanal", false));

                    LayoutInflater dialoginflater = LayoutInflater.from(v.getContext());
                    final View dialogview = dialoginflater.inflate(R.layout.layout_text_list_dialog,null);

                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setView(dialogview);

                    TextView nameDialog = (TextView) dialogview.findViewById(R.id.textlistText);
                    nameDialog.setText("Tipo da tarefa");
                    nameDialog.setTextColor(Color.BLACK);

                    final RecyclerView tarefaType = (RecyclerView) dialogview.findViewById(R.id.textlistList);

                    tarefaType.setAdapter(new RadioButtonListAdapter(tarefaTypeList));

                    RecyclerView.LayoutManager layout = new LinearLayoutManager(dialogview.getContext(), LinearLayoutManager.VERTICAL, false);

                    tarefaType.setLayoutManager(layout);

                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialog, int id)
                        {
                            int pos = ((RadioButtonListAdapter) tarefaType.getAdapter()).getRadioButtonPosition();
                            RadioButtonModel rbm = (RadioButtonModel) tarefaTypeList.get(pos);
                            TextView t = (TextView) v.findViewById(R.id.text2);
                            TextTextHolder holder = (TextTextHolder) viewHolder;
                            configureTextTextHolder(holder,viewHolder.getAdapterPosition(),rbm.getText());
                            t.setText(holder.getLabel2().getText());
                            tarefatype = rbm.getText();
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
                }*/
                else if(viewHolder.getAdapterPosition() == 3)
                {
                    LayoutInflater dialoginflater = LayoutInflater.from(v.getContext());
                    final View dialogview = dialoginflater.inflate(R.layout.layout_textdialog,null);

                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setView(dialogview);
                    EditText ed = (EditText) dialogview.findViewById(R.id.editTextDialog);
                    TextView nameDialog = (TextView) dialogview.findViewById(R.id.textDialog);
                    nameDialog.setText("Descrição");
                    nameDialog.setTextColor(Color.BLACK);
                    ed.setText("");
                    ed.setHint("Descrição");

                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int id)
                        {
                            EditText ed = (EditText) dialogview.findViewById(R.id.editTextDialog);
                            TextView t = (TextView) v.findViewById(R.id.text4);
                            TextText2Holder holder = (TextText2Holder) viewHolder;

                            if(ed.getText().toString().equals(""))
                            {
                                configureTextText2Holder(holder,viewHolder.getAdapterPosition(),"Desativado");
                                desc = "Desativado";
                            }
                            else {
                                configureTextText2Holder(holder, viewHolder.getAdapterPosition(), ed.getText().toString());
                                desc = ed.getText().toString();
                            }
                            t.setText(desc);
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

    private void configureTextText2Holder(TextText2Holder vh2, int position) {
        TextText2Model text = (TextText2Model) items.get(position);
        if(text != null)
        {
            vh2.getLabel3().setText(text.getText3());
            vh2.getLabel4().setText(text.getText4());
        }
    }

    private void configureTextText2Holder(TextText2Holder vh2, int position, String a) {
        TextText2Model text = (TextText2Model) items.get(position);
        if(text != null)
        {
            vh2.getLabel3().setText(text.getText3());
            vh2.getLabel4().setText(a);
        }
    }

    public String getNomeTarefa()
    {
        return nomeTarefa;
    }
    public String getDesc()
    {
        return desc;
    }
    /*public String getTarefaType()
    {
        return tarefatype;
    }*/

    public String getTag()
    {
        return tagtype;
    }


    @Override
    public int getItemCount() {
        return this.items.size();
    }
}
