package com.simonesolita.pswstorer.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.simonesolita.pswstorer.entities.Credenziale;

import java.util.List;

@Dao
public interface CredenzialeDao {

    @Insert
    void insert(Credenziale credenziale);
    @Update
    void update(Credenziale credenziale);
    @Delete
    void delete(Credenziale credenziale);

    @Query("DELETE FROM credenziale_table")
    void deleteAllCredenziali();

    @Query("SELECT * FROM credenziale_table ORDER BY id asc")
    LiveData<List<Credenziale>> getAllCredenziali();
}
