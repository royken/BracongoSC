package com.royken.bracongo.bracongosc.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;

import com.example.kloadingspin.KLoadingSpin;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.snackbar.Snackbar;
import com.j256.ormlite.dao.Dao;
import com.royken.bracongo.bracongosc.R;
import com.royken.bracongo.bracongosc.adapter.AchatMoisDataAdapter;
import com.royken.bracongo.bracongosc.adapter.MaterielAdapter;
import com.royken.bracongo.bracongosc.database.DatabaseHelper;
import com.royken.bracongo.bracongosc.entities.AchatMoisData;
import com.royken.bracongo.bracongosc.entities.AchatProduitMois;
import com.royken.bracongo.bracongosc.entities.Client;
import com.royken.bracongo.bracongosc.entities.Materiel;
import com.royken.bracongo.bracongosc.network.RetrofitBuilder;
import com.royken.bracongo.bracongosc.network.WebService;
import com.royken.bracongo.bracongosc.util.Helper;
import com.royken.bracongo.bracongosc.viewmodel.ClientViewModel;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HistoAchatsAnneeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HistoAchatsAnneeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoAchatsAnneeFragment extends Fragment {
    private static final String ARG_CLIENTID = "idClient";
    private int idClient;
    private ListView list;
    private Client client;
    private List<AchatProduitMois> achatProduitMois;
    private AchatMoisData[] achatMoisData;
    private AchatMoisDataAdapter achatMoisDataAdapter;

    private OnFragmentInteractionListener mListener;

    private TextView title;

    private ClientViewModel clientViewModel;

    KLoadingSpin spinner;
    private String accessToken;

    private SharedPreferences sharedPreferences;

    public HistoAchatsAnneeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment HistoAchatsAnneeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HistoAchatsAnneeFragment newInstance(int param1) {
        HistoAchatsAnneeFragment fragment = new HistoAchatsAnneeFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_CLIENTID, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idClient = getArguments().getInt(ARG_CLIENTID);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        clientViewModel = new ViewModelProvider(this).get(ClientViewModel.class);
        clientViewModel.getById(idClient).observe(getViewLifecycleOwner(), client_ -> {
            client = client_;
            getData();
        });
    }

    private void getData() {
        spinner.startAnimation();
        spinner.setIsVisible(true);
        Retrofit retrofit = RetrofitBuilder.getRetrofit("http://10.0.2.2:8085", accessToken);
        WebService service = retrofit.create(WebService.class);
        service.getHistoAchatsAnnee(client.getNumero().trim(),getIntFromClient(client.getNumero().trim())+"")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<AchatProduitMois>>() {

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
                        Calendar cal = Calendar.getInstance();
                        int jour = cal.get(Calendar.MONTH) + 1;
                        achatMoisData = new AchatMoisData[jour];
                        for(int i = 0; i < jour; i++){
                            achatMoisData[i] = new AchatMoisData();
                        }

                        for (AchatProduitMois achat: achatProduitMois) {
                            if(achat.getFamille().equalsIgnoreCase("BIERE")){
                                achatMoisData[achat.getJour() - 1].addBi(achat.getQuantite());
                            }

                            if(achat.getFamille().equalsIgnoreCase("BG")){
                                achatMoisData[achat.getJour() - 1].addBg(achat.getQuantite());
                            }

                            if(achat.getFamille().equalsIgnoreCase("PET")){
                                achatMoisData[achat.getJour() - 1].addPet(achat.getQuantite());
                            }
                            achatMoisData[achat.getJour() - 1].addCA(achat.getMontant());
                        }

                        achatMoisDataAdapter = new AchatMoisDataAdapter(getActivity(), Arrays.asList(achatMoisData));
                        list.setAdapter(achatMoisDataAdapter);
                        Helper.getListViewSize(list);
                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<AchatProduitMois> response) {
                        achatProduitMois = response;
                    }
                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_histo_achats_annee, container, false);
        list = (ListView) rootView.findViewById(R.id.listAchats);
        //AppBarLayout bar = (AppBarLayout)getActivity().findViewById(R.id.appbar);
       // title = (TextView) bar.findViewById(R.id.title);
        spinner = rootView.findViewById(R.id.spinner);
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
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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


    private static long getIntFromClient(String clientNumber){
        long hash = 0;
        for(int i = 0; i < clientNumber.length(); i ++){
            hash += clientNumber.charAt(i) * (i+1);
        }
        return hash;
    }
}
