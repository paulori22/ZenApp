package com.example.dispositivo.zenapp;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;

/**
 * Created by wesley on 16/04/2017.
 */

public class RepetirSemanalmenteAdapter extends RecyclerView.Adapter<ViewHolder>
{
    private List<CheckBoxModel> items;

    public RepetirSemanalmenteAdapter(List<CheckBoxModel> items)
    {
        this.items = items;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType)
    {
        ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        View v = inflater.inflate(R.layout.layout_checkbox_holder, viewGroup, false);
        viewHolder = new CheckBoxHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position)
    {
        CheckBoxHolder cb = (CheckBoxHolder) viewHolder;
        configureCheckBoxHolder(cb,position);
    }

    public void configureCheckBoxHolder(CheckBoxHolder ch, int position)
    {
        CheckBoxModel chmodel = items.get(position);
        if(chmodel != null)
        {
            ch.getCheckbox().setText(chmodel.getText());
            ch.getCheckbox().setChecked(chmodel.getIsChecked());
        }
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }
}
