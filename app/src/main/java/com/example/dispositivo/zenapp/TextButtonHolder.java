package com.example.dispositivo.zenapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;


/**
 * Created by wesley on 14/04/2017.
 */

public class TextButtonHolder extends RecyclerView.ViewHolder {

    private Button button;
    private Button button2;
    private TextView label3;
    private FragmentManager manager;


    public TextButtonHolder(View v)
    {
        super(v);

        button = (Button) v.findViewById(R.id.button1);
        button2 = (Button) v.findViewById(R.id.button2);
        label3 = (TextView) v.findViewById(R.id.text3);
        manager = getManager();

        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                DialogFragment datepicker = new DatePickerFragment(v);
                datepicker.show(manager, datepicker.getTag());
            }
        });

        button2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                DialogFragment timepicker = new TimePickerFragment(v);
                timepicker.show(manager, timepicker.getTag());
            }
        });
    }

    public Button getButton()
    {
        return button;
    }

    public Button getButton2()
    {
        return button2;
    }

    public TextView getLabel3()
    {
        return label3;
    }

    public FragmentManager getManager()
    {
        return manager;
    }

    public void setManager(FragmentManager manager)
    {
        this.manager = manager;
    }
}
