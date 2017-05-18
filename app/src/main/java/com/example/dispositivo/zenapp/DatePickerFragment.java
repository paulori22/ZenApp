package com.example.dispositivo.zenapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by wesley on 16/04/2017.
 */


public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    public Button b;
    Calendar c = Calendar.getInstance();
    View v;

    public DatePickerFragment(View v)
    {
        this.v = v;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {

        int ano = c.get(Calendar.YEAR);
        int mes = c.get(Calendar.MONTH);
        int dia = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dp = new DatePickerDialog(getActivity(),this,ano,mes,dia);

        dp.getDatePicker().setMinDate(c.getTimeInMillis());

        return dp;
    }

    @Override
    public void onDateSet(DatePicker view, int ano, int mes, int dia) {
        c.set(ano,mes,dia);
        Button b = (Button) v.findViewById(R.id.button1);
        SimpleDateFormat format = new SimpleDateFormat("EEE ',' dd 'de' MMM 'de' yyyy");
        SimpleDateFormat format2 = new SimpleDateFormat("dd'-'mm'-'yyyy");
        String data = format.format(c.getTime());
        String data2 = format2.format(c.getTime());
        b.setText(data2);
    }

}
