package com.example.dispositivo.zenapp;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;
import java.util.Objects;

/**
 * Created by wesley on 16/04/2017.
 */

public class TextListAdapter extends RecyclerView.Adapter<ViewHolder>
{
    private List<Object> items;
    private final int RADIOBUTTON = 0;

    public TextListAdapter(List<Object> items)
    {
        this.items = items;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType)
    {
        ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        switch(viewType)
        {
            case RADIOBUTTON:
                View v1 = inflater.inflate(R.layout.layout_buttonradio_holder, viewGroup, false);
                viewHolder = new RadioButtonHolder(v1);
                break;
            default:
                View v = inflater.inflate(R.layout.layout_buttonradio_holder, viewGroup, false);
                viewHolder = new RadioButtonHolder(v);
                break;
        }
        return viewHolder;
    }

    @Override
    public int getItemViewType(int position)
    {
        if(items.get(position) instanceof RadioButtonModel) return RADIOBUTTON;
        return -1;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position)
    {
        switch(viewHolder.getItemViewType())
        {
            case RADIOBUTTON:
                RadioButtonHolder rbh = (RadioButtonHolder) viewHolder;
                configureRadioButtonHolder(rbh,position);
                break;
            default:
                RadioButtonHolder rbhdefault = (RadioButtonHolder) viewHolder;
                configureRadioButtonHolder(rbhdefault,position);
                break;
        }
    }

    public void configureRadioButtonHolder(RadioButtonHolder rb, int position)
    {
        RadioButtonModel rbmodel = (RadioButtonModel) items.get(position);
        if(rbmodel != null)
        {
            rb.getRadioButton().setText(rbmodel.getText());
            rb.getRadioButton().setChecked(rbmodel.getIsChecked());
        }
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }
}
