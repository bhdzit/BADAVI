package com.bhdz.badavi;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class AdapterMercancia extends RecyclerView.Adapter<AdapterMercancia.ClienteViewHolder> {
   public static List<Mercancia> Mercancia;
    List<Mercancia> listaMercancia;

    public AdapterMercancia(List <Mercancia> Mercancia){
        listaMercancia =new ArrayList<Mercancia>();
        listaMercancia.addAll(Mercancia);
        this.Mercancia = Mercancia;
        this.Mercancia.clear();

    }
    @NonNull
    @Override
    public ClienteViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.textview_layout,viewGroup,false);
        ClienteViewHolder clienteViewHolder = new ClienteViewHolder(v);
        return clienteViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ClienteViewHolder clienteViewHolder, int i) {

    final Mercancia mercancia= Mercancia.get(i);
    clienteViewHolder.botella.setText(mercancia.getNombreMercancia());

        clienteViewHolder.botella.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ModificarBotella.searchViewMercancia.setQuery(mercancia.getNombreMercancia(),false);
                ModificarBotella.searchViewMarca.setQuery(mercancia.getMarcaMercancia(),false);
                ModificarBotella.idMercancia=mercancia.getIdMercancia();
              //  ModificarBotella.searchViewMarca.setQuery(mercancia.getMarcaMercancia(),false);
            }
        });
    }

    @Override
    public int getItemCount() {
        return Mercancia.size();
    }

    public static class ClienteViewHolder extends RecyclerView.ViewHolder {
        TextView botella;
        public ClienteViewHolder(@NonNull View itemView) {
            super(itemView);
            botella= (TextView) itemView.findViewById(R.id.rvbotella);
        }
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        Mercancia.clear();
        if (charText.length() == 0) {
            Mercancia.addAll(listaMercancia);
        } else {
            for (Mercancia wp : listaMercancia) {
                if (wp.NombreMercancia.toLowerCase(Locale.getDefault()).contains(charText)) {

                    Mercancia.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}
