package com.bhdz.badavi;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;



public class Adapter extends RecyclerView.Adapter<Adapter.ClienteViewHolder> {
    List<String> botellas;

    public Adapter(List <String>botellas){
        this.botellas =botellas;

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
    String botella= botellas.get(i);
    clienteViewHolder.botella.setText("hola");

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
}
