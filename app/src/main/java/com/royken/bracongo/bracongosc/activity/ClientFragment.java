package com.royken.bracongo.bracongosc.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.j256.ormlite.dao.Dao;
import com.royken.bracongo.bracongosc.R;
import com.royken.bracongo.bracongosc.database.DatabaseHelper;
import com.royken.bracongo.bracongosc.entities.Client;

import java.sql.SQLException;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ClientFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ClientFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ClientFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_CLIENTID = "idClient";

    private DatabaseHelper databaseHelper = null;
    public static final String PREFS_NAME = "com.bracongo.bracongoSCFile";
    SharedPreferences settings ;
    Dao<Client, Integer> clientsDao;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private int idClient;

    Client client;

    private OnFragmentInteractionListener mListener;

    TextView nomTvw;
    TextView numeroTvw;
    TextView adresseTvw;
    TextView telTvw;

    public ClientFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ClientFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ClientFragment newInstance(int idClient) {
        ClientFragment fragment = new ClientFragment();
        Bundle args = new Bundle();
      //  args.putString(ARG_PARAM1, param1);
       // args.putString(ARG_PARAM2, param2);
        args.putInt(ARG_CLIENTID,idClient);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            clientsDao = getHelper().getClientDao();
            client = clientsDao.queryForId(idClient);
            nomTvw.setText(client.getNom());
            numeroTvw.setText(client.getNumero());
            adresseTvw.setText(client.getAdresse());
            telTvw.setText(client.getTelephone());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //mParam1 = getArguments().getString(ARG_PARAM1);
            //mParam2 = getArguments().getString(ARG_PARAM2);
            idClient = getArguments().getInt(ARG_CLIENTID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_client, container, false);
        nomTvw = (TextView) rootView.findViewById(R.id.nomClient);
        numeroTvw = (TextView) rootView.findViewById(R.id.numero);
        adresseTvw = (TextView) rootView.findViewById(R.id.adresse);
        telTvw = (TextView) rootView.findViewById(R.id.tel);
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

    private DatabaseHelper getHelper() {
        if (databaseHelper == null) {
            //databaseHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);
            databaseHelper = new DatabaseHelper(getActivity());
        }
        return databaseHelper;
    }
}
