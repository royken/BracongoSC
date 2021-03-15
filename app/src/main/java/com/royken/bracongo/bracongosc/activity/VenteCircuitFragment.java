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

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;

import com.example.kloadingspin.KLoadingSpin;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.snackbar.Snackbar;
import com.royken.bracongo.bracongosc.MainActivity;
import com.royken.bracongo.bracongosc.R;
import com.royken.bracongo.bracongosc.adapter.AchatMoisDataAdapter;
import com.royken.bracongo.bracongosc.adapter.ProduitMoisCircuitAdapter;
import com.royken.bracongo.bracongosc.database.DatabaseHelper;
import com.royken.bracongo.bracongosc.entities.AchatMoisData;
import com.royken.bracongo.bracongosc.entities.AchatProduitMois;
import com.royken.bracongo.bracongosc.entities.PageLog;
import com.royken.bracongo.bracongosc.entities.ProduitMois;
import com.royken.bracongo.bracongosc.network.RetrofitBuilder;
import com.royken.bracongo.bracongosc.network.WebService;
import com.royken.bracongo.bracongosc.util.Constants;
import com.royken.bracongo.bracongosc.util.Helper;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.security.GeneralSecurityException;
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
 * {@link VenteCircuitFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link VenteCircuitFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VenteCircuitFragment extends Fragment {
    public static final String PREFS_NAME = "com.bracongo.bracongoSCFile";
    private static final String ARG_CLIENTID = "idClient";
    private final String PAGE_NAME = "VENTE_CIRCUIT";
    private static final String ARG_CIRCUIT = "circuit";
    private ProgressDialog Dialog1 ;
    private ProgressDialog Dialog2;
    private ListView listVw;
    private ListView produitsVw;
    private List<AchatProduitMois> achatProduitMois;
    private List<ProduitMois> produitsMois;
    private ProduitMoisCircuitAdapter produitsAdapter;
    private AchatMoisData[] achatMoisData;
    private AchatMoisDataAdapter achatMoisDataAdapter;

    private String circuit;

    private int casierTotal;
    private int casierTotalBi;
    private int casierTotalBg;
    private int casierTotalPet;

    private double hectoTotal;
    private double hectoTotalBi;
    private double hectoTotalBg;
    private double hectoTotalPet;

    private TextView casierTotalTvw;
    private TextView casierTotalBiTvw;
    private TextView casierTotalBgTvw;
    private TextView casierTotalPetTvw;

    private TextView hectoTotalTvw;
    private TextView hectoTotalBiTvw;
    private TextView hectoTotalBgTvw;
    private TextView hectoTotalPetTvw;

    private OnFragmentInteractionListener mListener;

    private TextView title;

    private String accessToken;

    private String userName;

    private SharedPreferences sharedPreferences;

    KLoadingSpin spinner;

    private View parent_view;

    public VenteCircuitFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param circuit Parameter 1.
     * @return A new instance of fragment VenteCircuitFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VenteCircuitFragment newInstance(String circuit) {
        VenteCircuitFragment fragment = new VenteCircuitFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CIRCUIT, circuit);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            circuit = getArguments().getString(ARG_CIRCUIT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_vente_circuit, container, false);
        TextView title = (TextView) getActivity().findViewById(R.id.title);
        title.setText("Ventes circuit");
        parent_view = rootView.findViewById(android.R.id.content);
        listVw = (ListView) rootView.findViewById(R.id.listAchats);
        produitsVw = (ListView) rootView.findViewById(R.id.listProduits);
        casierTotalTvw = (TextView) rootView.findViewById(R.id.casiersTotal);
        casierTotalBiTvw = (TextView) rootView.findViewById(R.id.casiersTotalBi);
        casierTotalBgTvw = (TextView) rootView.findViewById(R.id.casiersTotalBg);
        casierTotalPetTvw = (TextView) rootView.findViewById(R.id.casiersTotalPet);

        hectoTotalTvw = (TextView) rootView.findViewById(R.id.hectoTotal);
        hectoTotalBiTvw = (TextView) rootView.findViewById(R.id.hectoTotalBi);
        hectoTotalBgTvw = (TextView) rootView.findViewById(R.id.hectoTotalBg);
        hectoTotalPetTvw = (TextView) rootView.findViewById(R.id.hectoTotalPet);
        spinner = rootView.findViewById(R.id.spinner);
        spinner.setIsVisible(false);
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
            //ogPage();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
            //title.setText("HISTO ACHATS DU MOIS : " + circuit );
            getVentesCircuitData();
            logPage();
            /*Dialog1 = new ProgressDialog(getActivity());
            Dialog2 = new ProgressDialog(getActivity());
            Dialog1.setMessage("Récupération des informations...");
            Dialog2.setMessage("Récupération des informations...");
            Dialog1.show();
            Dialog2.show();
            new AchatsJourTask().execute();
            new ProduitMoisTask().execute();*/
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


    private class MergedResponse{
        public List<AchatProduitMois> achatProduitMois;
        public List<ProduitMois> produitsMois;

        public List<AchatProduitMois> getAchatProduitMois() {
            return achatProduitMois;
        }

        public void setAchatProduitMois(List<AchatProduitMois> achatProduitMois) {
            this.achatProduitMois = achatProduitMois;
        }

        public List<ProduitMois> getProduitsMois() {
            return produitsMois;
        }

        public void setProduitsMois(List<ProduitMois> produitsMois) {
            this.produitsMois = produitsMois;
        }

        public MergedResponse() {
        }

        public MergedResponse(List<AchatProduitMois> achatProduitMois, List<ProduitMois> produitsMois) {
            this.achatProduitMois = achatProduitMois;
            this.produitsMois = produitsMois;
        }

        @Override
        public String toString() {
            return "MergedResponse{" +
                    "achatProduitMois=" + achatProduitMois +
                    ", produitsMois=" + produitsMois +
                    '}';
        }
    }

    private void getVentesCircuitData(){
        spinner.startAnimation();
        spinner.setIsVisible(true);
        Retrofit retrofit = RetrofitBuilder.getRetrofit(Constants.API_BASE_URL, accessToken);
        WebService service = retrofit.create(WebService.class);
        Observable.zip(service.getProduitsAchatsMoisCircuit(circuit), service.getHistoAchatsMoisCircuit(circuit), new BiFunction<List<ProduitMois>, List<AchatProduitMois>, MergedResponse>() {
            int i;
            @Override
            public MergedResponse apply(List<ProduitMois> produitData, List<AchatProduitMois> achatData) throws Exception {
                // Log.i("OBJECTIF", objectifData.toString());
                return new MergedResponse(achatData, produitData);
            }
        }).subscribe(new Observer<MergedResponse>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(MergedResponse mergedResponse) {
                //Log.i("RESULT", mergedResponse.toString());
                produitsMois = mergedResponse.getProduitsMois();
                achatProduitMois = mergedResponse.getAchatProduitMois();
                // test();

            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof SocketTimeoutException) {
                    Snackbar.make(parent_view, "Erreur connexion, essayer ultérieurement", Snackbar.LENGTH_LONG).show();
                    spinner.stopAnimation();
                    spinner.setIsVisible(false);
                }
            }

            @Override
            public void onComplete() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Calendar cal = Calendar.getInstance();
                        int jour = cal.get(Calendar.DAY_OF_MONTH);
                        achatMoisData = new AchatMoisData[jour];
                        for(int i = 0; i < jour; i++){
                            achatMoisData[i] = new AchatMoisData();
                        }

                        for (AchatProduitMois achat: achatProduitMois) {
                            casierTotal += achat.getQuantite();
                            hectoTotal += achat.getHecto();
                            if(achat.getFamille().equalsIgnoreCase("BIERE")){
                                achatMoisData[achat.getJour() - 1].addBi(achat.getQuantite());
                                casierTotalBi += achat.getQuantite();
                                hectoTotalBi += achat.getHecto();
                            }

                            if(achat.getFamille().equalsIgnoreCase("BG")){
                                achatMoisData[achat.getJour() - 1].addBg(achat.getQuantite());
                                casierTotalBg += achat.getQuantite();
                                hectoTotalBg += achat.getHecto();
                            }

                            if(achat.getFamille().equalsIgnoreCase("PET")){
                                achatMoisData[achat.getJour() - 1].addPet(achat.getQuantite());
                                casierTotalPet += achat.getQuantite();
                                hectoTotalPet += achat.getHecto();
                            }
                            achatMoisData[achat.getJour() - 1].addCA(achat.getMontant());
                        }

                        casierTotalTvw.setText(casierTotal + " CS");
                        casierTotalBiTvw.setText(casierTotalBi + " CS");
                        casierTotalBgTvw.setText(casierTotalBg + " CS");
                        casierTotalPetTvw.setText(casierTotalPet + "PK");

                        hectoTotalTvw.setText(String.format("%.0f", hectoTotal) + " Hl");
                        hectoTotalBiTvw.setText(String.format("%.0f",hectoTotalBi) + " Hl");
                        hectoTotalBgTvw.setText(String.format("%.0f", hectoTotalBg) + " Hl");
                        hectoTotalPetTvw.setText(String.format("%.0f", hectoTotalPet) + "Hl");

                        achatMoisDataAdapter = new AchatMoisDataAdapter(getActivity(), Arrays.asList(achatMoisData));
                        listVw.setAdapter(achatMoisDataAdapter);
                        Helper.getListViewSize(listVw);

                        produitsAdapter = new ProduitMoisCircuitAdapter(getActivity(), produitsMois);
                        produitsVw.setAdapter(produitsAdapter);
                        Helper.getListViewSize(produitsVw);
                        spinner.stopAnimation();
                        spinner.setIsVisible(false);
                    }
                });
            }
        });


    }

    private void logPage() {
        Retrofit retrofit = RetrofitBuilder.getRetrofit(Constants.API_BASE_URL, accessToken);
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
