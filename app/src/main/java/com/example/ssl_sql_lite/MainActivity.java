package com.example.ssl_sql_lite;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

//Room:ia varten piti laittaa GRADLE:eenmjuutama rivi tänne:
// SSL_SQL_Lite\app\build.gradle
public class MainActivity extends AppCompatActivity {
// Jos tänne laittaa
 TauluDao tauluDao;
//  niin ei tarvitse initialisoida, paitsi että myöhemmin pitää


    private Button save;
    private EditText editText;
    private ListView listView;
    private TextView textViewTulos;
    private String str;
    private ArrayList<String> dataBaseArrayList;
    private Adapteri adapteri;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //UI-komponentit kyytiin:
        this.editText=findViewById(R.id.editText);
        this.listView=findViewById(R.id.listView);
        this.save=findViewById(R.id.save);
        this.textViewTulos=findViewById((R.id.textViewTulos));

        Tietokanta tietokanta=
                Room.databaseBuilder(getApplicationContext(),Tietokanta.class,Tietokanta.NIMI).allowMainThreadQueries().build();
        //kaatui, virhe näkyi debuggerikkunassa, oletusarvoisesti ei toimi pääsäikeessä tuo, niin piti lisätä allowMaindthread...


        //Taulu t=new Taulu();
        //t.teksti="KISSA";
        //TauluDao tauluDao = tietokanta.tauluDao(); //Tässä skoupiossa jos se on, niin pitää initialisoida
        //tauluDao.InsertTaulu(t);
        this.tauluDao=tietokanta.tauluDao();
        tauluDao.getAllInDescenfingOrder();
        str= "";
        this.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str = editText.getText().toString(); //tähän ei saanut laittaa this eteen tuolle editTekstille!
                Taulu t=new Taulu();
                //t.teksti="KOIRA";
                t.teksti=str;
                //t.pvm="12.2.2020";
                Calendar calendar = new GregorianCalendar();
                t.pvm = calendar.getTime().toString();
                tauluDao.InsertTaulu(t); //Kaatu null objektiin, taulu-dao on nulli.
                ShowResults();
            }

        });

        ShowResults();
    }

public void ShowResults(){
    List<Taulu> tResults=tauluDao.getAllInDescenfingOrder();
    adapteri = new Adapteri(this,R.layout.adapteri, tResults); //Mitähän tuohon viimeseen piti laittaa
    listView.setAdapter(adapteri);
    adapteri.addAll(tResults);
    Integer i = 0;
    String text ="";
    while (i< tResults.size()){

        text += tResults.get(i).id +" ";
        text += tResults.get(i).teksti +" ";
        text += tResults.get(i).pvm;
        text += "\n";
        i++;

    }
    textViewTulos.setText(text);
}

}



