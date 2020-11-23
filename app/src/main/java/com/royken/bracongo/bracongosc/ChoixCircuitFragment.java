package com.royken.bracongo.bracongosc;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kloadingspin.KLoadingSpin;
import com.google.android.material.snackbar.Snackbar;
import com.royken.bracongo.bracongosc.activity.ListClientActivity;
import com.royken.bracongo.bracongosc.adapter.CircuitRecycleAdapter;
import com.royken.bracongo.bracongosc.adapter.CircuitRecycleFilterAdapter;
import com.royken.bracongo.bracongosc.adapter.ClientRecycleAdapter;
import com.royken.bracongo.bracongosc.entities.Circuit;
import com.royken.bracongo.bracongosc.entities.Client;
import com.royken.bracongo.bracongosc.network.RetrofitBuilder;
import com.royken.bracongo.bracongosc.network.WebService;
import com.royken.bracongo.bracongosc.viewmodel.CircuitViewModel;
import com.royken.bracongo.bracongosc.viewmodel.ClientViewModel;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChoixCircuitFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChoixCircuitFragment extends Fragment  implements  SearchView.OnQueryTextListener, CircuitRecycleFilterAdapter.CircuitAdapterListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView circuitRecyclerView;
    private Button suivantBtn;
    private CircuitRecycleFilterAdapter circuitRecycleAdapter;
    private CircuitViewModel circuitViewModel;
    private ClientViewModel clientViewModel;
    private List<Client> clients;
    private String circuit;
    KLoadingSpin spinner;
    private String accessToken;
    private SearchView mSearchView;

    private SharedPreferences sharedPreferences;
    private TextView title;

    public ChoixCircuitFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ChoixCircuitFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChoixCircuitFragment newInstance() {
        ChoixCircuitFragment fragment = new ChoixCircuitFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_choix_circuit, container, false);
        title = (TextView) getActivity().findViewById(R.id.title);
        spinner = rootView.findViewById(R.id.spinner);
        mSearchView = (SearchView) rootView.findViewById(R.id.searchView);
        circuitRecyclerView = rootView.findViewById(R.id.recycler_view);

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
            boolean clientLoaded = sharedPreferences.getBoolean("config.clientLoaded", false);
            if(clientLoaded){
                Fragment fragment = ListClientActivity.newInstance();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.fragment,fragment);
                //ft.addToBackStack("choixCircuitSuiviFragment");
                ft.commit();
            }


        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                circuitRecycleAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                circuitRecycleAdapter.getFilter().filter(query);
                return false;
            }
        });
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        title.setText("Choix Circuit");
        circuitViewModel = new ViewModelProvider(this).get(CircuitViewModel.class);
        clientViewModel = new ViewModelProvider(this).get(ClientViewModel.class);
        circuitRecycleAdapter = new CircuitRecycleFilterAdapter(getActivity(), this);
        setupSearchView();
        circuitViewModel.getAllCircuits().observe(getViewLifecycleOwner(), circuitList -> {
            circuitRecycleAdapter.setData(circuitList);
            circuitRecyclerView.setAdapter(circuitRecycleAdapter);
            circuitRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        });

    }

    private void setupSearchView()
    {
        mSearchView.setIconifiedByDefault(false);
        mSearchView.setOnQueryTextListener(this);
        mSearchView.setSubmitButtonEnabled(true);
        mSearchView.setQueryHint("Recherche");
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        circuitRecycleAdapter.getFilter().filter(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (TextUtils.isEmpty(newText)) {
            // getListView().clearTextFilter();

        } else {
            //getListView().setFilterText(newText);
        }
        circuitRecycleAdapter.getFilter().filter(newText);
        return true;
    }

    @Override
    public void onCircuitSelected(Circuit circuit) {
        if(circuit == null){
            Toast.makeText(getContext(), "La selection du circuit est obligatoire", Toast.LENGTH_LONG).show();
        }
        loadClients(circuit.getCirCodcir().trim());
    }

    private void loadClients(String circuit){
        spinner.startAnimation();
        spinner.setIsVisible(true);
        Retrofit retrofit = RetrofitBuilder.getRetrofit("http://10.0.2.2:8085", accessToken);
        WebService service = retrofit.create(WebService.class);
        service.getClientsCircuit(circuit)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Client>>() {

                    @Override
                    public void onError(Throwable e) {
                        Log.i("ERROR CALL", e.getMessage());
                        Snackbar.make(getActivity().findViewById(android.R.id.content), "Erreur connexion, essayer ultérieurement", Snackbar.LENGTH_LONG).show();
                        spinner.stopAnimation();
                        spinner.setIsVisible(false);
                        //layout.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onComplete() {
                        spinner.stopAnimation();
                        spinner.setIsVisible(false);
                        // layout.setVisibility(View.VISIBLE);
                        if(clients.isEmpty()){
                            Snackbar.make(getActivity().findViewById(android.R.id.content), "Aucun client trouvé", Snackbar.LENGTH_SHORT).show();
                            return;
                        }
                        clientViewModel.deleteAllClient();
                        clientViewModel.saveAllClients(clients);
                        sharedPreferences.edit().putBoolean("config.clientLoaded", true).apply();
                        Fragment fragment = ListClientActivity.newInstance();
                        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.fragment,fragment);
                        // ft.addToBackStack("choixCircuitSuiviFragment");
                        ft.commit();
                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Client> clientList) {
                        clients = clientList;
                    }
                });
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}