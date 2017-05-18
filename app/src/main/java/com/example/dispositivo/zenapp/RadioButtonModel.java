package com.example.dispositivo.zenapp;

/**
 * Created by aluno on 20/04/17.
 */

public class RadioButtonModel {

    private String text;
    private boolean isChecked;

    public RadioButtonModel(String text, boolean isChecked)
    {
        this.text = text;
        this.isChecked = isChecked;
    }

    public String getText()
    {
        return text;
    }

    public boolean getIsChecked()
    {
        return isChecked;
    }
}
