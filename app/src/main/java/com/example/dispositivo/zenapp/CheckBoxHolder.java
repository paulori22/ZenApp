package com.example.dispositivo.zenapp;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;

/**
 * Created by wesley on 16/04/2017.
 */

public class CheckBoxHolder extends RecyclerView.ViewHolder{

    private CheckBox checkbox;

    public CheckBoxHolder(View v)
    {
        super(v);
        checkbox = (CheckBox) v.findViewById(R.id.checkBox);
    }

    public CheckBox getCheckbox()
    {
        return checkbox;
    }
}
