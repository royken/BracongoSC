package com.royken.bracongo.bracongosc.activity;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;
import android.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.table.TableUtils;
import com.royken.bracongo.bracongosc.MainActivity;
import com.royken.bracongo.bracongosc.R;
import com.royken.bracongo.bracongosc.adapter.ClientAdapter;
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

public class ListClientActivity extends ListActivity implements  SearchView.OnQueryTextListener, VenteCircuitFragment.OnFragmentInteractionListener{

    private static final String ARG_CLIENTID = "idClient";

    private DatabaseHelper databaseHelper = null;
    private ClientAdapter clientAdapter;
    private List<Client> clients;
    public static final String PREFS_NAME = "com.bracongo.bracongoSCFile";
    SharedPreferences settings ;
    Dao<Client, Integer> clientsDao;
    private String circuit;

    private SearchView mSearchView;
    private FloatingActionButton ventesBtn;
    private FloatingActionButton refreshCircuitBtn;
    private FloatingActionButton modifierCircuitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_client);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setActionBar(toolbar);
        mSearchView = (SearchView) findViewById(R.id.searchView);
        ventesBtn = (FloatingActionButton) findViewById(R.id.ventesBtn);
        refreshCircuitBtn = (FloatingActionButton) findViewById(R.id.refreshBtn);
        modifierCircuitBtn = (FloatingActionButton) findViewById(R.id.changerCircBtn);
        try {
            clientsDao = getHelper().getClientDao();
            clients = clientsDao.queryForAll();
            getListView().setTextFilterEnabled(true);
            setupSearchView();
            clientAdapter = new ClientAdapter(getApplicationContext(),clients);
            setListAdapter(clientAdapter);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ventesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        refreshCircuitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
                circuit = settings.getString("com.bracongo.circuit","");
                if(circuit.length() < 5){
                    Toast.makeText(getApplicationContext(), "Le circuit est invalide, Modifiez-le", Toast.LENGTH_LONG).show();
                }
                else {

                    AndroidNetworkUtility androidNetworkUtility = new AndroidNetworkUtility();
                    if (!androidNetworkUtility.isConnected(getApplicationContext())) {
                        Toast.makeText(getApplicationContext(), "Aucune connexion au serveur. Veuillez reéssayer plus tard", Toast.LENGTH_LONG).show();
                    } else {

                        new ClientsTask().execute();
                    }
                }

            }
        });

        modifierCircuitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean("com.bracongo.data", false);
                editor.commit();
                Intent intent = new Intent(ListClientActivity.this,
                        LoadCircuitActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                ListClientActivity.this.finish();
            }
        });
    }

    @Override
    protected void onListItemClick(ListView list, View view, int position, long id) {
        super.onListItemClick(list, view, position, id);
        Intent intent = new Intent(ListClientActivity.this,
                MainActivity.class);

        Log.i("ID SENBTTT", ((Client)clientAdapter.getItem(position)).getId()+"");
        intent.putExtra(ARG_CLIENTID, ((Client)clientAdapter.getItem(position)).getId());
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_circuit) {
            settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("com.bracongo.data", false);
            editor.commit();
            Intent intent = new Intent(ListClientActivity.this,
                    LoadCircuitActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
            ListClientActivity.this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void setupSearchView()
    {
        mSearchView.setIconifiedByDefault(false);
        mSearchView.setOnQueryTextListener(this);
        mSearchView.setSubmitButtonEnabled(true);
        mSearchView.setQueryHint("Recherche");
    }

    @Override
    public boolean onQueryTextChange(String newText)
    {

        if (TextUtils.isEmpty(newText)) {
            getListView().clearTextFilter();
        } else {
            getListView().setFilterText(newText);
        }
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query)
    {
        return false;
    }



    private DatabaseHelper getHelper() {
        if (databaseHelper == null) {
            //databaseHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);
            databaseHelper = new DatabaseHelper(getApplicationContext());
        }
        return databaseHelper;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    private class ClientsTask extends AsyncTask<String, Void, Void> {
        // Required initialization

        private ProgressDialog Dialog = new ProgressDialog(ListClientActivity.this);


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
                            TableUtils.dropTable(clientDao.getConnectionSource(), Client.class, true);
                            TableUtils.createTable(clientDao.getConnectionSource(), Client.class);
                            clientDao.create(clients);
                            settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = settings.edit();
                            editor.putString("com.bracongo.circuit", circuit.trim());
                            editor.commit();
                            //Log.i("TOTALLLL ===== ",clientDao.countOf()+"");
                            Dialog.dismiss();

                            Intent intent = new Intent(ListClientActivity.this,
                                    ListClientActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            startActivity(intent);
                            ListClientActivity.this.finish();
                        }
                        else {
                            Dialog.dismiss();

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


}
