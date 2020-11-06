package com.projetofinal.ancea.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.projetofinal.ancea.R;
import com.projetofinal.ancea.data.model.Ocorrencias;
import com.projetofinal.ancea.data.model.Usuario;

import java.util.List;

public class OcorrenciasAdapter extends RecyclerView.Adapter<OcorrenciasAdapter.MyViewHolder> {

    private List<Ocorrencias> ocorrencias;
    private Context context;
    private Usuario pacientes;

    public class MyViewHolder extends RecyclerView.ViewHolder{

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_paciente, parent, false);
        return new MyViewHolder( item ) ;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Ocorrencias oc = ocorrencias.get(position);
        Usuario paciente = oc.getPaciente();

    }

    @Override
    public int getItemCount() {
        return ocorrencias.size();
    }


}
