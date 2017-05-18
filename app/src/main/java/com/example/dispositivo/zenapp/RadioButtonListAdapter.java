package com.example.dispositivo.zenapp;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import java.util.List;

/**
 * Created by aluno on 04/05/17.
 */

public class RadioButtonListAdapter extends RecyclerView.Adapter<ViewHolder>
{

    private List<Object> items;
    private RadioButton lastChecked = null;
    private RadioButton currentChecked = null;
    private int lastCheckedPos = 0;

    public RadioButtonListAdapter(List<Object> items)
    {
        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View v1 = inflater.inflate(R.layout.layout_buttonradio_holder, viewGroup, false);
        return new RadioButtonHolder(v1);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        RadioButtonHolder vh1 = (RadioButtonHolder) viewHolder;
        configureRadioButtonHolder(vh1, position);

        if(position == 0 && vh1.getRadioButton().isChecked())
        {
            lastChecked = vh1.getRadioButton();
            lastCheckedPos = 0;
        }

        vh1.getRadioButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                currentChecked = (RadioButton) v.findViewById(R.id.radioButton);
                if(currentChecked.isChecked())
                {
                    if(lastChecked != null)
                    {
                        if(position != lastCheckedPos)
                        lastChecked.setChecked(false);
                    }
                    lastChecked = currentChecked;
                    lastCheckedPos = position;
                }
                //else lastChecked = null;
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    private void configureRadioButtonHolder(RadioButtonHolder vh1, int position)
    {
        RadioButtonModel rb = (RadioButtonModel) items.get(position);
        if(rb != null)
        {
            vh1.getRadioButton().setText(rb.getText());
            vh1.getRadioButton().setChecked(rb.getIsChecked());
        }
    }

    public int getRadioButtonPosition()
    {
        return lastCheckedPos;
    }
}
