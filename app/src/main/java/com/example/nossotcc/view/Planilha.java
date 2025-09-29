package com.example.nossotcc.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nossotcc.R;
import com.example.nossotcc.controller.DadoController;
import com.example.nossotcc.model.Dado;

import java.util.List;

public class Planilha extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PlanilhaAdapter adapter;
    private EditText edtNome, edtValor;
    private Button btnAdicionar, btnIrHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planilha);

        recyclerView = findViewById(R.id.recyclerView);
        edtNome = findViewById(R.id.edtNome);
        edtValor = findViewById(R.id.edtValor);
        btnAdicionar = findViewById(R.id.btnAdicionar);
        btnIrHome = findViewById(R.id.btnIrHome);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Dado> lista = DadoController.getDados();
        adapter = new PlanilhaAdapter(lista);
        recyclerView.setAdapter(adapter);

        btnAdicionar.setOnClickListener(v -> {
            String nome = edtNome.getText().toString();
            float valor = Float.parseFloat(edtValor.getText().toString());
            DadoController.adicionarDado(nome, valor);
            adapter.notifyDataSetChanged();
            edtNome.setText("");
            edtValor.setText("");
        });

        btnIrHome.setOnClickListener(v -> {
            Intent intent = new Intent(Planilha.this, Home.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        });

    }
}

