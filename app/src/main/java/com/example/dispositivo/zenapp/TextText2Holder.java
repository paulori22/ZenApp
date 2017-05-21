package com.example.dispositivo.zenapp;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Wesley Serra on 21/05/2017.
 */

public class TextText2Holder extends RecyclerView.ViewHolder{

    private TextView label3, label4;

    public TextText2Holder(View v)
    {
        super(v);
        label3 = (TextView) v.findViewById(R.id.text3);
        label4 = (TextView) v.findViewById(R.id.text4);
    }

    public TextView getLabel3()
    {
        return label3;
    }

    public void setLabel3(TextView label3)
    {
        this.label3 = label3;
    }

    public TextView getLabel4()
    {
        return label4;
    }

    public void setLabel4(TextView label4)
    {
        this.label4 = label4;
    }
}
