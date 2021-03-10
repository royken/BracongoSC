package com.royken.bracongo.bracongosc.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.royken.bracongo.bracongosc.dao.CentreDistributionDao;
import com.royken.bracongo.bracongosc.database.ClientDatabase;
import com.royken.bracongo.bracongosc.entities.CentreDistribution;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CdViewModel extends AndroidViewModel {

    private CentreDistributionDao cdDao;
    private ExecutorService executorService;

    public CdViewModel(@NonNull Application application) {
        super(application);
        cdDao = ClientDatabase.getInstance(application).getCentreDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<CentreDistribution>> getAllCds() {
        return cdDao.getAllCentreDistribution();
    }

    public LiveData<CentreDistribution> getCdByCode(String code) {
        return cdDao.findByCode(code);
    }

    public LiveData<CentreDistribution> getCdByCircuit(String codeCircuit) {
        return cdDao.findByCircuit(codeCircuit);
    }

    public void saveCd(CentreDistribution cd) {
        executorService.execute(() -> cdDao.insertCentreDistribution(cd));
    }

    public void saveAllCd(List<CentreDistribution> cds) {
        executorService.execute(() -> cdDao.insertAllCentreDistribution(cds));
    }

    public void deleteCd(CentreDistribution cd) {
        executorService.execute(() -> cdDao.deleteCentreDistribution(cd));
    }

    public void deleteAllCds() {
        executorService.execute(() -> cdDao.deleteAllCentreDistribution());
    }
}
