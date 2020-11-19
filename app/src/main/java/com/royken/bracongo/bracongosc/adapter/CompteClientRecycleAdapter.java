package com.royken.bracongo.bracongosc.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.royken.bracongo.bracongosc.R;
import com.royken.bracongo.bracongosc.entities.Compte;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CompteClientRecycleAdapter extends RecyclerView.Adapter<CompteClientRecycleAdapter.MyViewHolder> implements Filterable {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<Compte> comptes;

    public CompteClientRecycleAdapter(Context mContext) {
        this.mContext = mContext;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public CompteClientRecycleAdapter(Context mContext, List<Compte> comptes) {
        this.mContext = mContext;
        this.comptes = comptes;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public Filter getFilter() {
        return null;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View clientView = mInflater.inflate(R.layout.compte_item, parent, false);
        MyViewHolder holder = new MyViewHolder(clientView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Compte compte = comptes.get(position);
        Log.i("COMPTE", compte.toString());
        TextView tv_NomClient = holder.tv_NomClient;
        TextView tv_dateDemande = holder.tv_dateDemande;
        TextView tv_nomDemandeur = holder.tv_nomDemandeur;
        TextView tv_etat = holder.tv_etat;
        TextView tv_numero = holder.tv_numero;
        RelativeLayout compteLayout = holder.compteLayout;
        RelativeLayout etatLayout = holder.etatLayout;

        String dateString = getStringDate(compte.getDateDemande());
        holder.tv_NomClient.setText(compte.getNomPdv().trim());
        holder.tv_dateDemande.setText(dateString);
        holder.tv_nomDemandeur.setText(compte.getDemandeur());
        boolean validBac = compte.isValidationBac();
        boolean validDsi = compte.isValidationDsi();
        if(!validBac && !validDsi){
            holder.tv_etat.setText("BAC");
            holder.etatLayout.setBackgroundColor(ContextCompat.getColor(mContext,R.color.yellow_A400));
            holder.compteLayout.setVisibility(View.INVISIBLE);
        }
        if(validBac && !validDsi){
            holder.etatLayout.setBackgroundColor(ContextCompat.getColor(mContext,R.color.orange_A400));
            holder.tv_etat.setText("DSI");
            holder.compteLayout.setVisibility(View.INVISIBLE);
        }
        if(validBac && validDsi){
            holder.etatLayout.setBackgroundColor(ContextCompat.getColor(mContext,R.color.green_700));
            holder.tv_etat.setText("CREE");
            holder.compteLayout.setVisibility(View.INVISIBLE);
            holder.tv_numero.setText(compte.getNoCompte().trim());
        }
    }

    @Override
    public int getItemCount() {
        return comptes.size();
    }

    public  class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView tv_NomClient ;
        TextView tv_dateDemande ;
        TextView tv_nomDemandeur ;
        TextView tv_etat ;
        TextView tv_numero ;
        RelativeLayout compteLayout;
        RelativeLayout etatLayout;
        public MyViewHolder(View itemView) {
            super(itemView);
            tv_NomClient = (TextView)itemView.findViewById(R.id.nomClient);
            tv_nomDemandeur = (TextView) itemView.findViewById(R.id.demandeur);
            tv_dateDemande = (TextView) itemView.findViewById(R.id.datecompte);
            tv_etat = (TextView) itemView.findViewById(R.id.etatValue);
            tv_numero = (TextView)itemView.findViewById(R.id.compteValue);
            compteLayout = (RelativeLayout) itemView.findViewById(R.id.compteLayout);
            etatLayout = (RelativeLayout) itemView.findViewById(R.id.etatLayout);

        }
    }

    private String getStringDate(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_MONTH) + "/" +cal.get(Calendar.MONTH) + "/" +cal.get(Calendar.YEAR) +  " " + cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE);
    }

    public void setData(List<Compte> newData) {
        this.comptes = newData;
        notifyDataSetChanged();
    }
}
