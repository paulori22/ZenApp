package com.example.dispositivo.zenapp;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RadioButton;

/**
 * Created by aluno on 20/04/17.
 */

public class RadioButtonHolder extends RecyclerView.ViewHolder {

    private RadioButton radioButton;

    public RadioButtonHolder(View v)
    {
        super(v);
        radioButton = (RadioButton) v.findViewById(R.id.radioButton);
    }

    public RadioButton getRadioButton()
    {
        return radioButton;
    }
}
