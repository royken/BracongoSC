package com.royken.bracongo.bracongosc.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.table.TableUtils;
import com.royken.bracongo.bracongosc.R;
import com.royken.bracongo.bracongosc.database.DatabaseHelper;
import com.royken.bracongo.bracongosc.entities.Client;
import com.royken.bracongo.bracongosc.network.RetrofitBuilder;
import com.royken.bracongo.bracongosc.network.WebService;
import com.royken.bracongo.bracongosc.network.util.AndroidNetworkUtility;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import java.sql.SQLException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoadCircuitActivity extends AppCompatActivity {

    public static final String PREFS_NAME = "com.bracongo.bracongoSCFile";

    SharedPreferences settings ;
    SharedPreferences.Editor editor;
    private boolean loadData;

    private DatabaseHelper databaseHelper = null;
    private Button dataBtn;
    private EditText circuitTxt;

    private List<Client> clients;
    private String circuit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_circuit);
        dataBtn = (Button)findViewById(R.id.button);
        circuitTxt = (EditText) findViewById(R.id.circuit);
        settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        loadData = settings.getBoolean("com.royken.data",false);
        if(loadData == true){
            Intent intent = new Intent(LoadCircuitActivity.this,
                    ListClientActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
            LoadCircuitActivity.this.finish();

        }
        else {
            dataBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AndroidNetworkUtility androidNetworkUtility = new AndroidNetworkUtility();
                    SharedPreferences settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
                    if(circuitTxt.getText().length() < 3){
                        Toast.makeText(getApplicationContext(), "Le circuit est invalide", Toast.LENGTH_LONG).show();
                    }
                    else{
                        circuit = circuitTxt.getText().toString().trim();
                        if (!androidNetworkUtility.isConnected(getApplicationContext())) {
                            Toast.makeText(getApplicationContext(), "Aucune connexion au serveur. Veuillez reéssayer plus tard", Toast.LENGTH_LONG).show();
                        } else {

                            new ClientsTask().execute();
                        }
                    }



                }
            });

        }
    }

    private class ClientsTask extends AsyncTask<String, Void, Void> {
        // Required initialization

        private final HttpClient Client = new DefaultHttpClient();
        private String Content;
        private String Error = null;
        private ProgressDialog Dialog = new ProgressDialog(LoadCircuitActivity.this);
        private boolean data;


        protected void onPreExecute() {
            Dialog.setMessage("Récupération des clients...");
            Dialog.show();
        }

        // Call after onPreExecute method
        protected Void doInBackground(String... urls) {
            //Retrofit retrofit = RetrofitBuilder.getRetrofit("https://api.bracongo-cd.com:8443");
            Retrofit retrofit = RetrofitBuilder.getRetrofit("https://api.bracongo-cd.com:8443");
            WebService service = retrofit.create(WebService.class);
            Call<List<Client>> call = service.getClientsCircuit(circuit);
            call.enqueue(new Callback<List<Client>>() {
                @Override
                public void onResponse(Call<List<Client>> call, Response<List<Client>> response) {
                      Log.i("Result....", response.toString());
                   // ClientReponse rep = response.body();
                    clients = response.body();

                    final Dao<Client, Integer> clientDao;
                    try {
                        clientDao = getHelper().getClientDao();
                        if(!clients.isEmpty()){
                            data  = true;
                            TableUtils.dropTable(clientDao.getConnectionSource(), Client.class, true);
                            TableUtils.createTable(clientDao.getConnectionSource(), Client.class);
                            clientDao.create(clients);
                            settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = settings.edit();
                            editor.putBoolean("com.bracongo.data", true);
                            editor.putString("com.bracongo.circuit", circuit.trim());
                            editor.commit();
                            //Log.i("TOTALLLL ===== ",clientDao.countOf()+"");
                            Dialog.dismiss();
                            Intent intent = new Intent(LoadCircuitActivity.this,
                                    ListClientActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            startActivity(intent);
                            LoadCircuitActivity.this.finish();
                        }
                        else {
                            Dialog.dismiss();
                            data = false;
                            Toast.makeText(getApplicationContext(),"Aucun Client trouvé, Vérifiez la connexion/Circuit !!!",Toast.LENGTH_LONG).show();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                @Override
                public void onFailure(Call<List<Client>> call, Throwable t) {
                    Log.i("Error...", t.toString());
                }
            });
            return null;
        }

        protected void onPostExecute(Void unused) {
           // Dialog.dismiss();

        }

    }

    private DatabaseHelper getHelper() {
        if (databaseHelper == null) {
            //databaseHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);
            databaseHelper = new DatabaseHelper(this);
        }
        return databaseHelper;
    }
}
