package com.example.dispositivo.zenapp;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

/**
 * Created by aluno on 27/04/17.
 */

public class EditTextHolder extends RecyclerView.ViewHolder
{
    private EditText editText;

    public EditTextHolder(View v) {

        super(v);
        editText = (EditText) v.findViewById(R.id.editText);
    }

    public EditText getEditText()
    {
        return editText;
    }
}
