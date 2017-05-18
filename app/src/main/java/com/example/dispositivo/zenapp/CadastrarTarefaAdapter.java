package com.example.dispositivo.zenapp;

import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wesley on 14/04/2017.
 */

public class CadastrarTarefaAdapter extends RecyclerView.Adapter<ViewHolder> {

    private List<Object> items;
    FragmentManager manager;
    String nomeTarefa = "";
    String tagtype = "";
    String desc = "";
    int timernum = 0;

    private final int TEXT = 0, BUTTON = 1, EDITTEXT = 2;

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
            case EDITTEXT:
                View v3 = inflater.inflate(R.layout.layout_edittext_holder, viewGroup, false);
                viewHolder = new EditTextHolder(v3);
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
        else if(items.get(position) instanceof EditTextModel) return EDITTEXT;
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
            case EDITTEXT:
                EditTextHolder vh3 = (EditTextHolder) viewHolder;
                configureEditTextHolder(vh3,position);
                break;
            default:
                TextButtonHolder vh = (TextButtonHolder) viewHolder;
                configureTextButtonHolder(vh,position);
                break;
        }



        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if(position == 0)
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
                            configureTextTextHolder(holder, position, ed.getText().toString());
                            nomeTarefa = ed.getText().toString();
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
                            configureTextTextHolder(holder,position,rbm.getText().toString());
                            t.setText(holder.getLabel2().getText());
                            tagtype = rbm.getText().toString();
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
                else if(position == 2)
                {
                    final ArrayList<Object> timerList = new ArrayList<>();
                    timerList.add(new RadioButtonModel("Desativado", true));
                    timerList.add(new RadioButtonModel("15 Minutos", false));
                    timerList.add(new RadioButtonModel("30 Minutos", false));
                    timerList.add(new RadioButtonModel("45 Minutos", false));
                    timerList.add(new RadioButtonModel("60 Minutos", false));
                    timerList.add(new RadioButtonModel("90 Minutos", false));
                    timerList.add(new RadioButtonModel("120 Minutos", false));


                    LayoutInflater dialoginflater = LayoutInflater.from(v.getContext());
                    final View dialogview = dialoginflater.inflate(R.layout.layout_text_list_dialog,null);


                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setView(dialogview);

                    TextView nameDialog = (TextView) dialogview.findViewById(R.id.textlistText);
                    nameDialog.setText("Timer");
                    nameDialog.setTextColor(Color.BLACK);

                    final RecyclerView timer = (RecyclerView) dialogview.findViewById(R.id.textlistList);

                    timer.setAdapter(new RadioButtonListAdapter(timerList));

                    RecyclerView.LayoutManager layout = new LinearLayoutManager(dialogview.getContext(), LinearLayoutManager.VERTICAL, false);

                    timer.setLayoutManager(layout);

                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialog, int id)
                        {
                            int pos = ((RadioButtonListAdapter) timer.getAdapter()).getRadioButtonPosition();
                            RadioButtonModel rbm = (RadioButtonModel) timerList.get(pos);
                            TextView t = (TextView) v.findViewById(R.id.text2);
                            TextTextHolder holder = (TextTextHolder) viewHolder;
                            configureTextTextHolder(holder,position,rbm.getText().toString());
                            t.setText(holder.getLabel2().getText());
                            getTimerIntFromRadioButton(rbm);
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
                else if(position == 3)
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
                            TextView t = (TextView) v.findViewById(R.id.text2);
                            TextTextHolder holder = (TextTextHolder) viewHolder;

                            configureTextTextHolder(holder,position,ed.getText().toString());
                            desc = ed.getText().toString();
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

    private void configureEditTextHolder(EditTextHolder vh3, int position)
    {
        EditTextModel editText = (EditTextModel) items.get(position);
        if(editText != null)
        {
            vh3.getEditText().setText(editText.getText());
            vh3.getEditText().setHint(editText.getHint());
        }

    }

    private void configureEditTextHolder(EditTextHolder vh3, int position, String s)
    {
        EditTextModel editText = (EditTextModel) items.get(position);
        if(editText != null)
        {
            vh3.getEditText().setText(s);
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

    private void getTimerIntFromRadioButton(RadioButtonModel rbm) {
        String s = rbm.getText().toString();

        if(s == "Desativado") timernum = 0;
        else
        {
            s = s.replace(" Minutos","");
            timernum = Integer.parseInt(s);
        }

    }

    public int getTimer()
    {
        return timernum;
    }

    public String getTag()
    {
        return tagtype;
    }


    @Override
    public int getItemCount() {
        return this.items.size();
    }
}
