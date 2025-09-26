package com.example.nossotcc.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;


import com.example.nossotcc.R;

import com.example.nossotcc.controller.ConversorController;
import com.example.nossotcc.controller.GastoController;
import com.example.nossotcc.model.Conversao;
import com.example.nossotcc.model.Gasto;
import com.google.android.material.navigation.NavigationView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;

public class Home extends AppCompatActivity {


    private EditText etValor;
    private Button btnConverter;
    private TextView tvResultado;
    private String moedaOrigem;



    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private PieChart pieChart;
    private GastoController gastoController;

    //parte do conversor

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        drawerLayout = findViewById(R.id.drawerLayout);
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.navigationView);
        setSupportActionBar(toolbar);


        etValor = findViewById(R.id.Valor);
        btnConverter = findViewById(R.id.btnConverter);
        tvResultado = findViewById(R.id.dsjValor);

        ConversorController conversorController = new ConversorController();
        String moedaOrigem = "USD";

        btnConverter.setOnClickListener(v -> {
            String valorStr = etValor.getText().toString().trim();
            if (valorStr.isEmpty()) {
                Toast.makeText(this, "Digite um valor", Toast.LENGTH_SHORT).show();
                return;
            }

            double valor = Double.parseDouble(valorStr);
            Conversao conversao = new Conversao(moedaOrigem, valor);

            conversorController.converterParaReal(conversao, new ConversorController.ConversaoListener() {
                @Override
                public void onSucesso(Conversao conv) {
                    runOnUiThread(() -> tvResultado.setText(String.format("%.2f BRL", conv.getValorConvertido())));
                }

                @Override
                public void onErro(String mensagem) {
                    runOnUiThread(() -> Toast.makeText(Home.this, mensagem, Toast.LENGTH_SHORT).show());
                }
            });
        });


        //------ parte do grafico ---------

//        pieChart = findViewById(R.id.pieChart); //coloca isso tbm por favor O pieChart.invalidate
//        // serve para redesenhar o gráfico na tela depois que você altera os dados dele.
//        gastoController = new GastoController(this);
//        carregarGrafico();


//        Button btnGastos = findViewById(R.id.btnGastos);
//        btnGastos.setOnClickListener(v -> {
//            Intent intent = new Intent(Home.this, Planilhas.class);
//            startActivity(intent);
//        });
    }


    private void carregarGrafico() {
        ArrayList<Gasto> lista = gastoController.listar();
        ArrayList<PieEntry> entries = new ArrayList<>();

        for (Gasto g : lista) {
            if (g.getValor() > 0) {
                entries.add(new PieEntry((float) g.getValor(), g.getDescricao()));
            }
        }

        if (!entries.isEmpty()) {
            PieDataSet dataSet = new PieDataSet(entries, "Gastos");
            PieData data = new PieData(dataSet);
            pieChart.setData(data);
            pieChart.invalidate();
        }
    }
}
