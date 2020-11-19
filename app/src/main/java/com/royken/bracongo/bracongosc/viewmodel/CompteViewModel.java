package com.royken.bracongo.bracongosc.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.royken.bracongo.bracongosc.dao.CompteDao;
import com.royken.bracongo.bracongosc.database.ClientDatabase;
import com.royken.bracongo.bracongosc.entities.Compte;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CompteViewModel extends AndroidViewModel {

    private CompteDao compteDao;
    private ExecutorService executorService;

    public CompteViewModel(@NonNull Application application) {
        super(application);
        compteDao = ClientDatabase.getInstance(application).getCompteDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<Compte>> getAllCompte() {
        return compteDao.getAllCompte();
    }

    public void saveCompte(Compte compte) {
        executorService.execute(() -> compteDao.insert(compte));
    }

    public void saveAllCompte(List<Compte> comptes) {
        executorService.execute(() -> compteDao.insertAllCompte(comptes));
    }

    public void deleteCompte(Compte compte) {
        executorService.execute(() -> compteDao.deleteCompte(compte));
    }

    public void deleteAllCompte() {
        executorService.execute(() -> compteDao.deleteAllCompte());
    }
}
