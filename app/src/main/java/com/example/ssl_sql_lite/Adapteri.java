package com.example.ssl_sql_lite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Adapteri extends ArrayAdapter<Taulu> {

    private Context context;
    ArrayList<Taulu> dataset;
    private Button poista;
    Taulu taulu;

    public Adapteri(@NonNull Context context, int resource, @NonNull List<Taulu> objects) {
        super(context, resource, objects);

        this.context = context;
        this.dataset = (ArrayList<Taulu>)objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            v = layoutInflater.inflate(R.layout.adapteri,parent,false);
            LinearLayout linearLayout = (LinearLayout) v;

            TextView lista = v.findViewById(R.id.textView);
            TextView lista2 = v.findViewById(R.id.textView2);
            TextView lista3 = v.findViewById(R.id.textView3);

            //lista.setText(dataset.get(position).getTitle());
            //lista2.setText(dataset.get(position).getBody());

/*
            String tmp="";
            Integer iTmp =0;
            iTmp=dataset.get(position).id;
            tmp= Integer.toString(iTmp);

            lista.setText(tmp);

 */
            lista.setText(Integer.toString(dataset.get(position).id)+":  " );
            lista2.setText(dataset.get(position).teksti);
            lista3.setText(dataset.get(position).pvm);

        }
        return v;
    }



}
