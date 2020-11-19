package com.royken.bracongo.bracongosc.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
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

import com.google.android.material.appbar.AppBarLayout;
import com.j256.ormlite.dao.Dao;
import com.royken.bracongo.bracongosc.R;
import com.royken.bracongo.bracongosc.adapter.RemiseAdapter;
import com.royken.bracongo.bracongosc.database.DatabaseHelper;
import com.royken.bracongo.bracongosc.entities.Client;
import com.royken.bracongo.bracongosc.entities.RemiseInfo;
import com.royken.bracongo.bracongosc.network.RetrofitBuilder;
import com.royken.bracongo.bracongosc.network.WebService;
import com.royken.bracongo.bracongosc.util.Helper;

import java.sql.SQLException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RemiseFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RemiseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RemiseFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public static final String PREFS_NAME = "com.bracongo.bracongoSCFile";
    private DatabaseHelper databaseHelper = null;
    private static final String ARG_CLIENTID = "idClient";
    private int idClient;
    private ProgressDialog Dialog1 ;
    private ListView list;
    private RemiseAdapter remiseAdapter;
    List<RemiseInfo> remiseInfos;
    Dao<Client, Integer> clientsDao;
    private Client client;

    private OnFragmentInteractionListener mListener;

    private TextView title;

    public RemiseFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment RemiseFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RemiseFragment newInstance(int param1) {
        RemiseFragment fragment = new RemiseFragment();
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
            //title.setText("HISTO REMISES");
            clientsDao = getHelper().getClientDao();
            Log.i("IDCLIENT", idClient+"");
            client = clientsDao.queryForId(idClient);
            Dialog1 = new ProgressDialog(getActivity());
            Dialog1.setMessage("Récupération des informations...");
            Dialog1.show();
            new RemiseTask().execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Toast.makeText(getActivity(),idClient+" received",Toast.LENGTH_LONG).show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_remise, container, false);
        //AppBarLayout bar = (AppBarLayout)getActivity().findViewById(R.id.appbar);
        //title = (TextView) bar.findViewById(R.id.title);
        list = (ListView) rootView.findViewById(R.id.list);
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

    private class RemiseTask extends AsyncTask<String, Void, Void> {
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
            Retrofit retrofit = RetrofitBuilder.getRetrofit("https://api.bracongo-cd.com:8443", "");
            WebService service = retrofit.create(WebService.class);
            Call<List<RemiseInfo>> call = service.getHistoRemise(client.getNumero().trim(),getIntFromClient(client.getNumero().trim())+"");
            call.enqueue(new Callback<List<RemiseInfo>>() {
                @Override
                public void onResponse(Call<List<RemiseInfo>> call, Response<List<RemiseInfo>> response) {
                    Log.i("Result....", response.toString());
                    remiseInfos = response.body();
                    remiseAdapter = new RemiseAdapter(getActivity(), remiseInfos);
                    list.setAdapter(remiseAdapter);
                    Helper.getListViewSize(list);
                    /* FIN MOIS*/

                    Dialog1.dismiss();


                }
                @Override
                public void onFailure(Call<List<RemiseInfo>> call, Throwable t) {
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
