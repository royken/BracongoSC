package com.royken.bracongo.bracongosc.database;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.royken.bracongo.bracongosc.converters.Converters;
import com.royken.bracongo.bracongosc.dao.CentreDistributionDao;
import com.royken.bracongo.bracongosc.dao.CircuitDao;
import com.royken.bracongo.bracongosc.dao.ClientDao;
import com.royken.bracongo.bracongosc.dao.CompteDao;
import com.royken.bracongo.bracongosc.entities.CentreDistribution;
import com.royken.bracongo.bracongosc.entities.Circuit;
import com.royken.bracongo.bracongosc.entities.Client;
import com.royken.bracongo.bracongosc.entities.Compte;

@Database(version = 2, entities = {Circuit.class, CentreDistribution.class, Compte.class, Client.class})
@TypeConverters({Converters.class})
public abstract class ClientDatabase extends RoomDatabase {

    private static final String DB_NAME = "bracongoSVdb2";
    private static ClientDatabase instance;

    public static synchronized  ClientDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), ClientDatabase.class, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    public abstract CircuitDao getCircuitDao();

    public abstract CompteDao getCompteDao();

    public abstract CentreDistributionDao getCentreDao();

    public abstract ClientDao getClientDao();
}
