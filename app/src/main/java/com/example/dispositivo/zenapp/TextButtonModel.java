package com.example.dispositivo.zenapp;

/**
 * Created by wesley on 14/04/2017.
 */

public class TextButtonModel {

    private String text1, buttontext, buttontext2;

    public TextButtonModel(String text1, String buttontext, String buttontext2)
    {
        this.text1 = text1;
        this.buttontext = buttontext;
        this.buttontext2 = buttontext2;
    }

    public String getText1()
    {
        return text1;
    }

    public String getButtontext()
    {
        return buttontext;
    }

    public String getButtontext2()
    {
        return buttontext2;
    }
}
