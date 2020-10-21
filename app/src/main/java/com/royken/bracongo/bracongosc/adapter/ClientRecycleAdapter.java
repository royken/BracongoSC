package com.royken.bracongo.bracongosc.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.royken.bracongo.bracongosc.R;
import com.royken.bracongo.bracongosc.entities.Client;

import java.util.ArrayList;
import java.util.List;

public class ClientRecycleAdapter extends RecyclerView.Adapter<ClientRecycleAdapter.MyViewHolder> implements Filterable {

    private List<Client> clients;
    private List<Client> clientsFiltres;
    private Context mContext;
    private LayoutInflater mInflater;
    private ClientsAdapterListener listener;

    public ClientRecycleAdapter(List<Client> clients, Context mContext, ClientsAdapterListener listener) {
        this.clients = clients;
        this.clientsFiltres = clients;
        this.mContext = mContext;
        this.listener = listener;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View clientView = mInflater.inflate(R.layout.client_item, viewGroup, false);
        MyViewHolder holder = new MyViewHolder(clientView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        Client client = clientsFiltres.get(i);

        TextView tv_Nom = myViewHolder.tv_Nom;
        TextView tv_type = myViewHolder.tv_type;
        TextView tv_regime = myViewHolder.tv_regime;
        TextView tv_categorie = myViewHolder.tv_categorie;
        ImageView img = myViewHolder.img;

        String firstLetter = String.valueOf(client.getNom().charAt(0));

        ColorGenerator generator = ColorGenerator.MATERIAL;
        int color = generator.getRandomColor();
        TextDrawable drawable = TextDrawable.builder()
                .buildRound(firstLetter, color);

        img.setImageDrawable(drawable);
        tv_Nom.setText(client.getNom().trim());
        tv_type.setText(client.getType().trim());
        tv_regime.setText(client.getRegime().trim());
        tv_categorie.setText(client.getCategorie().trim());

    }

    @Override
    public int getItemCount() {

        return clientsFiltres != null ? clientsFiltres.size() : 0;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if (charString.isEmpty()) {
                    clientsFiltres = clients;
                } else {
                    List<Client> filteredList = new ArrayList<>();
                    for (Client row : clients) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getNom().toLowerCase().contains(charString.toLowerCase()) || row.getNomProprietaire().contains(constraint)) {
                            filteredList.add(row);
                        }
                    }

                    clientsFiltres = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = clientsFiltres;
                return filterResults;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint,
                                          FilterResults results) {
                clientsFiltres = (ArrayList<Client>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public  class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView tv_Nom ;
        TextView tv_type ;
        TextView tv_regime ;
        TextView tv_categorie ;
        ImageView img ;
        public MyViewHolder(View itemView) {
            super(itemView);
            tv_Nom = (TextView)itemView.findViewById(R.id.nom);
            tv_type = (TextView) itemView.findViewById(R.id.type);
            tv_regime = (TextView) itemView.findViewById(R.id.regime);
            tv_categorie = (TextView) itemView.findViewById(R.id.categorie);
            img = (ImageView)itemView.findViewById(R.id.image_view);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                    listener.onClientSelected(clientsFiltres.get(getAdapterPosition()));
                }
            });
        }
    }

    public interface ClientsAdapterListener {
        void onClientSelected(Client client);
    }
}
