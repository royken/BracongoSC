package com.royken.bracongo.bracongosc.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.royken.bracongo.bracongosc.R;
import com.royken.bracongo.bracongosc.entities.Client;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vr.kenfack on 30/08/2017.
 */

public class ClientAdapter extends BaseAdapter implements Filterable {

    private List<Client> clients;
    private List<Client> orig;
    private Context mContext;
    private LayoutInflater mInflater;

    public ClientAdapter(Context mContext, List<Client> clients) {
        this.mContext = mContext;
        this.clients = clients;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return clients.size();
    }

    @Override
    public Object getItem(int position) {
        return clients.get(position);
    }

    @Override
    public long getItemId(int position) {
        return clients.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FrameLayout layout;
        if (convertView == null) {
            layout = (FrameLayout) mInflater.inflate(R.layout.client_item, parent, false);
        } else {
            layout = (FrameLayout) convertView;
        }

        ImageView img = (ImageView)layout.findViewById(R.id.image_view);
        // ImageView img2 = (ImageView)layout.findViewById(R.id.image_view1);
        String firstLetter = String.valueOf(clients.get(position).getNom().charAt(0));

        ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
        // generate random color
        int color = generator.getRandomColor();
        //int color = generator.getRandomColor();
        //Log.i("CARACTER",firstLetter);
        TextDrawable drawable = TextDrawable.builder()
                .buildRound(firstLetter, color);

        img.setImageDrawable(drawable);

        TextView tv_Nom = (TextView)layout.findViewById(R.id.nom);
        TextView tv_type = (TextView) layout.findViewById(R.id.type);
        TextView tv_regime = (TextView) layout.findViewById(R.id.regime);
        TextView tv_categorie = (TextView) layout.findViewById(R.id.categorie);
        tv_Nom.setText(clients.get(position).getNom());
        tv_type.setText(clients.get(position).getType());
        tv_regime.setText(clients.get(position).getRegime());
        tv_categorie.setText(clients.get(position).getCategorie());

        tv_Nom.setTag(position);
        img.setTag(position);
        tv_type.setTag(position);
        tv_regime.setTag(position);
        tv_categorie.setTag(position);
        return layout;
    }

    public Filter getFilter() {
        return new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                final FilterResults oReturn = new FilterResults();
                final ArrayList<Client> results = new ArrayList<Client>();
                if (orig == null)
                    orig = clients;
                if (constraint != null) {
                    if (orig != null && orig.size() > 0) {
                        for (final Client g : orig) {
                            if (g.getNom().toLowerCase()
                                    .contains(constraint.toString()))
                                results.add(g);
                        }
                    }
                    oReturn.values = results;
                }
                return oReturn;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint,
                                          FilterResults results) {
                clients = (ArrayList<Client>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @Override
    public boolean isEmpty() {
        return clients.isEmpty();
    }

}
