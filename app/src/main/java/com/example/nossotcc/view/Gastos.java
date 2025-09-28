package com.example.nossotcc.view;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nossotcc.R;
import com.example.nossotcc.controller.GastoController;
import com.example.nossotcc.model.Gasto;

import java.util.ArrayList;

public class Gastos extends AppCompatActivity {

    private EditText edtDescricao, edtValor, edtCategoria, edtData, edtFormaPagamento, edtObservacoes, edtMesConsulta;
    private Button btnSalvar, btnConsultar;
    private TextView tvResultado;
    private GastoController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gastos);

        edtDescricao = findViewById(R.id.edtDescricao);
        edtValor = findViewById(R.id.edtValor);
        edtCategoria = findViewById(R.id.edtCategoria);
        edtData = findViewById(R.id.edtData);
        edtFormaPagamento = findViewById(R.id.edtFormaPagamento);
        edtObservacoes = findViewById(R.id.edtObservacoes);

        edtMesConsulta = findViewById(R.id.edtMesConsulta);
        btnConsultar = findViewById(R.id.btnConsultar);

        btnSalvar = findViewById(R.id.btnSalvar);
        tvResultado = findViewById(R.id.tvResultado);

        controller = new GastoController(this);

        btnSalvar.setOnClickListener(v -> salvarGasto());
        btnConsultar.setOnClickListener(v -> consultarGastosPorMes());
    }

    private void salvarGasto() {
        String descricao = edtDescricao.getText().toString().trim();
        String valorStr = edtValor.getText().toString().trim();
        String categoria = edtCategoria.getText().toString().trim();
        String data = edtData.getText().toString().trim();
        String forma = edtFormaPagamento.getText().toString().trim();
        String observacoes = edtObservacoes.getText().toString().trim();

        if (descricao.isEmpty() || valorStr.isEmpty() || data.isEmpty()) {
            Toast.makeText(this, "Preencha pelo menos descrição, valor e data", Toast.LENGTH_SHORT).show();
            return;
        }

        double valor;
        try {
            valor = Double.parseDouble(valorStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Valor inválido", Toast.LENGTH_SHORT).show();
            return;
        }

        Gasto gasto = new Gasto();
        gasto.setDescricao(descricao);
        gasto.setValor(valor);
        gasto.setCategoria(categoria);
        gasto.setData(data);
        gasto.setFormaPagamento(forma);
        gasto.setObservacoes(observacoes);

        if (controller.inserir(gasto)) {
            Toast.makeText(this, "Gasto salvo!", Toast.LENGTH_SHORT).show();
            limparCampos();
        } else {
            Toast.makeText(this, "Erro ao salvar gasto!", Toast.LENGTH_SHORT).show();
        }
    }

    private void consultarGastosPorMes() {
        String mes = edtMesConsulta.getText().toString().trim(); // Formato YYYY-MM
        if (mes.isEmpty()) {
            Toast.makeText(this, "Digite o mês para consulta (ex.: 2025-09)", Toast.LENGTH_SHORT).show();
            return;
        }

        ArrayList<Gasto> listaMes = controller.listarPorMes(mes);
        if (listaMes.isEmpty()) {
            tvResultado.setText("Nenhum gasto encontrado para " + mes);
            return;
        }

        StringBuilder resultado = new StringBuilder();
        double total = 0;

        for (Gasto g : listaMes) {
            resultado.append("Descrição: ").append(g.getDescricao()).append("\n")
                    .append("Valor: R$ ").append(g.getValor()).append("\n")
                    .append("Categoria: ").append(g.getCategoria()).append("\n")
                    .append("Data: ").append(g.getData()).append("\n")
                    .append("Forma: ").append(g.getFormaPagamento()).append("\n")
                    .append("Observações: ").append(g.getObservacoes()).append("\n\n");

            total += g.getValor();
        }

        resultado.append("Total do mês: R$ ").append(total);
        tvResultado.setText(resultado.toString());
    }

    private void limparCampos() {
        edtDescricao.setText("");
        edtValor.setText("");
        edtCategoria.setText("");
        edtData.setText("");
        edtFormaPagamento.setText("");
        edtObservacoes.setText("");
    }
}
