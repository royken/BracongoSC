package com.royken.bracongo.bracongosc.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.royken.bracongo.bracongosc.dao.ClientDao;
import com.royken.bracongo.bracongosc.database.ClientDatabase;
import com.royken.bracongo.bracongosc.entities.Client;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientViewModel extends AndroidViewModel {

    private ClientDao clientDao;
    private ExecutorService executorService;

    public ClientViewModel(@NonNull Application application) {
        super(application);
        clientDao = ClientDatabase.getInstance(application).getClientDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<Client>> getAllClients() {
        return clientDao.getAllClients();
    }

    public LiveData<Client> getById(int id){
        return clientDao.findById(id);
    }

    public void saveAllClients(List<Client> clients) {
        executorService.execute(() -> clientDao.insertAllClients(clients));
    }

    public void deleteAllClient() {
        executorService.execute(() -> clientDao.deleteAllClients());
    }
}
