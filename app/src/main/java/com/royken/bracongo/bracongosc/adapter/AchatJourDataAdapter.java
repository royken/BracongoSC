package com.royken.bracongo.bracongosc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.royken.bracongo.bracongosc.R;
import com.royken.bracongo.bracongosc.entities.AchatJourData;

import java.util.List;

public class AchatJourDataAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<AchatJourData> achatsData;

    public AchatJourDataAdapter(Context mContext, List<AchatJourData> achatsData) {
        this.mContext = mContext;
        this.achatsData = achatsData;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return achatsData.size();
    }

    @Override
    public Object getItem(int position) {
        return achatsData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return achatsData.get(position).getJour();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout layout;
        if (convertView == null) {
            layout = (LinearLayout) mInflater.inflate(R.layout.achat_jour_item, parent, false);
        } else {
            layout = (LinearLayout) convertView;
        }
        TextView tv_jour = (TextView)layout.findViewById(R.id.jour);
        TextView tv_bi = (TextView)layout.findViewById(R.id.bijour);
        TextView tv_bg = (TextView) layout.findViewById(R.id.bgjour);
        TextView tv_pet = (TextView) layout.findViewById(R.id.petjour);
        TextView tv_chiffreAffaire = (TextView) layout.findViewById(R.id.cajour);
        TextView tv_produit = (TextView)layout.findViewById(R.id.produits);

        tv_jour.setText((position + 1 )+"");
        tv_bi.setText(achatsData.get(position).getQteBi()+"");
        tv_bg.setText(achatsData.get(position).getQteBg()+"");
        tv_pet.setText(achatsData.get(position).getQtePet()+"");
        tv_chiffreAffaire.setText(String.format("%d",achatsData.get(position).getChiffreAffaire()));
        tv_produit.setText(achatsData.get(position).getProduits());

        tv_jour.setTag(position);
        tv_bi.setTag(position);
        tv_bg.setTag(position);
        tv_pet.setTag(position);
        tv_chiffreAffaire.setTag(position);
        tv_produit.setTag(position);
        return layout;
    }
}
