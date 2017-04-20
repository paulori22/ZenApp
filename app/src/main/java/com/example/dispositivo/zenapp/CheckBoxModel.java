package com.example.dispositivo.zenapp;

/**
 * Created by wesley on 16/04/2017.
 */

public class CheckBoxModel {

    private String text;
    private boolean isChecked;

    public CheckBoxModel(String text, boolean isChecked)
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
