package com.royken.bracongo.bracongosc.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.royken.bracongo.bracongosc.entities.CentreDistribution;
import com.royken.bracongo.bracongosc.entities.Circuit;

import java.util.List;

@Dao
public interface CentreDistributionDao {

    @Query("select * from cds ")
    LiveData<List<CentreDistribution>> getAllCentreDistribution();

    @Query("SELECT * FROM cds WHERE cdiCodecd =  :codeCd LIMIT 1")
    LiveData<CentreDistribution> findByCode(String codeCd);

    @Insert
    void insertCentreDistribution(CentreDistribution cd);

    @Insert
    void insertAllCentreDistribution(List<CentreDistribution> cds);

    @Update
    void updateCentreDistribution(CentreDistribution cd);

    @Delete
    void deleteCentreDistribution(CentreDistribution cd);

    @Query("DELETE from cds ")
    void deleteAllCentreDistribution();
}
