package com.royken.bracongo.bracongosc.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.j256.ormlite.dao.Dao;
import com.royken.bracongo.bracongosc.R;
import com.royken.bracongo.bracongosc.adapter.AchatMoisDataAdapter;
import com.royken.bracongo.bracongosc.database.DatabaseHelper;
import com.royken.bracongo.bracongosc.entities.AchatMoisData;
import com.royken.bracongo.bracongosc.entities.AchatProduitMois;
import com.royken.bracongo.bracongosc.entities.Client;
import com.royken.bracongo.bracongosc.network.RetrofitBuilder;
import com.royken.bracongo.bracongosc.network.WebService;
import com.royken.bracongo.bracongosc.util.Helper;

import java.sql.SQLException;
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
 * {@link HistoAchatsAnneeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HistoAchatsAnneeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoAchatsAnneeFragment extends Fragment {
    public static final String PREFS_NAME = "com.bracongo.bracongoSCFile";
    private DatabaseHelper databaseHelper = null;
    private static final String ARG_CLIENTID = "idClient";
    private int idClient;
    private ProgressDialog Dialog1 ;
    private ProgressDialog Dialog2;
    private ListView list;
    Dao<Client, Integer> clientsDao;
    private Client client;
    private List<AchatProduitMois> achatProduitMois;
    private AchatMoisData[] achatMoisData;
    private AchatMoisDataAdapter achatMoisDataAdapter;

    private OnFragmentInteractionListener mListener;

    private TextView title;

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
        try {
            title.setText("HISTO ACHATS DE L'ANNEE");
            clientsDao = getHelper().getClientDao();
            client = clientsDao.queryForId(idClient);
            Dialog1 = new ProgressDialog(getActivity());
            Dialog2 = new ProgressDialog(getActivity());
            Dialog1.setMessage("Récupération des informations...");
            Dialog1.show();
            new AchatsMoisTask().execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_histo_achats_annee, container, false);
        list = (ListView) rootView.findViewById(R.id.listAchats);
        AppBarLayout bar = (AppBarLayout)getActivity().findViewById(R.id.appbar);
        title = (TextView) bar.findViewById(R.id.title);

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

    private class AchatsMoisTask extends AsyncTask<String, Void, Void> {
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
            Retrofit retrofit = RetrofitBuilder.getRetrofit("https://api.bracongo-cd.com:8443");
            WebService service = retrofit.create(WebService.class);
            Call<List<AchatProduitMois>> call = service.getHistoAchatsAnnee(client.getNumero().trim(),getIntFromClient(client.getNumero().trim())+"");
            call.enqueue(new Callback<List<AchatProduitMois>>() {
                @Override
                public void onResponse(Call<List<AchatProduitMois>> call, Response<List<AchatProduitMois>> response) {
                    Log.i("Result....", response.toString());
                    achatProduitMois = response.body();
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

    private DatabaseHelper getHelper() {
        if (databaseHelper == null) {
            //databaseHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);
            databaseHelper = new DatabaseHelper(getActivity());
        }
        return databaseHelper;
    }

    private static long getIntFromClient(String clientNumber){
        long hash = 0;
        for(int i = 0; i < clientNumber.length(); i ++){
            hash += clientNumber.charAt(i) * (i+1);
        }
        return hash;
    }
}
