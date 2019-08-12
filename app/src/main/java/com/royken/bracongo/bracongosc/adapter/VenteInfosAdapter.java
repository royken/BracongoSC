package com.royken.bracongo.bracongosc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.royken.bracongo.bracongosc.R;
import com.royken.bracongo.bracongosc.entities.Client;
import com.royken.bracongo.bracongosc.entities.VentesInfos;

import java.util.List;

/**
 * Created by vr.kenfack on 05/10/2017.
 */

public class VenteInfosAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<VentesInfos> infos;
    private boolean mois;

    public VenteInfosAdapter(Context mContext, List<VentesInfos> infos, boolean mois) {
        this.mContext = mContext;
        this.infos = infos;
        this.mois = mois;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return infos.size();
    }

    @Override
    public Object getItem(int position) {
        return infos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return infos.get(position).getJour();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout layout;
        if (convertView == null) {
            layout = (LinearLayout) mInflater.inflate(R.layout.vente_info_item, parent, false);
        } else {
            layout = (LinearLayout) convertView;
        }
        TextView tv_jour = (TextView)layout.findViewById(R.id.jour);
        TextView tv_qtBi = (TextView) layout.findViewById(R.id.qtBi);
        TextView tv_qtBg = (TextView) layout.findViewById(R.id.qtBg);
        TextView tv_qtPet = (TextView) layout.findViewById(R.id.qtPet);
        TextView tv_chiffreA = (TextView) layout.findViewById(R.id.chiffre);
        if(mois){
            tv_jour.setText( getMonthByNumber(infos.get(position).getJour()));
        }
        else {
            tv_jour.setText(infos.get(position).getJour()+"");
        }

        tv_qtBi.setText(infos.get(position).getQuantiteBiere()+"");
        tv_qtBg.setText(infos.get(position).getQuantiteBg()+"");
        tv_qtPet.setText(infos.get(position).getQuantitePet()+"");
        tv_chiffreA.setText((long)(infos.get(position).getChiffreBierre()+ infos.get(position).getChiffreBg()+ infos.get(position).getChiffrePet()) +" FC");

        tv_jour.setTag(position);
        tv_qtBi.setTag(position);
        tv_qtBg.setTag(position);
        tv_qtPet.setTag(position);
        tv_chiffreA.setTag(position);
        return layout;
    }

    private String getMonthByNumber( int number) {
        String  mois = "";
        switch (number) {
            case 1:
                mois = "Janvier";
                break;
            case 2:
                mois = "Février";
                break;
            case 3:
                mois = "Mars";
                break;
            case 4:
                mois = "Avril";
                break;
            case 5:
                mois = "Mai";
                break;
            case 6:
                mois = "Juin";
                break;
            case 7:
                mois = "Juillet";
                break;
            case 8:
                mois = "Août";
                break;
            case 9:
                mois = "Septembre";
                break;
            case 10:
                mois = "Octobre";
                break;
            case 11:
                mois = "Novembre";
                break;
            case 12:
                mois = "Décembre";
                break;
        }
        return mois;
    }
}
