package com.royken.bracongo.bracongosc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.royken.bracongo.bracongosc.R;
import com.royken.bracongo.bracongosc.entities.AchatMoisData;

import java.util.List;

public class AchatMoisDataAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<AchatMoisData> achatMoisData;

    public AchatMoisDataAdapter(Context mContext, List<AchatMoisData> achatMoisData) {
        this.mContext = mContext;
        this.achatMoisData = achatMoisData;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return achatMoisData.size();
    }

    @Override
    public Object getItem(int position) {
        return achatMoisData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return achatMoisData.get(position).getJour();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout layout;
        if (convertView == null) {
            layout = (LinearLayout) mInflater.inflate(R.layout.achat_mois_item, parent, false);
        } else {
            layout = (LinearLayout) convertView;
        }
        TextView tv_jour = (TextView)layout.findViewById(R.id.mois);
        TextView tv_bi = (TextView)layout.findViewById(R.id.bimois);
        TextView tv_bg = (TextView) layout.findViewById(R.id.bgmois);
        TextView tv_pet = (TextView) layout.findViewById(R.id.petmois);
        TextView tv_chiffreAffaire = (TextView) layout.findViewById(R.id.totalMois);

        tv_jour.setText((position + 1)+"");
        tv_bi.setText(achatMoisData.get(position).getQteBi()+"");
        tv_bg.setText(achatMoisData.get(position).getQteBg()+"");
        tv_pet.setText(achatMoisData.get(position).getQtePet()+"");
        tv_chiffreAffaire.setText((achatMoisData.get(position).getQteBi() + achatMoisData.get(position).getQteBg() + achatMoisData.get(position).getQtePet()) + "");

        tv_jour.setTag(position);
        tv_bi.setTag(position);
        tv_bg.setTag(position);
        tv_pet.setTag(position);
        tv_chiffreAffaire.setTag(position);
        return layout;
    }
}
