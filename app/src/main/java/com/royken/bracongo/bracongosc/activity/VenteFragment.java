package com.royken.bracongo.bracongosc.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.pavlospt.CircleView;
import com.j256.ormlite.dao.Dao;
import com.royken.bracongo.bracongosc.R;
import com.royken.bracongo.bracongosc.adapter.VenteInfosAdapter;
import com.royken.bracongo.bracongosc.database.DatabaseHelper;
import com.royken.bracongo.bracongosc.entities.Client;
import com.royken.bracongo.bracongosc.entities.ClientReponse;
import com.royken.bracongo.bracongosc.entities.VenteReponse;
import com.royken.bracongo.bracongosc.entities.Ventes;
import com.royken.bracongo.bracongosc.entities.VentesInfos;
import com.royken.bracongo.bracongosc.network.RetrofitBuilder;
import com.royken.bracongo.bracongosc.network.WebService;
import com.royken.bracongo.bracongosc.util.Helper;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link VenteFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link VenteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VenteFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    public static final String PREFS_NAME = "com.bracongo.bracongoSCFile";

    final String[] mois = new String[] { "JAN", "FEV", "MARS", "AVR","MAI","JUN","JUI","AOU","SEP","OCT","NOV","DEV" };

    SharedPreferences settings ;
    SharedPreferences.Editor editor;
    private boolean loadData;

    private DatabaseHelper databaseHelper = null;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_CLIENTID = "idClient";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private int idClient;
    String compte;

    Dao<Client, Integer> clientsDao;
    private Client client;

    private OnFragmentInteractionListener mListener;

    private List<VentesInfos> ventesMois;

    private List<VentesInfos> ventesJours;

    private Date debut;

    private Date fin;

    private double remise;

    private double chiffre;

    LineChart jourChart;

    LineChart moisChart;

    CircleView remiseView;

    Ventes result;

    private ProgressDialog Dialog1 ;

    private ListView list;
    private ListView list1;

    private VenteInfosAdapter infosAdapter;
    private VenteInfosAdapter infosAdapter1;

    public VenteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment VenteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VenteFragment newInstance(int param1) {
        VenteFragment fragment = new VenteFragment();
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
            clientsDao = getHelper().getClientDao();
            Log.i("IDCLIENT", idClient+"");
            client = clientsDao.queryForId(idClient);
            Dialog1 = new ProgressDialog(getActivity());
            Dialog1.setMessage("Récupération des informations...");
            Dialog1.show();
            new VentesTask().execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Toast.makeText(getActivity(),idClient+" received",Toast.LENGTH_LONG).show();
    }
        @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_vente, container, false);
            jourChart = (LineChart) rootView.findViewById(R.id.chartJours);
            moisChart = (LineChart)rootView.findViewById(R.id.chartMois);
            remiseView  = (CircleView) rootView.findViewById(R.id.remise);
            list = (ListView) rootView.findViewById(R.id.list);
            list1 = (ListView) rootView.findViewById(R.id.listJ);
            Log.i("IDCLIENT 22", idClient+"");

        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
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

    private class VentesTask extends AsyncTask<String, Void, Void> {
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
            Call<VenteReponse> call = service.getVentes(client.getNumero().trim(),getIntFromClient(client.getNumero().trim())+"");
            call.enqueue(new Callback<VenteReponse>() {
                @Override
                public void onResponse(Call<VenteReponse> call, Response<VenteReponse> response) {
                    Log.i("Result....", response.toString());
                    VenteReponse rep = response.body();
                    result = rep.getVentes();
                    ventesMois = result.getVentesMois();
                    ventesJours = result.getVentesJours();
                    remiseView.setTitleText(result.getRemise() + " FC");
                    remiseView.setSubtitleText("C.A : " + (long)result.getChiffreAffaire() + " FC");
                    List<Entry> bieres = new ArrayList<Entry>();
                    List<Entry> bg = new ArrayList<Entry>();
                    List<Entry> pet = new ArrayList<Entry>();

                    for (VentesInfos data : ventesMois) {
                        // turn your data into Entry objects
                        bieres.add(new Entry(Float.valueOf(data.getJour()+""), Float.valueOf(data.getQuantiteBiere()+"")));
                        bg.add(new Entry(Float.valueOf(data.getJour()+""), Float.valueOf(data.getQuantiteBg()+"")));
                        pet.add(new Entry(Float.valueOf(data.getJour()+""), Float.valueOf(data.getQuantitePet()+"")));
                    }
                    LineDataSet dataSetBiM = new LineDataSet(bieres, "BI"); // add entries to dataset
                    dataSetBiM.setColor(Color.parseColor("#26C6DA"));
                    dataSetBiM.setAxisDependency(YAxis.AxisDependency.LEFT);
                    LineDataSet dataSetBgM = new LineDataSet(bg, "BG"); // add entries to dataset
                    dataSetBgM.setColor(Color.parseColor("#FCE4EC"));
                    dataSetBgM.setAxisDependency(YAxis.AxisDependency.LEFT);
                    LineDataSet dataSetPetM = new LineDataSet(pet, "PET"); // add entries to dataset
                    dataSetPetM.setColor(Color.parseColor("#EF6C00"));
                    dataSetPetM.setAxisDependency(YAxis.AxisDependency.LEFT);

                    List<ILineDataSet> dataSetsM = new ArrayList<ILineDataSet>();
                    dataSetsM.add(dataSetBiM);
                    dataSetsM.add(dataSetBgM);
                    dataSetsM.add(dataSetPetM);

                    LineData dataM = new LineData(dataSetsM);
                    moisChart.setData(dataM);
                    IAxisValueFormatter formatter = new IAxisValueFormatter() {

                        @Override
                        public String getFormattedValue(float value, AxisBase axis) {
                            return mois[(int) value-1];
                        }

                    };
                    XAxis xAxis = moisChart.getXAxis();
                    xAxis.setGranularity(1f); // minimum axis-step (interval) is 1
                    xAxis.setValueFormatter(formatter);
                    moisChart.invalidate();
                    infosAdapter = new VenteInfosAdapter(getActivity(),ventesMois,true);
                    list.setAdapter(infosAdapter);
                    Helper.getListViewSize(list);
                    /* FIN MOIS*/


                    /* DEBUT JOUR*/
                    List<Entry> bieresJ = new ArrayList<Entry>();
                    List<Entry> bgJ = new ArrayList<Entry>();
                    List<Entry> petJ = new ArrayList<Entry>();
                    for (VentesInfos data : ventesJours) {
                        // turn your data into Entry objects
                        bieresJ.add(new Entry(Float.valueOf(data.getJour()+""), Float.valueOf(data.getQuantiteBiere()+"")));
                        bgJ.add(new Entry(Float.valueOf(data.getJour()+""), Float.valueOf(data.getQuantiteBg()+"")));
                        petJ.add(new Entry(Float.valueOf(data.getJour()+""), Float.valueOf(data.getQuantitePet()+"")));
                    }

                    LineDataSet dataSetBiJ = new LineDataSet(bieresJ, "BI"); // add entries to dataset
                    dataSetBiJ.setColor(Color.parseColor("#26C6DA"));
                    dataSetBiJ.setAxisDependency(YAxis.AxisDependency.LEFT);
                    LineDataSet dataSetBgJ = new LineDataSet(bgJ, "BG"); // add entries to dataset
                    dataSetBgJ.setColor(Color.parseColor("#FCE4EC"));
                    dataSetBgJ.setAxisDependency(YAxis.AxisDependency.LEFT);
                    LineDataSet dataSetPetJ = new LineDataSet(petJ, "PET"); // add entries to dataset
                    dataSetPetJ.setColor(Color.parseColor("#EF6C00"));
                    dataSetPetJ.setAxisDependency(YAxis.AxisDependency.LEFT);

                    List<ILineDataSet> dataSetsJ = new ArrayList<ILineDataSet>();
                    dataSetsJ.add(dataSetBiJ);
                    dataSetsJ.add(dataSetBgJ);
                    dataSetsJ.add(dataSetPetJ);

                    LineData dataJ = new LineData(dataSetsJ);
                    jourChart.setData(dataJ);
                    XAxis xAxis2 = jourChart.getXAxis();
                    xAxis2.setGranularity(1f);
                    jourChart.invalidate();

                    infosAdapter1 = new VenteInfosAdapter(getActivity(),ventesJours,false);
                    list1.setAdapter(infosAdapter1);
                    Helper.getListViewSize(list1);

                    /* FIN JOUR*/
                    Dialog1.dismiss();


                }
                @Override
                public void onFailure(Call<VenteReponse> call, Throwable t) {
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
