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
import android.widget.Toast;

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

        /*   http://tutorials.jenkov.com/java-date-time/java-util-calendar.html
        Calendar calendar = new GregorianCalendar();
            int year       = calendar.get(Calendar.YEAR);
            int month      = calendar.get(Calendar.MONTH);
            int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH); // Jan = 0, not 1
            int dayOfWeek  = calendar.get(Calendar.DAY_OF_WEEK);
            int weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);
            int weekOfMonth= calendar.get(Calendar.WEEK_OF_MONTH);

            int hour       = calendar.get(Calendar.HOUR);        // 12 hour clock
            int hourOfDay  = calendar.get(Calendar.HOUR_OF_DAY); // 24 hour clock
            int minute     = calendar.get(Calendar.MINUTE);
            int second     = calendar.get(Calendar.SECOND);
            int millisecond= calendar.get(Calendar.MILLISECOND);
        */


        this.tauluDao=tietokanta.tauluDao(); //Täällä pitää alustaa, ettei ole null myöhemmin ->
        tauluDao.getAllInDescenfingOrder();
        str= "";
        this.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str = editText.getText().toString(); //tähän ei saanut laittaa this eteen tuolle editTekstille!
                if (!str.isEmpty()) {
                    Taulu t = new Taulu();
                    t.teksti = str;
                    Calendar calendar = new GregorianCalendar();
                    //t.pvm = calendar.getTime().toString(); //tällä tulee turhan pitkä litania

                    int year       = calendar.get(Calendar.YEAR);
                    int month      = calendar.get(Calendar.MONTH) +1; //HUOM! java aloittaa kk  numeroinnin 0:sta
                    int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                    int hourOfDay  = calendar.get(Calendar.HOUR_OF_DAY); // 24 hour clock
                    int minute     = calendar.get(Calendar.MINUTE);

                    String strYear = Integer.toString(year);
                    String strMonth = Integer.toString(month);
                    if (strMonth.length()==1) strMonth ="0"+strMonth; //0 eteen, jos 1-numeroinen kk
                    String strDayOfMonth = Integer.toString(dayOfMonth);
                    if (strDayOfMonth.length()==1) strDayOfMonth ="0"+strDayOfMonth; //0 eteen, jos 1-numeroinen pv
                    //t.pvm = Integer.toString(dayOfMonth) + "." + Integer.toString(month)+ "." + Integer.toString(year);
                    String strHourOfDay = Integer.toString(hourOfDay);
                    if (strHourOfDay.length()==1) strHourOfDay ="0"+strHourOfDay; //0 eteen, jos 1-numeroinen kk
                    String strMinute =Integer.toString(minute);
                    if (strMinute.length()==1) strMinute ="0"+strMinute; //0 eteen, jos 1-numeroinen kk

                    String klo = strHourOfDay +":" + strMinute;

                    t.pvm = strYear + "-" + strMonth +"-" + strDayOfMonth + " (" + klo + ")";
                    tauluDao.InsertTaulu(t); //Kaatui null objektiin, taulu-dao oli nulli.
                    editText.setText("");
                }
                else {
                    Toast.makeText(MainActivity.this, "Tyhjää tekstiä ei tallenneta", Toast.LENGTH_SHORT).show();
                }
                ShowResults();
            }

        });


        ShowResults();
    }

public void ShowResults(){
    List<Taulu> tResults=tauluDao.getAllInDescenfingOrder();
    adapteri = new Adapteri(this,R.layout.adapteri, tResults); //Mitähän tuohon viimeseen piti laittaa
    listView.setAdapter(adapteri);
    //adapteri.clear(); //Lähtiskö tällä duplikaatit pois, no ei, kun ei näkynyt enää mitään
    //adapteri.addAll(tResults); //Eikun tämä olikin ylimääräinen, kun jo haettiin kaikki tuonne...jotenkin...
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
    textViewTulos.setVisibility(View.INVISIBLE);//hävitetään tekstiboksi näkyvistä
}

}



