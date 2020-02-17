package com.example.ssl_sql_lite;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities ={Taulu.class} , version=1)


public abstract class Tietokanta extends RoomDatabase {
    public static final String NIMI= "TESTIKANTA";
    public  abstract TauluDao tauluDao();

}
