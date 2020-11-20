package com.royken.bracongo.bracongosc.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.royken.bracongo.bracongosc.entities.Circuit;
import com.royken.bracongo.bracongosc.entities.Client;

import java.util.List;

@Dao
public interface ClientDao {

    @Query("select * from clients ")
    LiveData<List<Client>> getAllClients();

    @Insert
    void insertAllClients(List<Client> clients);

    @Query("SELECT * FROM clients WHERE id =  :id LIMIT 1")
    LiveData<Client> findById(int id);

    @Query("delete from clients ")
    void deleteAllClients();

}
