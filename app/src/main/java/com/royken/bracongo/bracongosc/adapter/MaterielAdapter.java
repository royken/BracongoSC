package com.royken.bracongo.bracongosc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.royken.bracongo.bracongosc.R;
import com.royken.bracongo.bracongosc.entities.Materiel;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class MaterielAdapter extends BaseAdapter {

    List<Materiel> materiels;
    private Context mContext;
    private LayoutInflater mInflater;

    public MaterielAdapter(List<Materiel> materiels, Context mContext) {
        this.materiels = materiels;
        this.mContext = mContext;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return materiels.size();
    }

    @Override
    public Object getItem(int position) {
        return materiels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return materiels.get(position).getQuantite();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout layout;
        if (convertView == null) {
            layout = (LinearLayout) mInflater.inflate(R.layout.materiel_item, parent, false);
        } else {
            layout = (LinearLayout) convertView;
        }
        TextView tv_materiel = (TextView)layout.findViewById(R.id.nomMateriel);
        TextView tv_quantite = (TextView)layout.findViewById(R.id.quantite);
        TextView tv_caracteristique = (TextView) layout.findViewById(R.id.caracteristique);
        TextView tv_etat = (TextView) layout.findViewById(R.id.etat);
        TextView tv_dateAffectation = (TextView) layout.findViewById(R.id.dateAffectation);
        TextView tv_motif = (TextView) layout.findViewById(R.id.motifAffectation);

        tv_materiel.setText(materiels.get(position).getLibelle());
        tv_quantite.setText(materiels.get(position).getQuantite() + "");
        tv_caracteristique.setText(materiels.get(position).getCaracteristique() == null ? "" : materiels.get(position).getCaracteristique());
        tv_etat.setText(materiels.get(position).getEtat() == null ? "" : materiels.get(position).getEtat());
        tv_dateAffectation.setText(getDateString(materiels.get(position).getDateAffectation()));
        tv_motif.setText(materiels.get(position).getMotif());

        tv_materiel.setTag(position);
        tv_quantite.setTag(position);
        tv_caracteristique.setTag(position);
        tv_etat.setTag(position);
        tv_dateAffectation.setTag(position);
        tv_motif.setTag(position);
        return layout;
    }

    private String getDateString(Date date){
        Calendar gc = new GregorianCalendar();
        gc.setTime(date);
        String result = gc.get(Calendar.DAY_OF_MONTH)+"/"+(gc.get(Calendar.MONTH)+1)+"/"+gc.get(Calendar.YEAR);
        return result;
    }
}
