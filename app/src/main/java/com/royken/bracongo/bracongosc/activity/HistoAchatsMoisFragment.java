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
import com.royken.bracongo.bracongosc.adapter.AchatJourDataAdapter;
import com.royken.bracongo.bracongosc.adapter.ProduitMoisAdapter;
import com.royken.bracongo.bracongosc.adapter.RemiseAdapter;
import com.royken.bracongo.bracongosc.database.DatabaseHelper;
import com.royken.bracongo.bracongosc.entities.AchatJourData;
import com.royken.bracongo.bracongosc.entities.AchatProduit;
import com.royken.bracongo.bracongosc.entities.Client;
import com.royken.bracongo.bracongosc.entities.PageLog;
import com.royken.bracongo.bracongosc.entities.ProduitMois;
import com.royken.bracongo.bracongosc.entities.RemiseInfo;
import com.royken.bracongo.bracongosc.network.RetrofitBuilder;
import com.royken.bracongo.bracongosc.network.WebService;
import com.royken.bracongo.bracongosc.util.Constants;
import com.royken.bracongo.bracongosc.util.Helper;
import com.royken.bracongo.bracongosc.viewmodel.ClientViewModel;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.security.GeneralSecurityException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function3;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HistoAchatsMoisFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HistoAchatsMoisFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoAchatsMoisFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private final String PAGE_NAME = "HISTO_ACHAT_ANNEE_CLIENT";
    private static final String ARG_CLIENTID = "idClient";
    private int idClient;
    private ListView list;
    private ListView listProduits;
    private AchatJourDataAdapter  achatJourDataAdapter;
    private ProduitMoisAdapter produitMoisAdapter;
    private AchatJourData[] jourData;
    List<AchatProduit> achatProduits;
    List<ProduitMois> produitMois;
    private Client client;

    private OnFragmentInteractionListener mListener;

    private TextView title;

    private ClientViewModel clientViewModel;

    KLoadingSpin spinner;
    private String accessToken;
    private String userName;

    private SharedPreferences sharedPreferences;

    public HistoAchatsMoisFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment HistoAchatsMoisFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HistoAchatsMoisFragment newInstance(int param1) {
        HistoAchatsMoisFragment fragment = new HistoAchatsMoisFragment();
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
            logPage();
            getData();
        });
    }

    private void getData() {
        spinner.startAnimation();
        spinner.setIsVisible(true);
        Retrofit retrofit = RetrofitBuilder.getRetrofit(Constants.API_BASE_URL, accessToken);
        WebService service = retrofit.create(WebService.class);
        Observable.zip(service.getHistoAchatsMois(client.getNumero().trim(),getIntFromClient(client.getNumero().trim())+""), service.getProduitsAchatsMois(client.getNumero().trim(),getIntFromClient(client.getNumero().trim())+""), new BiFunction<List<AchatProduit>, List<ProduitMois>, MergedResponse>() {
            int i;
            @Override
            public MergedResponse apply(List<AchatProduit> achatProduits, List<ProduitMois> produitMois) throws Exception {
                // Log.i("OBJECTIF", objectifData.toString());
                return new MergedResponse(produitMois, achatProduits);
            }
        }).subscribe(new Observer<MergedResponse>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(MergedResponse mergedResponse) {
                //Log.i("RESULT", mergedResponse.toString());
                achatProduits = mergedResponse.getVentesMois();
                produitMois = mergedResponse.getProduitsMois();
                // test();
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof SocketTimeoutException) {
                    spinner.stopAnimation();
                    spinner.setIsVisible(false);
                }
            }

            @Override
            public void onComplete() {

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        computeAchatData();
                        computeProduitsData();
                    }
                });

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_histo_achats_mois, container, false);
        TextView title = (TextView) getActivity().findViewById(R.id.title);
        title.setText("Historique des achats du mois");
        list = (ListView) rootView.findViewById(R.id.listAchats);
        listProduits = (ListView) rootView.findViewById(R.id.listProduits);
        spinner = rootView.findViewById(R.id.spinner);
        //AppBarLayout bar = (AppBarLayout)getActivity().findViewById(R.id.appbar);
        //title = (TextView) bar.findViewById(R.id.title);
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
            //logPage();
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

    private class MergedResponse{
        public List<ProduitMois> produitMoisList;
        public List<AchatProduit> achatProduitList;



        public List<ProduitMois> getProduitsMois() {
            return produitMoisList;
        }

        public List<AchatProduit> getVentesMois() {
            return achatProduitList;
        }


        public MergedResponse() {
        }

        public MergedResponse(List<ProduitMois> produitMoisList, List<AchatProduit> achatProduitList){
            this.produitMoisList = produitMoisList;
            this.achatProduitList = achatProduitList;

        }

        @Override
        public String toString() {
            return "MergedResponse{" +
                    "produitMoisList=" + produitMoisList +
                    ", achatProduitList=" + achatProduitList +
                    '}';
        }
    }

    private void computeAchatData(){
        Calendar cal = Calendar.getInstance();
        int jour = cal.get(Calendar.DAY_OF_MONTH);
        jourData = new AchatJourData[jour];
        for(int i = 0; i < jour; i++){
            jourData[i] = new AchatJourData();
        }

        for (AchatProduit achat: achatProduits) {
            if(achat.getFamille().equalsIgnoreCase("BIERE")){
                jourData[achat.getJour() - 1].addBi(achat.getQuantite());
            }

            if(achat.getFamille().equalsIgnoreCase("BG")){
                jourData[achat.getJour() - 1].addBg(achat.getQuantite());
            }

            if(achat.getFamille().equalsIgnoreCase("PET")){
                jourData[achat.getJour() - 1].addPet(achat.getQuantite());
            }
            jourData[achat.getJour() - 1].addCA(achat.getMontant());
            jourData[achat.getJour() - 1].addProduit(achat.getProduit() + ": "+ achat.getQuantite() );
        }

        achatJourDataAdapter = new AchatJourDataAdapter(getActivity(), Arrays.asList(jourData));
        list.setAdapter(achatJourDataAdapter);
        Helper.getListViewSize(list);
    }

    private void computeProduitsData(){
        produitMoisAdapter = new ProduitMoisAdapter(getActivity(), produitMois);
        listProduits.setAdapter(produitMoisAdapter);
        Helper.getListViewSize(listProduits);
    }

    private void logPage() {
        Retrofit retrofit = RetrofitBuilder.getRetrofit(Constants.API_BASE_URL, accessToken);
        WebService service = retrofit.create(WebService.class);
        PageLog page = new PageLog();
        page.setPage(PAGE_NAME);
        page.setUtilisateur(userName);
        page.setClient(client.getNumero().trim());
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
