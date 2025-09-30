package com.example.nossotcc.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.nossotcc.R;
import com.example.nossotcc.controller.ConversorController;
import com.example.nossotcc.controller.GastoController;
import com.example.nossotcc.model.Conversao;
import com.example.nossotcc.model.Gasto;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Home extends AppCompatActivity {

    private EditText etValor;
    private TextView tvResultado;
    private Button btnConverter;
    private String moedaOrigemUsuario;

    private PieChart pieChart;
    private GastoController gastoController;
    private Button btnIrPlanilha;
    private Button btnGastos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        etValor = findViewById(R.id.editTextValor);
        tvResultado = findViewById(R.id.ValorResultado);
        btnConverter = findViewById(R.id.btnConverter);

        Button btnMetas = findViewById(R.id.Metas);
        Button btnChatbot = findViewById(R.id.Chatbot);
        Button btnInvestimento = findViewById(R.id.btnInvestimento);
        Button btnGastos = findViewById(R.id.btnGastos);
        Button btnFacilitador = findViewById(R.id.btnFacilitador);



        SharedPreferences prefs = getSharedPreferences("user_prefs", MODE_PRIVATE);
        moedaOrigemUsuario = prefs.getString("moeda_origem", "USD");

        ConversorController conversorController = new ConversorController();

        btnConverter.setOnClickListener(v -> {
            String valorStr = etValor.getText().toString().trim();
            if (valorStr.isEmpty()) {
                Toast.makeText(this, "Digite um valor", Toast.LENGTH_SHORT).show();
                return;
            }

            double valor;
            try {
                valor = Double.parseDouble(valorStr);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Valor inválido", Toast.LENGTH_SHORT).show();
                return;
            }

            Conversao conversao = new Conversao(moedaOrigemUsuario, valor);

            conversorController.converter(conversao, new ConversorController.ConversaoListener() {
                @Override
                public void onSucesso(Conversao conv) {
                    runOnUiThread(() -> tvResultado.setText(String.format("%.2f %s = %.2f BRL",
                            conv.getValorOriginal(), conv.getMoedaOrigem(), conv.getValorConvertido())));
                }

                @Override
                public void onErro(String mensagem) {
                    runOnUiThread(() -> Toast.makeText(Home.this, mensagem, Toast.LENGTH_SHORT).show());
                }
            });
        });

        btnMetas.setOnClickListener(v -> {
            Intent intent = new Intent(Home.this, MetaActivity.class);
            startActivity(intent);
        });

        btnChatbot.setOnClickListener(v -> {
            Intent intent = new Intent(Home.this, ChatBot.class);
            startActivity(intent);
        });

        btnInvestimento.setOnClickListener(v -> {
            Intent intent = new Intent(Home.this, Investimento.class);
            startActivity(intent);
        });

        btnGastos.setOnClickListener(v -> {
            Intent intent = new Intent(Home.this, Planilha.class);
            startActivity(intent);
        });

        btnFacilitador.setOnClickListener(v -> {
            Intent intent = new Intent(Home.this, Facilitador.class);
            startActivity(intent);
        });

        pieChart = findViewById(R.id.pieChart);
        gastoController = new GastoController(this);

        carregarGrafico();
    }

    private void carregarGrafico() {
        List<Gasto> gastos = gastoController.listar();

        Map<String, Double> totaisPorCategoria = new HashMap<>();
        Map<String, Integer> coresPorCategoria = new HashMap<>();

        coresPorCategoria.put("Alimentação", Color.parseColor("#FF6384"));
        coresPorCategoria.put("Transporte", Color.parseColor("#36A2EB"));
        coresPorCategoria.put("Lazer", Color.parseColor("#FFCE56"));
        coresPorCategoria.put("Educação", Color.parseColor("#4CAF50"));
        coresPorCategoria.put("Saúde", Color.parseColor("#9C27B0"));
        coresPorCategoria.put("Outros", Color.parseColor("#FF9800"));

        for (Gasto g : gastos) {
            double total = totaisPorCategoria.getOrDefault(g.getCategoria(), 0.0);
            totaisPorCategoria.put(g.getCategoria(), total + g.getValor());
        }

        ArrayList<PieEntry> entries = new ArrayList<>();
        ArrayList<Integer> colors = new ArrayList<>();

        for (String categoria : totaisPorCategoria.keySet()) {
            entries.add(new PieEntry(totaisPorCategoria.get(categoria).floatValue(), categoria));
            colors.add(coresPorCategoria.getOrDefault(categoria, Color.GRAY));
        }

        PieDataSet dataSet = new PieDataSet(entries, "Gastos por Categoria");
        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        pieChart.setData(data);
        pieChart.invalidate();
    }
}



