package com.royken.bracongo.bracongosc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.royken.bracongo.bracongosc.R;
import com.royken.bracongo.bracongosc.entities.CentreDistribution;

import java.util.List;

public class CdRecycleAdapter extends RecyclerView.Adapter<CdRecycleAdapter.MyViewHolder>  {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<CentreDistribution> cds;

    private int positionChoisie = -1;

    public CdRecycleAdapter(Context mContext) {
        this.mContext = mContext;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public CdRecycleAdapter(Context mContext, List<CentreDistribution> cds) {
        this.mContext = mContext;
        this.cds = cds;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View clientView = mInflater.inflate(R.layout.cd_choix_item, parent, false);
        MyViewHolder holder = new MyViewHolder(clientView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        CentreDistribution cd = cds.get(position);
        holder.tv_codeCd.setText(cd.getCdiCodecd().trim());
        holder.tv_NomCd.setText(cd.getCdiNomcdi().trim());
        holder.selection.setChecked( positionChoisie == position);
    }

    @Override
    public int getItemCount() {
        return cds.size();
    }

    public  class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView tv_NomCd ;
        TextView tv_codeCd ;
        RadioButton selection ;
        public MyViewHolder(View itemView) {
            super(itemView);
            tv_NomCd  = (TextView)itemView.findViewById(R.id.nomCd);
            tv_codeCd = (TextView) itemView.findViewById(R.id.codeCd);
            selection = (RadioButton) itemView.findViewById(R.id.select);
            selection.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    positionChoisie = getAdapterPosition();
                    notifyDataSetChanged();
                }
            });

        }
    }

    public void setData(List<CentreDistribution> newData) {
        this.cds = newData;
        notifyDataSetChanged();
    }

    public CentreDistribution getSelectedCd(){
        return positionChoisie == -1 ? null : cds.get(positionChoisie);
    }
}
