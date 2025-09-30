package com.example.nossotcc.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.nossotcc.R;

import com.example.nossotcc.controller.GastoController;
import com.example.nossotcc.model.Gasto;
import java.util.List;

public class Planilha extends AppCompatActivity {

    private EditText edtDescricao, edtValor, edtCategoria, edtData, edtFormaPagamento, edtObs ;
    private Button btnAdicionar, btnIrHome;
    private RecyclerView recyclerView;
    private GastoController gastoController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planilha);

        gastoController = new GastoController(this);

        edtDescricao = findViewById(R.id.edtNome);
        edtValor = findViewById(R.id.edtValor);
        edtCategoria = findViewById(R.id.edtCategoria);
        edtData = findViewById(R.id.edtData);
        edtFormaPagamento = findViewById(R.id.edtFormaPagamento);
        edtObs = findViewById(R.id.edtObservacoes);
        btnAdicionar = findViewById(R.id.btnAdicionar);
        btnIrHome = findViewById(R.id.btnIrHome);
        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        btnAdicionar.setOnClickListener(v -> {
            Gasto g = new Gasto();
            g.setDescricao(edtDescricao.getText().toString());
            g.setValor(Double.parseDouble(edtValor.getText().toString()));
            g.setCategoria(edtCategoria.getText().toString());
            g.setData(edtData.getText().toString());
            g.setFormaPagamento(edtFormaPagamento.getText().toString());
            g.setObservacoes(edtObs.getText().toString());
            g.setRecorrente(false);
            g.setTag(null);

            gastoController.salvar(g);
            carregarLista();
        });

        btnIrHome.setOnClickListener(v -> {

            Intent intent = new Intent(Planilha.this, Home.class);
            startActivity(intent);
            finish();
        });

        carregarLista();
    }

    private void carregarLista() {
        List<Gasto> lista = gastoController.listar();
        recyclerView.setAdapter(new PlanilhaAdapter(lista));
    }
}
