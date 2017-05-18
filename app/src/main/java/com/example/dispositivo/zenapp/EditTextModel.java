package com.example.dispositivo.zenapp;

/**
 * Created by aluno on 27/04/17.
 */

public class EditTextModel
{
    private String text,hint;

    public EditTextModel(String text, String hint)
    {
        this.text = text;
        this.hint = hint;
    }

    public String getText()
    {
        return text;
    }

    public String getHint()
    {
        return hint;
    }
}
