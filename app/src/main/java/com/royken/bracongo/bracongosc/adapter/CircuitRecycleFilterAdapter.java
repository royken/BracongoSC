package com.royken.bracongo.bracongosc.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.royken.bracongo.bracongosc.R;
import com.royken.bracongo.bracongosc.entities.Circuit;

import java.util.ArrayList;
import java.util.List;

public class CircuitRecycleFilterAdapter extends RecyclerView.Adapter<CircuitRecycleFilterAdapter.MyViewHolder> implements Filterable {

    private List<Circuit> circuits;
    private List<Circuit> circuitsFiltres;
    private Context mContext;
    private LayoutInflater mInflater;
    private CircuitAdapterListener listener;

    public CircuitRecycleFilterAdapter(Context mContext, CircuitAdapterListener listener) {
        this.mContext = mContext;
        this.listener = listener;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View clientView = mInflater.inflate(R.layout.circuit_item, viewGroup, false);
        MyViewHolder holder = new MyViewHolder(clientView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        Circuit circuit = circuitsFiltres.get(i);

        TextView tv_NomCircuit = myViewHolder.tv_NomCircuit;
        TextView tv_codeCircuit = myViewHolder.tv_codeCircuit;

        tv_NomCircuit.setText(circuit.getCirNomcir().trim());
        String circ= circuit.getCirCodcir() == null ? "": circuit.getCirCodcir();
        String codeSigma = circuit.getCirCodsigma() == null ? "" : circuit.getCirCodsigma();
        tv_codeCircuit.setText(circ.trim() + " : " + codeSigma.trim());

    }

    @Override
    public int getItemCount() {

        return circuitsFiltres != null ? circuitsFiltres.size() : 0;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if (charString.isEmpty()) {
                    circuitsFiltres = circuits;
                } else {
                    List<Circuit> filteredList = new ArrayList<>();
                    for (Circuit row : circuits) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        String circ= row.getCirCodcir() == null ? "": row.getCirCodcir();
                        String codeSigma = row.getCirCodsigma() == null ? "" : row.getCirCodsigma();
                        String nom = row.getCirNomcir() == null ? "" : row.getCirNomcir();
                        if (codeSigma.toLowerCase().contains(charString.toLowerCase()) || circ.contains(constraint) || nom.contains(constraint)) {
                            filteredList.add(row);
                        }
                    }

                    circuitsFiltres = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = circuitsFiltres;
                return filterResults;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint,
                                          FilterResults results) {
                circuitsFiltres = (ArrayList<Circuit>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public  class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView tv_NomCircuit ;
        TextView tv_codeCircuit ;
        public MyViewHolder(View itemView) {
            super(itemView);
            tv_NomCircuit  = (TextView)itemView.findViewById(R.id.nomCircuit);
            tv_codeCircuit = (TextView) itemView.findViewById(R.id.codeCircuit);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                    listener.onCircuitSelected(circuitsFiltres.get(getAdapterPosition()));
                }
            });

        }
    }

    public interface CircuitAdapterListener {
        void onCircuitSelected(Circuit circuit);
    }

    public void setData(List<Circuit> newData) {
        this.circuits = newData;
        this.circuitsFiltres = newData;
        notifyDataSetChanged();
    }
}
