package com.royken.bracongo.bracongosc.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.royken.bracongo.bracongosc.entities.Compte;

import java.util.List;

@Dao
public interface CompteDao {

    @Query("select * from comptes ")
    LiveData<List<Compte>> getAllCompte();

    @Insert
    void insertAllCompte(List<Compte> comptes);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Compte compte);

    @Update
    void updateCompte(Compte compte);

    @Query("delete from comptes ")
    void deleteAllCompte();

    @Delete
    void deleteCompte(Compte compte);

}
