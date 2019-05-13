package com.bhdz.badavi;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AdapaterMarca extends RecyclerView.Adapter<AdapaterMarca.ClienteViewHolder>  {
    public static  List<Mercancia>  marcas;
    List<Mercancia> ListaMarca;

    public AdapaterMarca(List <Mercancia>botellas){
        ListaMarca =new ArrayList<Mercancia>();
        ListaMarca.addAll(botellas);
        this.marcas =botellas;
       this.marcas.clear();
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

       final Mercancia mercancia= marcas.get(i);
        clienteViewHolder.botella.setText(mercancia.getMarcaMercancia());
        clienteViewHolder.botella.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ModificarBotella.searchViewMercancia.setQuery(mercancia.getNombreMercancia(),false);
                ModificarBotella.searchViewMarca.setQuery(mercancia.getMarcaMercancia(),false);


                //  ModificarBotella.searchViewMarca.setQuery(mercancia.getMarcaMercancia(),false);
            }
        });
    }

    @Override
    public int getItemCount() {
        return marcas.size();
    }

    public static class ClienteViewHolder extends RecyclerView.ViewHolder {
        TextView botella;
        public ClienteViewHolder(@NonNull View itemView) {
            super(itemView);
            botella= (TextView) itemView.findViewById(R.id.rvbotella);
        }
    }

    public void filter(String charText) {
        Log.e("Adapter",charText);
        charText = charText.toLowerCase(Locale.getDefault());
        marcas.clear();
        if (charText.length() == 0) {
            marcas.addAll(ListaMarca);
        } else {
            for (Mercancia wp : ListaMarca) {
                if (wp.MarcaMercancia.toLowerCase(Locale.getDefault()).contains(charText)) {
                    marcas.add(wp);
                }
            }
        }
       notifyDataSetChanged();
    }
}
