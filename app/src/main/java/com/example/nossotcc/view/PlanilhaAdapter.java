package com.example.nossotcc.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nossotcc.R;
import com.example.nossotcc.model.Dado;

import java.util.List;

public class PlanilhaAdapter extends RecyclerView.Adapter<PlanilhaAdapter.ViewHolder> {

    private List<Dado> lista;

    public PlanilhaAdapter(List<Dado> lista) {
        this.lista = lista;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_planilha, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Dado dado = lista.get(position);
        holder.txtNome.setText(dado.getNome());
        holder.txtValor.setText(String.valueOf(dado.getValor()));
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtNome, txtValor;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNome = itemView.findViewById(R.id.txtNome);
            txtValor = itemView.findViewById(R.id.txtValor);
        }
    }
}
