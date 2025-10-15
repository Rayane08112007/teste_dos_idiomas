package com.example.nossotcc.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.nossotcc.R;
import com.example.nossotcc.model.Gasto;
import java.util.List;

public class PlanilhaAdapter extends RecyclerView.Adapter<PlanilhaAdapter.ViewHolder> {

    private List<Gasto> gastos;

    public PlanilhaAdapter(List<Gasto> gastos) {
        this.gastos = gastos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_planilha, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Gasto g = gastos.get(position);
        holder.txtDescricao.setText(g.getDescricao());
        holder.txtValor.setText("R$ " + g.getValor());
        holder.txtCategoria.setText(g.getCategoria());
        holder.txtData.setText(g.getData());
        holder.txtFormaPagamento.setText(g.getFormaPagamento());
        holder.txtObs.setText(g.getObservacoes());
    }

    @Override
    public int getItemCount() {
        return gastos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtDescricao, txtValor, txtCategoria, txtData, txtFormaPagamento, txtObs;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDescricao = itemView.findViewById(R.id.txtNome);
            txtValor = itemView.findViewById(R.id.txtValor);
            txtCategoria = itemView.findViewById(R.id.txtCategoria);
            txtData = itemView.findViewById(R.id.txtData);
            txtFormaPagamento = itemView.findViewById(R.id.txtFormaPagamento);
            txtObs = itemView.findViewById(R.id.txtObservacoes);
        }
    }
}
