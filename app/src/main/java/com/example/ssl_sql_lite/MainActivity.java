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
    private String str;
    private ArrayList<String> dataBaseArrayList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //UI-komponentit kyytiin:
        this.editText=findViewById(R.id.editText);
        this.listView=findViewById(R.id.listView);
        this.save=findViewById(R.id.save);

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

            }
        });

        //Taulu tResult=new Taulu();
        //ArrayAdapter<String> itemsAdapter =new ArrayAdapter<String>
                //new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);



        List<Taulu> tResults=tauluDao.getAllInDescenfingOrder();
/*
        //TextView otsikko = findViewById(R.id.otsikko);
        //TextView teksti = findViewById(R.id.textiosa);
        Context c = null;
        LayoutInflater layoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = layoutInflater.inflate(R.layout.activity_main,R.layout.activity_main,false);
        TextView otsikko = (TextView) row.findViewById(R.id.otsikko);
        TextView teksti = (TextView) row.findViewById(R.id.textiosa);
        SingleRow tmp = list.get(position);
        otsikko.setText(tmp.Otsikko);
        teksti.setText(tmp.Teksti);

*/


        Integer i = 0;
        String text ="";
        while (i< tResults.size()){

            text += tResults.get(i).id;
            text += tResults.get(i).teksti;
            text += tResults.get(i).pvm;
            i++;


        }




    }


}
/*

class SingleRow {
    String Otsikko;
    String Teksti;

    SingleRow(String otsikko, String teksti) {
        this.Teksti = teksti;
        this.Otsikko = otsikko;
    }
}



class CustomAdapter extends BaseAdapter {

    ArrayList<SingleRow> list;
    Context c;

    CustomAdapter(Context context) {
        c = context;
        list = new ArrayList<SingleRow>();
        list.add(new SingleRow("Testiotsikko","TestiTeksti")); //TESTATAAN ENSN KIINTEILLÄ
        list.add(new SingleRow("Testiotsikko2","TestiTeksti2")); //TESTATAAN ENSN KIINTEILLÄ
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {

        return list.get(position);
        //return null;

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //View row = layoutInflater.inflate(R.layout.omalayout, parent, false);
        View row = layoutInflater.inflate(R.layout.activity_main,parent,false);
        TextView otsikko = (TextView) row.findViewById(R.id.otsikko);
        TextView teksti = (TextView) row.findViewById(R.id.textiosa);
        SingleRow tmp = list.get(position);
        otsikko.setText(tmp.Otsikko);
        teksti.setText(tmp.Teksti);
        return row;
    }
}


*/



/*
public class adapteri extends ArrayAdapter<String>{

    ArrayList<String> dataset;

    public adapteri(@NonNull Context context, int resource, @NonNull List objects) {
        super(context, resource, objects);

        //this.context = context;
        this.dataset = (ArrayList<String>)objects;



    }

    //TODO Tämä adapteri....

    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View v = layoutInflater.inflate(R.layout.omalayout,parent,false);
        LinearLayout linearLayout = (LinearLayout) v;

        TextView lista = v.findViewById(R.id.lista);
        lista.setText(dataset.get(position).toString());

        Button poista = v.findViewById(R.id.poista);

        poista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataset.remove(position);
                notifyDataSetChanged();

            }
        });


        return v;
    }

}
*/