package com.royken.bracongo.bracongosc.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.table.TableUtils;
import com.royken.bracongo.bracongosc.CamionFragment;
import com.royken.bracongo.bracongosc.ChoixCircuitFragment;
import com.royken.bracongo.bracongosc.MainActivity;
import com.royken.bracongo.bracongosc.R;
import com.royken.bracongo.bracongosc.adapter.ClientAdapter;
import com.royken.bracongo.bracongosc.adapter.ClientRecycleAdapter;
import com.royken.bracongo.bracongosc.database.DatabaseHelper;
import com.royken.bracongo.bracongosc.entities.Client;
import com.royken.bracongo.bracongosc.entities.PageLog;
import com.royken.bracongo.bracongosc.network.RetrofitBuilder;
import com.royken.bracongo.bracongosc.network.WebService;
import com.royken.bracongo.bracongosc.network.util.AndroidNetworkUtility;
import com.royken.bracongo.bracongosc.util.ModuleChoice;
import com.royken.bracongo.bracongosc.viewmodel.CircuitViewModel;
import com.royken.bracongo.bracongosc.viewmodel.ClientViewModel;


import java.io.IOException;
import java.security.GeneralSecurityException;
import java.sql.SQLException;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ListClientActivity extends Fragment implements  SearchView.OnQueryTextListener, ClientRecycleAdapter.ClientsAdapterListener {

    private static final String ARG_CLIENTID = "idClient";

    private final String PAGE_NAME = "LISTE_CLIENTS";
    private List<Client> clients;
    private SharedPreferences sharedPreferences;
    Dao<Client, Integer> clientsDao;
    private String circuit;

    private SearchView mSearchView;
    private FloatingActionButton ventesBtn;
    private FloatingActionButton refreshCircuitBtn;
    private FloatingActionButton modifierCircuitBtn;
    private TextView title;
    private RecyclerView recyclerView;
    private ClientRecycleAdapter clientRecycleAdapter;

    private ClientViewModel clientViewModel;

    private OnFragmentInteractionListener mListener;
    private String accessToken;

    private String userName;

    public static ListClientActivity newInstance() {
        ListClientActivity fragment = new ListClientActivity();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
            title.setText("Liste clients" );
            clientViewModel = new ViewModelProvider(this).get(ClientViewModel.class);
            clientRecycleAdapter = new ClientRecycleAdapter(getActivity(), this);
            setupSearchView();
            clientViewModel.getAllClients().observe(getViewLifecycleOwner(), clientList -> {
                clientRecycleAdapter.setData(clientList);
                recyclerView.setAdapter(clientRecycleAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //AppBarLayout bar = (AppBarLayout)getActivity().findViewById(R.id.appbar);

        View rootView = inflater.inflate(R.layout.activity_list_client, container, false);
        title = (TextView) getActivity().findViewById(R.id.title);
        mSearchView = (SearchView) rootView.findViewById(R.id.searchView);
        recyclerView = rootView.findViewById(R.id.recycler_view);
        ventesBtn = (FloatingActionButton) rootView.findViewById(R.id.ventesBtn);
        refreshCircuitBtn = (FloatingActionButton) rootView.findViewById(R.id.refreshBtn);
        modifierCircuitBtn = (FloatingActionButton) rootView.findViewById(R.id.changerCircBtn);

        MasterKey masterKey = null;


        try {
            masterKey = new MasterKey.Builder(getContext(),MasterKey.DEFAULT_MASTER_KEY_ALIAS).setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build();
            sharedPreferences = EncryptedSharedPreferences.create(
                    getContext(),
                    "com.bracongo.bracongosc.sharedPrefs",
                    masterKey,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );
            accessToken = sharedPreferences.getString("user.accessToken", "");
            userName  = sharedPreferences.getString("user.username", "");
            circuit = sharedPreferences.getString("config.circuit", "");
            Log.i("CIRCUIT+++++", circuit);
            logPage();


        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

       /* ventesBtn.setOnClickListener(new View.OnClickListener() {
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
        });*/

       /* refreshCircuitBtn.setOnClickListener(new View.OnClickListener() {
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

                       // new ClientsTask().execute();
                    }
                }

            }
        });
        */

        modifierCircuitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences.edit().putBoolean("config.clientLoaded", false).apply();
                Fragment fragment = ChoixCircuitFragment.newInstance(String.valueOf(ModuleChoice.SUIVI));
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.fragment,fragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        ventesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(circuit.length() < 5){
                    Toast.makeText(getActivity(), "Le circuit est invalide, Modifiez-le", Toast.LENGTH_LONG).show();
                }
                else{
                    Fragment fragment = VenteCircuitFragment.newInstance(circuit);
                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.fragment,fragment);
                    ft.addToBackStack(null);
                    ft.commit();
                }

            }
        });

        refreshCircuitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = CamionFragment.newInstance();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.fragment,fragment);
                ft.addToBackStack(null);
                ft.commit();

            }
        });


        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                clientRecycleAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                clientRecycleAdapter.getFilter().filter(query);
                return false;
            }
        });
        return rootView;
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
           // getListView().clearTextFilter();

        } else {
            //getListView().setFilterText(newText);
        }
        clientRecycleAdapter.getFilter().filter(newText);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query)
    {
        clientRecycleAdapter.getFilter().filter(query);
        return false;
    }



    @Override
    public void onClientSelected(Client client) {
        Fragment fragment = ClientDetailFragment.newInstance(client.getId());
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment,fragment);
        ft.addToBackStack(null);
        ft.commit();
    }


 /*   private class ClientsTask extends AsyncTask<String, Void, Void> {
        // Required initialization

        private ProgressDialog Dialog = new ProgressDialog(getActivity());


        protected void onPreExecute() {
            Dialog.setMessage("Récupération des clients...");
            Dialog.show();
        }

        // Call after onPreExecute method
        protected Void doInBackground(String... urls) {
            //Retrofit retrofit = RetrofitBuilder.getRetrofit("https://api.bracongo-cd.com:8443");
            Retrofit retrofit = RetrofitBuilder.getRetrofit("https://api.bracongo-cd.com:8443","");
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
    */

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

    private void logPage() {
        Retrofit retrofit = RetrofitBuilder.getRetrofit("http://10.0.2.2:8085", accessToken);
        WebService service = retrofit.create(WebService.class);
        PageLog page = new PageLog();
        page.setPage(PAGE_NAME);
        page.setUtilisateur(userName);
        service.pageLog(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PageLog>() {

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(PageLog compte) {

                    }
                });
    }


}
