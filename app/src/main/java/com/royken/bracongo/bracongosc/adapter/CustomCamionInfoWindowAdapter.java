package com.royken.bracongo.bracongosc.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.royken.bracongo.bracongosc.R;

public class CustomCamionInfoWindowAdapter implements GoogleMap.InfoWindowAdapter{

    private Context context;

    private LayoutInflater mInflater;
    private String lat;
    private String lng;
    private String date;

    private String adresse;
    private String vitesse;
    private String circuit;
    private String ub;

    public CustomCamionInfoWindowAdapter(Context context,String ub, String lat, String lng, String date, String adresse, String vitesse, String circuit) {
        this.context = context;
        this.lat = lat;
        this.lng = lng;
        this.date = date;
        this.adresse = adresse;
        this.vitesse = vitesse;
        this.circuit = circuit;
        this.ub = ub;

        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        //View view = context.getLayoutInflater().inflate(R.layout.custominfowindow, null);
        View view = mInflater.inflate(R.layout.camion_custom_position_window, null, false);

        TextView dateTvw = (TextView) view.findViewById(R.id.date);
        TextView latTvw = (TextView) view.findViewById(R.id.lat);
        TextView longTvw = (TextView) view.findViewById(R.id.lng);
        TextView adresseTvw = (TextView) view.findViewById(R.id.adresse);
        TextView vitesseTvw = (TextView) view.findViewById(R.id.vitesse);
        TextView circuitTvw = (TextView) view.findViewById(R.id.circuit);
        TextView titre = (TextView) view.findViewById(R.id.ub);

        //tvTitle.setText(marker.getTitle());
        //tvSubTitle.setText(marker.getSnippet());
        dateTvw.setText(date);
        latTvw.setText(lat);
        longTvw.setText(lng);
        adresseTvw.setText(adresse);
        vitesseTvw.setText(vitesse);
        circuitTvw.setText(circuit);
        titre.setText(ub);

        return view;
    }
}
