package com.royken.bracongo.bracongosc.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.royken.bracongo.bracongosc.R;
import com.royken.bracongo.bracongosc.entities.Circuit;

import java.util.List;

public class CircuitRecycleAdapter extends RecyclerView.Adapter<CircuitRecycleAdapter.MyViewHolder> {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<Circuit> circuits;

    private int positionChoisie = -1;

    public CircuitRecycleAdapter(Context mContext) {
        this.mContext = mContext;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public CircuitRecycleAdapter(Context mContext, List<Circuit> circuits) {
        this.mContext = mContext;
        this.circuits = circuits;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View clientView = mInflater.inflate(R.layout.circuit_choix_item, parent, false);
        MyViewHolder holder = new MyViewHolder(clientView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Circuit circuit = circuits.get(position);
        holder.tv_NomCircuit.setText(circuit.getCirNomcir().trim());
        String circ= circuit.getCirCodcir() == null ? "": circuit.getCirCodcir();
        String codeSigma = circuit.getCirCodsigma() == null ? "" : circuit.getCirCodsigma();
        holder.tv_codeCircuit.setText(circ.trim() + " : " + codeSigma.trim());
        holder.selection.setChecked( positionChoisie == position);
    }

    @Override
    public int getItemCount() {
        return circuits.size();
    }


    public  class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView tv_NomCircuit ;
        TextView tv_codeCircuit ;
        RadioButton selection ;
        public MyViewHolder(View itemView) {
            super(itemView);
            tv_NomCircuit  = (TextView)itemView.findViewById(R.id.nomCircuit);
            tv_codeCircuit = (TextView) itemView.findViewById(R.id.codeCircuit);
            selection = (RadioButton) itemView.findViewById(R.id.select);
            selection.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    positionChoisie = getAdapterPosition();
                    notifyDataSetChanged();
                    Log.i("CHOIX", circuits.get(positionChoisie).getCirNomcir());
                }
            });

        }
    }

    public void setData(List<Circuit> newData) {
        this.circuits = newData;
        notifyDataSetChanged();
    }

    public Circuit getSelectedCircuit(){
        return positionChoisie == -1 ? null : circuits.get(positionChoisie);
    }
}
