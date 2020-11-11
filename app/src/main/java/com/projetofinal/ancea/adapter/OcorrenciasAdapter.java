package com.projetofinal.ancea.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.projetofinal.ancea.R;
import com.projetofinal.ancea.data.model.Ocorrencias;
import com.projetofinal.ancea.data.model.Usuario;

import java.util.List;

public class OcorrenciasAdapter extends RecyclerView.Adapter<OcorrenciasAdapter.MyViewHolder> {

    private List<Ocorrencias> ocorrencias;
    private Context context;
    private Usuario paciente;

    public OcorrenciasAdapter(List<Ocorrencias> ocorrencias, Context context, Usuario paciente) {
        this.ocorrencias = ocorrencias;
        this.context = context;
        this.paciente = paciente;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView titulo;
        TextView data;
        TextView resumo;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.textTitulo);
            data = itemView.findViewById(R.id.textData);
            resumo = itemView.findViewById(R.id.textResumo);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_lista_ocorrencias, parent, false);
        return new MyViewHolder( item ) ;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Ocorrencias oc = ocorrencias.get(position);
        paciente.setOcorrencias(ocorrencias);
        holder.titulo.setText(oc.getTipoCrise());
        holder.resumo.setText(oc.getObservacoesExtras());
        holder.data.setText(oc.getData());
    }

    @Override
    public int getItemCount() {
        return ocorrencias.size();
    }


}
