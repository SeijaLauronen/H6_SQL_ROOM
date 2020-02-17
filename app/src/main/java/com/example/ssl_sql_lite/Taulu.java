package com.example.ssl_sql_lite;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Taulu {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String teksti;
    public String pvm;
}
