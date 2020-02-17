package com.example.ssl_sql_lite;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TauluDao {
    @Query("Select * from Taulu order by id desc")
    List<Taulu> getAllInDescenfingOrder();
    @Insert
    void InsertTaulu(Taulu taulu);
    @Delete void DeleteTaulusta(Taulu taulu);


}
