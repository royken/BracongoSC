package com.royken.bracongo.bracongosc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.royken.bracongo.bracongosc.R;

public class CustomClientInfoWindowAdater implements GoogleMap.InfoWindowAdapter {

    private Context context;

    private LayoutInflater mInflater;

    private String nomClient;

    private String categorie;

    private String regime;

    private String type;

    private String numeroCompte;

    private String adresse;

    public CustomClientInfoWindowAdater(Context context,String nomClient, String categorie, String regime, String type, String numeroCompte, String adresse) {
        this.context = context;
        this.nomClient = nomClient;
        this.categorie = categorie;
        this.regime = regime;
        this.type = type;
        this.numeroCompte = numeroCompte;
        this.adresse = adresse;

        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        //View view = context.getLayoutInflater().inflate(R.layout.custominfowindow, null);
        View view = mInflater.inflate(R.layout.client_custom_position_window, null, false);

        TextView nomTvw = (TextView) view.findViewById(R.id.client);
        TextView compteTvw = (TextView) view.findViewById(R.id.compte);
        TextView categorieTvw = (TextView) view.findViewById(R.id.categorie);
        TextView regimeTvw = (TextView) view.findViewById(R.id.regime);
        TextView typeTvw = (TextView) view.findViewById(R.id.type);
        TextView adresseTvw = (TextView) view.findViewById(R.id.adresse);

        //tvTitle.setText(marker.getTitle());
        //tvSubTitle.setText(marker.getSnippet());
        nomTvw.setText(nomClient);
        compteTvw.setText(numeroCompte);
        categorieTvw.setText(categorie);
        adresseTvw.setText(adresse);
        regimeTvw.setText(regime);
        typeTvw.setText(type);

        return view;
    }
}
