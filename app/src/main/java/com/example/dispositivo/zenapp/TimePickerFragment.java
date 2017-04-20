package com.example.dispositivo.zenapp;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by wesley on 16/04/2017.
 */

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    public Button b;
    Calendar c = Calendar.getInstance();
    View v;

    public TimePickerFragment(View v)
    {
        this.v = v;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        int hora = c.get(Calendar.HOUR_OF_DAY);
        int min = c.get(Calendar.MINUTE);

        return new TimePickerDialog(getActivity(),this,hora,min,true);
    }

    @Override
    public void onTimeSet(TimePicker view, int hora, int min)
    {
        b = (Button) v.findViewById(R.id.button2);
        b.setText(hora + ":" + min);
    }
}
