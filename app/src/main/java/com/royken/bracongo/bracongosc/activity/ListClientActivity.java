package com.royken.bracongo.bracongosc.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
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


import java.sql.SQLException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ListClientActivity extends ListFragment implements  SearchView.OnQueryTextListener{

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
    private TextView title;

    private OnFragmentInteractionListener mListener;

    public static ListClientActivity newInstance() {
        ListClientActivity fragment = new ListClientActivity();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            clientsDao = getHelper().getClientDao();
            clients = clientsDao.queryForAll();
            getListView().setTextFilterEnabled(true);
            setupSearchView();
            clientAdapter = new ClientAdapter(getActivity(),clients);
            setListAdapter(clientAdapter);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        AppBarLayout bar = (AppBarLayout)getActivity().findViewById(R.id.appbar);
        title = (TextView) bar.findViewById(R.id.title);
        View rootView = inflater.inflate(R.layout.activity_list_client, container, false);
        mSearchView = (SearchView) rootView.findViewById(R.id.searchView);
        ventesBtn = (FloatingActionButton) rootView.findViewById(R.id.ventesBtn);
        refreshCircuitBtn = (FloatingActionButton) rootView.findViewById(R.id.refreshBtn);
        modifierCircuitBtn = (FloatingActionButton) rootView.findViewById(R.id.changerCircBtn);

        ventesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settings = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
                circuit = settings.getString("com.bracongo.circuit","");
                if(circuit.length() < 5){
                    Toast.makeText(getActivity(), "Le circuit est invalide, Modifiez-le", Toast.LENGTH_LONG).show();
                }
                Fragment fragment = VenteCircuitFragment.newInstance(circuit);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragment,fragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        refreshCircuitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settings = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
                circuit = settings.getString("com.bracongo.circuit","");
                if(circuit.length() < 5){
                    Toast.makeText(getActivity(), "Le circuit est invalide, Modifiez-le", Toast.LENGTH_LONG).show();
                }
                else {

                    AndroidNetworkUtility androidNetworkUtility = new AndroidNetworkUtility();
                    if (!androidNetworkUtility.isConnected(getActivity())) {
                        Toast.makeText(getActivity(), "Aucune connexion au serveur. Veuillez reéssayer plus tard", Toast.LENGTH_LONG).show();
                    } else {

                        new ClientsTask().execute();
                    }
                }

            }
        });

        modifierCircuitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settings = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean("com.bracongo.data", false);
                editor.commit();
                Intent intent = new Intent(getActivity(),
                        LoadCircuitActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                getActivity().finish();
            }
        });
        return rootView;
    }



    @Override
    public void onListItemClick(ListView list, View view, int position, long id) {
        super.onListItemClick(list, view, position, id);

        Fragment fragment = ClientDetailFragment.newInstance(((Client)clientAdapter.getItem(position)).getId());
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment,fragment);
        ft.addToBackStack(null);
        ft.commit();
        /*Intent intent = new Intent(getActivity(),
                MainActivity.class);

        Log.i("ID SENBTTT", ((Client)clientAdapter.getItem(position)).getId()+"");
        intent.putExtra(ARG_CLIENTID, ((Client)clientAdapter.getItem(position)).getId());
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);*/
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
            databaseHelper = new DatabaseHelper(getActivity());
        }
        return databaseHelper;
    }


    private class ClientsTask extends AsyncTask<String, Void, Void> {
        // Required initialization

        private ProgressDialog Dialog = new ProgressDialog(getActivity());


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
                            settings = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = settings.edit();
                            editor.putString("com.bracongo.circuit", circuit.trim());
                            editor.commit();
                            //Log.i("TOTALLLL ===== ",clientDao.countOf()+"");
                            Dialog.dismiss();

                            Intent intent = new Intent(getActivity(),
                                    MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            startActivity(intent);
                            getActivity().finish();
                        }
                        else {
                            Dialog.dismiss();

                            Toast.makeText(getActivity(),"Aucun Client trouvé, Vérifiez la connexion/Circuit !!!",Toast.LENGTH_LONG).show();
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


}
