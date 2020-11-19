package com.royken.bracongo.bracongosc.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.royken.bracongo.bracongosc.entities.Circuit;

import java.util.List;

@Dao
public interface CircuitDao {

    @Query("select * from circuits order by cirCodcir")
    LiveData<List<Circuit>> getAllCircuit();

    @Query("select * from circuits where substr(cirCodcir, 1, 2) = :codeCd order by cirCodcir")
    LiveData<List<Circuit>> getAllCircuitByCd(String codeCd);

    @Query("SELECT * FROM circuits WHERE cirCodcir =  :codeCircuit LIMIT 1")
    LiveData<Circuit> findByCode(String codeCircuit);

    @Insert
    void insertCircuit(Circuit circuit);

    @Insert
    void insertAllCircuit(List<Circuit> circuits);

    @Update
    void updateCircuit(Circuit circuit);

    @Delete
    void deleteCircuit(Circuit circuit);

    @Query("DELETE FROM circuits")
    void deleteAllCircuit();

}
