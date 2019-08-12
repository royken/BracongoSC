package com.royken.bracongo.bracongosc.activity;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;


import com.j256.ormlite.dao.Dao;
import com.royken.bracongo.bracongosc.R;
import com.royken.bracongo.bracongosc.adapter.PlainteAdapter;
import com.royken.bracongo.bracongosc.database.DatabaseHelper;
import com.royken.bracongo.bracongosc.entities.Client;
import com.royken.bracongo.bracongosc.entities.Plainte;
import com.royken.bracongo.bracongosc.network.RetrofitBuilder;
import com.royken.bracongo.bracongosc.network.WebService;
import java.sql.SQLException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PlainteFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PlainteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlainteFragment extends ListFragment {

    public static final String PREFS_NAME = "com.bracongo.bracongoSCFile";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_CLIENTID = "idClient";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private int idClient;

    private DatabaseHelper databaseHelper = null;


    private OnFragmentInteractionListener mListener;

    private PlainteAdapter plainteAdapter;
    private List<Plainte> plaintes;
    Dao<Client, Integer> clientsDao;
    private Client client;

    private ListView list;

    private ProgressDialog Dialog1 ;

    private TextView title;

    public PlainteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment PlainteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PlainteFragment newInstance(int param1) {
        PlainteFragment fragment = new PlainteFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_CLIENTID, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            title.setText("PLAINTES");
            clientsDao = getHelper().getClientDao();
            //Log.i("IDCLIENT", idClient+"");
            client = clientsDao.queryForId(idClient);
            Dialog1 = new ProgressDialog(getActivity());
            Dialog1.setMessage("Récupération des informations...");
            Dialog1.show();
            new PlaintesTask().execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idClient = getArguments().getInt(ARG_CLIENTID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_plainte, container, false);
        AppBarLayout bar = (AppBarLayout)getActivity().findViewById(R.id.appbar);
        title = (TextView) bar.findViewById(R.id.title);
        //list = (ListView) rootView.findViewById(R.id.list);
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

    private class PlaintesTask extends AsyncTask<String, Void, Void> {
        // Required initialization

        private ProgressDialog Dialog = new ProgressDialog(getActivity());
        private boolean data;


        protected void onPreExecute() {
           // Dialog.setMessage("Récupération des informations...");
          //  Dialog.show();
        }

        // Call after onPreExecute method
        protected Void doInBackground(String... urls) {
            //Retrofit retrofit = RetrofitBuilder.getRetrofit("https://api.bracongo-cd.com:8443");
            Retrofit retrofit = RetrofitBuilder.getRetrofit("https://api.bracongo-cd.com:8443");
            WebService service = retrofit.create(WebService.class);
            Call<List<Plainte>> call = service.getPlaintesClientMaryse(client.getNumero().trim());
            call.enqueue(new Callback<List<Plainte>>() {
                @Override
                public void onResponse(Call<List<Plainte>> call, Response<List<Plainte>> response) {
                    Log.i("Result....", response.toString());
                    plaintes  = response.body();
                    plainteAdapter = new PlainteAdapter(getActivity(),plaintes);
                    setListAdapter(plainteAdapter);
                    Dialog1.dismiss();
                }
                @Override
                public void onFailure(Call<List<Plainte>> call, Throwable t) {
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
