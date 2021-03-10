package com.royken.bracongo.bracongosc.activity;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kloadingspin.KLoadingSpin;
import com.google.android.material.snackbar.Snackbar;
import com.royken.bracongo.bracongosc.R;
import com.royken.bracongo.bracongosc.entities.Client;
import com.royken.bracongo.bracongosc.entities.Compte;
import com.royken.bracongo.bracongosc.entities.DemandeModificationClient;
import com.royken.bracongo.bracongosc.entities.DemandeModificationClientDto;
import com.royken.bracongo.bracongosc.entities.DemandeModificationMotDePasse;
import com.royken.bracongo.bracongosc.entities.PageLog;
import com.royken.bracongo.bracongosc.network.NetworkUtil;
import com.royken.bracongo.bracongosc.network.RetrofitBuilder;
import com.royken.bracongo.bracongosc.network.WebService;
import com.royken.bracongo.bracongosc.viewmodel.ClientViewModel;

import java.io.IOException;
import java.security.GeneralSecurityException;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ModificationClientFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ModificationClientFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_CLIENT_ID = "idClient";
    private final String PAGE_NAME = "MODIFICATION_CLIENT";
    private View parent_view;

    private OnFragmentInteractionListener mListener;

    Client client;

    private String accessToken;

    private String userName;

    private String role;

    private SharedPreferences sharedPreferences;

    private Button envoyerBtn;

    private Spinner attributSpinner;

    private EditText valeurTxt;

    private int positionAttribut;

    private ClientViewModel clientViewModel;

    KLoadingSpin spinner;

    private DemandeModificationClientDto demande;

    private DemandeModificationClient result;

    private LinearLayout layout;
    // TODO: Rename and change types of parameters
    private int idClient;

    public ModificationClientFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param idClient le numero de compte.
     * @return A new instance of fragment ModificationClientFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ModificationClientFragment newInstance(int idClient) {
        ModificationClientFragment fragment = new ModificationClientFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_CLIENT_ID, idClient);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //title.setText("PLAINTES");
        clientViewModel = new ViewModelProvider(this).get(ClientViewModel.class);
        clientViewModel.getById(idClient).observe(getViewLifecycleOwner(), client_ -> {
            client = client_;
            logPage();
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idClient = getArguments().getInt(ARG_CLIENT_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        TextView title = (TextView) getActivity().findViewById(R.id.title);
        title.setText("Modification client");

        View rootView = inflater.inflate(R.layout.fragment_modification_client, container, false);
        parent_view = rootView.findViewById(android.R.id.content);
        valeurTxt = rootView.findViewById(R.id.valeur);
        attributSpinner = rootView.findViewById(R.id.attribut);
        envoyerBtn = rootView.findViewById(R.id.enregistrer);
        spinner = rootView.findViewById(R.id.spinner);
        spinner.setIsVisible(false);
        Resources res = getResources();
        String[] attributs = res.getStringArray(R.array.modification_attribut_list);
        demande = new DemandeModificationClientDto();
        attributSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                demande.setAttribut(attributs[position]);
                positionAttribut = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
                envoyerBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String valeur = valeurTxt.getText().toString();
                        if(valeur.isEmpty()){
                            Toast.makeText(getContext(),"La nouvelle valeur est obligatoire", Toast.LENGTH_LONG).show();
                            return;
                        }
                        demande.setValeur(valeur.trim().toUpperCase());
                        demande.setUsername(userName);
                        demande.setRole(role);
                        demande.setClient(client.getNumero().trim());
                        if(!NetworkUtil.isConnected(getActivity())){
                            Snackbar.make(parent_view, "Vérifiez votre connexion internet", Snackbar.LENGTH_LONG).show();
                        }
                        else {
                            envoyerDemande();
                        }
                    }
                });
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
            role = sharedPreferences.getString("user.role", "");
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rootView;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void logPage() {
        Retrofit retrofit = RetrofitBuilder.getRetrofit("http://10.0.2.2:8085", accessToken);
        WebService service = retrofit.create(WebService.class);
        PageLog page = new PageLog();
        page.setPage(PAGE_NAME);
        page.setClient(client.getNumero());
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

    private void envoyerDemande() {
        spinner.startAnimation();
        spinner.setIsVisible(true);
        Retrofit retrofit = RetrofitBuilder.getRetrofit("http://10.0.2.2:8085", accessToken);
        WebService service = retrofit.create(WebService.class);
        service.demanderModification(demande)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DemandeModificationClient>() {

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
                        if(result.getId() == null){
                            Snackbar.make(getActivity().findViewById(android.R.id.content), "Erreur lors de l'enregistrement", Snackbar.LENGTH_SHORT).show();
                            return;
                        }
                        Fragment fragment = ClientDetailFragment.newInstance(client.getId());
                        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.fragment,fragment);
                        ft.addToBackStack(null);
                        ft.commit();
                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(DemandeModificationClient demande_) {
                        result = demande_;
                    }
                });
    }
}