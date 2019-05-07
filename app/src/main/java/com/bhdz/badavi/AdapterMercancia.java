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


public class Adapter extends RecyclerView.Adapter<Adapter.ClienteViewHolder> {
    List<Mercancia> botellas;
    List<Mercancia> listaBotellas;

    public Adapter(List <Mercancia>botellas){
        listaBotellas=new ArrayList<Mercancia>();
        listaBotellas.addAll(botellas);
        this.botellas =botellas;
        this.botellas.clear();
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

    Mercancia mercancia= botellas.get(i);
    clienteViewHolder.botella.setText(mercancia.getNombreMercancia());
    }

    @Override
    public int getItemCount() {
        return botellas.size();
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
        botellas.clear();
        if (charText.length() == 0) {
            botellas.addAll(listaBotellas);
        } else {
            for (Mercancia wp : listaBotellas) {
                if (wp.NombreMercancia.toLowerCase(Locale.getDefault()).contains(charText)) {
                    botellas.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}
