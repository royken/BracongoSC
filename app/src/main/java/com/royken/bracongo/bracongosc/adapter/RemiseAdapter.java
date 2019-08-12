package com.royken.bracongo.bracongosc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.royken.bracongo.bracongosc.R;
import com.royken.bracongo.bracongosc.entities.RemiseInfo;
import java.util.List;

public class RemiseAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<RemiseInfo> infos;

    public RemiseAdapter(Context mContext, List<RemiseInfo> infos) {
        this.mContext = mContext;
        this.infos = infos;
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
        return infos.get(position).getMois();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout layout;
        if (convertView == null) {
            layout = (LinearLayout) mInflater.inflate(R.layout.remise_item, parent, false);
        } else {
            layout = (LinearLayout) convertView;
        }
        TextView tv_mois = (TextView)layout.findViewById(R.id.mois);
        TextView tv_remise = (TextView) layout.findViewById(R.id.remise);
        TextView tv_chiffreAffaire = (TextView) layout.findViewById(R.id.cAffaire);

        tv_mois.setText(infos.get(position).getMois()+"");
        tv_remise.setText(String.format("%.0f",infos.get(position).getRemise()));
        tv_chiffreAffaire.setText(String.format("%.0f",infos.get(position).getChiffreAffaire()));

        tv_mois.setTag(position);
        tv_remise.setTag(position);
        tv_chiffreAffaire.setTag(position);
        return layout;
    }
}
