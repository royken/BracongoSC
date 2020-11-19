package com.royken.bracongo.bracongosc.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.royken.bracongo.bracongosc.dao.CircuitDao;
import com.royken.bracongo.bracongosc.dao.CompteDao;
import com.royken.bracongo.bracongosc.database.ClientDatabase;
import com.royken.bracongo.bracongosc.entities.Circuit;
import com.royken.bracongo.bracongosc.entities.Compte;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CircuitViewModel extends AndroidViewModel {

    private CircuitDao circuitDao;
    private ExecutorService executorService;

    public CircuitViewModel(@NonNull Application application) {
        super(application);
        circuitDao = ClientDatabase.getInstance(application).getCircuitDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<Circuit>> getAllCircuits() {
        return circuitDao.getAllCircuit();
    }

    public LiveData<List<Circuit>> getAllCircuitsByCd(String codeCd) {
        return circuitDao.getAllCircuitByCd(codeCd);
    }

    public LiveData<Circuit> getCircuitByCode(String code) {
        return  circuitDao.findByCode(code);
    }

    public void saveCircuit(Circuit circuit) {
        executorService.execute(() -> circuitDao.insertCircuit(circuit));
    }

    public void saveAllCircuit(List<Circuit> circuits) {
        executorService.execute(() -> circuitDao.insertAllCircuit(circuits));
    }

    public void deleteCircuit(Circuit circuit) {
        executorService.execute(() -> circuitDao.deleteCircuit(circuit));
    }


}
