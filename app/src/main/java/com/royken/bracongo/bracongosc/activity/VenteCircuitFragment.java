package com.royken.bracongo.bracongosc.activity;

import android.app.ProgressDialog;
import android.content.Context;
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

import com.google.android.material.appbar.AppBarLayout;
import com.royken.bracongo.bracongosc.R;
import com.royken.bracongo.bracongosc.adapter.AchatMoisDataAdapter;
import com.royken.bracongo.bracongosc.adapter.ProduitMoisCircuitAdapter;
import com.royken.bracongo.bracongosc.database.DatabaseHelper;
import com.royken.bracongo.bracongosc.entities.AchatMoisData;
import com.royken.bracongo.bracongosc.entities.AchatProduitMois;
import com.royken.bracongo.bracongosc.entities.ProduitMois;
import com.royken.bracongo.bracongosc.network.RetrofitBuilder;
import com.royken.bracongo.bracongosc.network.WebService;
import com.royken.bracongo.bracongosc.util.Helper;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

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
    private DatabaseHelper databaseHelper = null;
    private static final String ARG_CLIENTID = "idClient";
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
        //AppBarLayout bar = (AppBarLayout)getActivity().findViewById(R.id.appbar);
        //title = (TextView) bar.findViewById(R.id.title);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
            title.setText("HISTO ACHATS DU MOIS : " + circuit );
            Dialog1 = new ProgressDialog(getActivity());
            Dialog2 = new ProgressDialog(getActivity());
            Dialog1.setMessage("Récupération des informations...");
            Dialog2.setMessage("Récupération des informations...");
            Dialog1.show();
            Dialog2.show();
            new AchatsJourTask().execute();
            new ProduitMoisTask().execute();
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

    private class AchatsJourTask extends AsyncTask<String, Void, Void> {
        // Required initialization

        private ProgressDialog Dialog = new ProgressDialog(getActivity());
        private boolean data;


        protected void onPreExecute() {
            // Dialog.setMessage("Récupération des informations...");
            // Dialog.show();
            casierTotal = 0;
            casierTotalBg = 0;
            casierTotalBi = 0;
            casierTotalPet = 0;

            hectoTotal = 0;
            hectoTotalBg = 0;
            hectoTotalBi = 0;
            hectoTotalPet = 0;
        }

        // Call after onPreExecute method
        protected Void doInBackground(String... urls) {
            //Retrofit retrofit = RetrofitBuilder.getRetrofit("https://api.bracongo-cd.com:8443");
            Retrofit retrofit = RetrofitBuilder.getRetrofit("https://api.bracongo-cd.com:8443","");
            WebService service = retrofit.create(WebService.class);
            Call<List<AchatProduitMois>> call = service.getHistoAchatsMoisCircuit(circuit);
            call.enqueue(new Callback<List<AchatProduitMois>>() {
                @Override
                public void onResponse(Call<List<AchatProduitMois>> call, Response<List<AchatProduitMois>> response) {
                    Log.i("Result....", response.toString());
                    achatProduitMois = response.body();
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
                    /* FIN MOIS*/

                    Dialog1.dismiss();


                }
                @Override
                public void onFailure(Call<List<AchatProduitMois>> call, Throwable t) {
                    Log.i("Error...", t.toString());
                }
            });
            return null;
        }

        protected void onPostExecute(Void unused) {
            Dialog.dismiss();
        }
    }

    private class ProduitMoisTask extends AsyncTask<String, Void, Void> {
        // Required initialization

        private ProgressDialog Dialog = new ProgressDialog(getActivity());
        private boolean data;


        protected void onPreExecute() {
            // Dialog.setMessage("Récupération des informations...");
            // Dialog.show();
        }

        // Call after onPreExecute method
        protected Void doInBackground(String... urls) {
            //Retrofit retrofit = RetrofitBuilder.getRetrofit("https://api.bracongo-cd.com:8443");
            Retrofit retrofit = RetrofitBuilder.getRetrofit("https://api.bracongo-cd.com:8443","");
            WebService service = retrofit.create(WebService.class);
            Call<List<ProduitMois>> call = service.getProduitsAchatsMoisCircuit(circuit);
            call.enqueue(new Callback<List<ProduitMois>>() {
                @Override
                public void onResponse(Call<List<ProduitMois>> call, Response<List<ProduitMois>> response) {
                    Log.i("Result....", response.toString());
                    produitsMois = response.body();
                    produitsAdapter = new ProduitMoisCircuitAdapter(getActivity(), produitsMois);
                    produitsVw.setAdapter(produitsAdapter);
                    Helper.getListViewSize(produitsVw);
                    /* FIN MOIS*/

                    Dialog2.dismiss();


                }
                @Override
                public void onFailure(Call<List<ProduitMois>> call, Throwable t) {
                    Log.i("Error...", t.toString());
                }
            });
            return null;
        }

        protected void onPostExecute(Void unused) {
            Dialog.dismiss();
        }
    }
}
