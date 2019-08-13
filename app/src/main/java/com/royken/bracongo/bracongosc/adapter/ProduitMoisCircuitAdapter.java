package com.royken.bracongo.bracongosc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.royken.bracongo.bracongosc.R;
import com.royken.bracongo.bracongosc.entities.ProduitMois;

import java.util.List;

public class ProduitMoisCircuitAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<ProduitMois> produitMois;

    public ProduitMoisCircuitAdapter(Context mContext, List<ProduitMois> produitMois) {
        this.mContext = mContext;
        this.produitMois = produitMois;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return produitMois.size();
    }

    @Override
    public Object getItem(int position) {
        return produitMois.get(position);
    }

    @Override
    public long getItemId(int position) {
        return produitMois.get(position).getMontant();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout layout;
        if (convertView == null) {
            layout = (LinearLayout) mInflater.inflate(R.layout.produit_circuit_item, parent, false);
        } else {
            layout = (LinearLayout) convertView;
        }
        TextView tv_produit = (TextView)layout.findViewById(R.id.produit);
        TextView tv_quantite = (TextView) layout.findViewById(R.id.quantite);
        TextView tv_hecto = (TextView) layout.findViewById(R.id.hecto);

        tv_produit.setText(produitMois.get(position).getNomProduit());
        tv_quantite.setText(produitMois.get(position).getQuantite()+"");
        tv_hecto.setText(String.format("%.0f",produitMois.get(position).getHecto()));

        tv_produit.setTag(position);
        tv_quantite.setTag(position);
        tv_hecto.setTag(position);
        return layout;
    }
}
