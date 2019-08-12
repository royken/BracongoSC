package com.royken.bracongo.bracongosc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.royken.bracongo.bracongosc.R;
import com.royken.bracongo.bracongosc.entities.Client;
import com.royken.bracongo.bracongosc.entities.Plainte;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by vr.kenfack on 05/09/2017.
 */

public class PlainteAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;

    private List<Plainte> plaintes;

    public PlainteAdapter(Context mContext, List<Plainte> plaintes) {
        this.mContext = mContext;
        this.plaintes = plaintes;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return plaintes.size();
    }

    @Override
    public Object getItem(int position) {
        return plaintes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout layout;
        if (convertView == null) {
            layout = (LinearLayout) mInflater.inflate(R.layout.plainte_item, parent, false);
        } else {
            layout = (LinearLayout) convertView;
        }
         TextView tv_type = (TextView)layout.findViewById(R.id.type);
        TextView tv_description = (TextView) layout.findViewById(R.id.description);
        TextView tv_date = (TextView) layout.findViewById(R.id.dateDepot);
        TextView tv_statut = (TextView) layout.findViewById(R.id.statut);
        TextView tv_reponse1 = (TextView) layout.findViewById(R.id.rep1);
        TextView tv_dateRep1 = (TextView) layout.findViewById(R.id.dateRep1);
        TextView tv_reponse2 = (TextView) layout.findViewById(R.id.rep2);
        TextView tv_dateRep2 = (TextView) layout.findViewById(R.id.dateRep2);
        TextView tv_reponse3 = (TextView) layout.findViewById(R.id.rep3);
        TextView tv_dateRep3 = (TextView) layout.findViewById(R.id.dateRep3);


        tv_type.setText(plaintes.get(position).getType());
        tv_description.setText(plaintes.get(position).getDescription());
        tv_date.setText(plaintes.get(position).getDateDepot() != null ? getDateString(plaintes.get(position).getDateDepot()) : "");
        tv_statut.setText(plaintes.get(position).getStatut());
        tv_reponse1.setText(plaintes.get(position).getReponse());
        tv_dateRep1.setText(plaintes.get(position).getDateReponse() != null ? getDateString(plaintes.get(position).getDateReponse()):  "");
        tv_reponse2.setText(plaintes.get(position).getReponse2());
        tv_dateRep2.setText(plaintes.get(position).getDateReponse2() != null ? getDateString(plaintes.get(position).getDateReponse2()) : "");
        tv_reponse3.setText(plaintes.get(position).getReponse3());
        tv_dateRep3.setText(plaintes.get(position).getDateReponse3() != null ? getDateString(plaintes.get(position).getDateReponse3()) : "");

        tv_type.setTag(position);
        tv_description.setTag(position);
        tv_date.setTag(position);
        tv_statut.setTag(position);
        tv_reponse1.setTag(position);
        tv_dateRep1.setTag(position);
        tv_reponse2.setTag(position);
        tv_dateRep2.setTag(position);
        tv_reponse3.setTag(position);
        tv_dateRep3.setTag(position);
        return layout;
    }

    private String getDateString(Date date){
        Calendar gc = new GregorianCalendar();
        gc.setTime(date);
        String result = gc.get(Calendar.DAY_OF_MONTH)+"-"+(gc.get(Calendar.MONTH)+1)+"-"+gc.get(Calendar.YEAR);
        return result;
    }

    @Override
    public boolean isEmpty() {
        return plaintes.isEmpty();
    }
}
