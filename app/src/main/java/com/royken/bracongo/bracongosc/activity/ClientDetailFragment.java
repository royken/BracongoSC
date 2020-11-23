package com.royken.bracongo.bracongosc.activity;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.j256.ormlite.dao.Dao;
import com.royken.bracongo.bracongosc.R;
import com.royken.bracongo.bracongosc.database.DatabaseHelper;
import com.royken.bracongo.bracongosc.entities.Client;
import com.royken.bracongo.bracongosc.viewmodel.ClientViewModel;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ClientDetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ClientDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ClientDetailFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_CLIENTID = "idClient";

    public static final String PREFS_NAME = "com.bracongo.bracongoSCFile";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private int idClient;

    Client client;

    private TextView compteTvw;
    private TextView nomTvw;
    private TextView quartierTvw;
    private TextView communeTvw;
    private TextView proprioTvw;
    private TextView adresseTvw;
    private TextView telephoneTvw;
    private TextView typeTvw;
    private TextView regimeTvw;
    private TextView categorieTvw;
    private TextView decorationTvw;
    private TextView nocontratTvw;
    private TextView datecreationTvw;
    private TextView dernierAchatTvw;
    private TextView emballagesTvw;
    private TextView consEmballagesTvw;


    private FloatingActionButton remiseBtn;
    private FloatingActionButton achatsMoisBtn;
    private FloatingActionButton achatsAnneeBtn;
    private FloatingActionButton itineraireBtn;
    private FloatingActionButton materielBtn;
    private FloatingActionButton plainteBtn;


    private OnFragmentInteractionListener mListener;

    private ClientViewModel clientViewModel;

    private TextView title;

    public ClientDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param idClient le numéro de compte du client.
     * @return A new instance of fragment ClientDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ClientDetailFragment newInstance(int idClient) {
        ClientDetailFragment fragment = new ClientDetailFragment();
        Bundle args = new Bundle();
        //  args.putString(ARG_PARAM1, param1);
        // args.putString(ARG_PARAM2, param2);
        args.putInt(ARG_CLIENTID,idClient);
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       // AppBarLayout bar = (AppBarLayout)getActivity().findViewById(R.id.appbar);
       // title = (TextView) bar.findViewById(R.id.title);
        TextView title = (TextView) getActivity().findViewById(R.id.title);
        title.setText("Détails client");
        View rootView = inflater.inflate(R.layout.fragment_client_detail, container, false);
        nomTvw = (TextView) rootView.findViewById(R.id.nomValue);
        compteTvw = (TextView) rootView.findViewById(R.id.compteValue);
        quartierTvw = (TextView) rootView.findViewById(R.id.quartierValue);
        communeTvw = (TextView) rootView.findViewById(R.id.communeValue);
        proprioTvw = (TextView) rootView.findViewById(R.id.proprioValue);
        adresseTvw = (TextView) rootView.findViewById(R.id.adresseValue);
        telephoneTvw = (TextView) rootView.findViewById(R.id.telephoneValue);
        typeTvw = (TextView) rootView.findViewById(R.id.typeValue);
        regimeTvw = (TextView) rootView.findViewById(R.id.regimeValue);
        categorieTvw = (TextView) rootView.findViewById(R.id.categorieValue);
        //decorationTvw.setText(client.getDec);
        nocontratTvw = (TextView) rootView.findViewById(R.id.nocontratValue);
        datecreationTvw = (TextView) rootView.findViewById(R.id.datecreationValue);
        dernierAchatTvw = (TextView) rootView.findViewById(R.id.dernierachatValue);
        emballagesTvw = (TextView) rootView.findViewById(R.id.emballagesValue);
        consEmballagesTvw = (TextView) rootView.findViewById(R.id.consValue);

        remiseBtn = (FloatingActionButton)rootView.findViewById(R.id.remiseBtn);
        achatsMoisBtn =(FloatingActionButton) rootView.findViewById(R.id.achatMoisBtn);
        achatsAnneeBtn = (FloatingActionButton)rootView.findViewById(R.id.achatAnneBtn);
        itineraireBtn = (FloatingActionButton) rootView.findViewById(R.id.itineraireBtn);
        materielBtn = (FloatingActionButton) rootView.findViewById(R.id.materielBtn);
        plainteBtn = (FloatingActionButton) rootView.findViewById(R.id.plainteBtn);

        remiseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = RemiseFragment.newInstance(idClient);
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.fragment,fragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
        achatsMoisBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = HistoAchatsMoisFragment.newInstance(idClient);
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.fragment,fragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        achatsAnneeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = HistoAchatsAnneeFragment.newInstance(idClient);
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.fragment,fragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        materielBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = MaterielFragment.newInstance(idClient);
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.fragment,fragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        plainteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = PlainteFragment.newInstance(idClient);
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.fragment,fragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        /*rootView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                Log.i(getTag(), "keyCode: " + keyCode);
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    getActivity().getActionBar().show();
                    Log.i(getTag(), "onKey Back listener is working!!!");
                    getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    // String cameback="CameBack";
                    Intent i = new Intent(getActivity(), ListClientActivity.class);
                    // i.putExtra("Comingback", cameback);
                    startActivity(i);
                    return true;
                } else {
                    return false;
                }
            }
        });*/
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//            title.setText("DETAILS CLIENT");
            clientViewModel = new ViewModelProvider(this).get(ClientViewModel.class);
            Log.i("CLIENTID", idClient+"");
            clientViewModel.getById(idClient).observe(getViewLifecycleOwner(), client_ -> {
                client = client_;
                Log.i("CLIENT : ", client.toString());
                nomTvw.setText(client.getNom());
                compteTvw.setText(client.getNumero());
                quartierTvw.setText(client.getQuartier());
                communeTvw.setText(client.getCommune());
                proprioTvw.setText(client.getNomProprietaire());
                adresseTvw.setText(client.getAdresse());
                telephoneTvw.setText(client.getTelephone());
                typeTvw.setText(client.getType());
                regimeTvw.setText(client.getRegime());
                categorieTvw.setText(client.getCategorie());
                //decorationTvw.setText(client.getDec);
                nocontratTvw.setText(client.getNumeroContrat());
                datecreationTvw.setText(getDateString(client.getDateCreation()));
                dernierAchatTvw.setText(getDateString(client.getDernierAchat()));
                consEmballagesTvw.setText(client.getConsignationEmballages()+"");
                emballagesTvw.setText(client.getEmballage()+"");
            });
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


    private String getDateString(Date date){
        Calendar gc = new GregorianCalendar();
        gc.setTime(date);
        String result = gc.get(Calendar.DAY_OF_MONTH)+"/"+(gc.get(Calendar.MONTH)+1)+"/"+gc.get(Calendar.YEAR);
        return result;
    }
}
