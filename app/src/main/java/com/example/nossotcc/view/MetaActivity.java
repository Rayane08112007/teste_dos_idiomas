package com.example.nossotcc.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nossotcc.R;
import com.example.nossotcc.controller.MetaController;
import com.example.nossotcc.model.Meta;

import java.util.List;

public class MetaActivity extends AppCompatActivity {

    private MetaController metaController;

    private EditText etTitulo, etDescricao, etValor, etIdRemover;
    private TextView tvMetas;
    private Button btnAdicionar, btnListar, btnRemover, btnVoltarHome;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meta);


        metaController = new MetaController(this);


        etTitulo = findViewById(R.id.etTitulo);
        etDescricao = findViewById(R.id.etDescricao);
        etValor = findViewById(R.id.etValor);
        etIdRemover = findViewById(R.id.etIndiceRemover);
        tvMetas = findViewById(R.id.tvMetas);

        btnAdicionar = findViewById(R.id.btnAdicionar);
        btnListar = findViewById(R.id.btnListar);
        btnRemover = findViewById(R.id.btnRemover);
        btnVoltarHome = findViewById(R.id.btnVoltarHome);

        btnAdicionar.setOnClickListener(v -> adicionarMeta());


        btnListar.setOnClickListener(v -> listarMetas());


        btnRemover.setOnClickListener(v -> removerMeta());


        listarMetas();

        btnVoltarHome.setOnClickListener(v -> {
            Intent intent = new Intent(MetaActivity.this, Home.class);
            startActivity(intent);
            finish();
        });
    }


    private void adicionarMeta() {
        String titulo = etTitulo.getText().toString();
        String descricao = etDescricao.getText().toString();
        String valorStr = etValor.getText().toString();

        if (titulo.isEmpty() || descricao.isEmpty() || valorStr.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }

        double valor;
        try {
            valor = Double.parseDouble(valorStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Valor inválido", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean sucesso = metaController.adicionarMeta(titulo, descricao, valor);
        if (sucesso) {
            Toast.makeText(this, "Meta adicionada!", Toast.LENGTH_SHORT).show();
            limparCampos();
            listarMetas();
        } else {
            Toast.makeText(this, "Erro ao adicionar meta", Toast.LENGTH_SHORT).show();
        }
    }


    private void listarMetas() {
        List<Meta> lista = metaController.listarMetas();
        StringBuilder sb = new StringBuilder();

        for (Meta meta : lista) {
            sb.append(meta.toString()).append("\n");
        }

        tvMetas.setText(sb.toString());
    }


    private void removerMeta() {
        String idStr = etIdRemover.getText().toString();
        if (idStr.isEmpty()) {
            Toast.makeText(this, "Informe o ID da meta", Toast.LENGTH_SHORT).show();
            return;
        }

        int id;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "ID inválido", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean sucesso = metaController.removerMeta(id);
        if (sucesso) {
            Toast.makeText(this, "Meta removida!", Toast.LENGTH_SHORT).show();
            listarMetas();
        } else {
            Toast.makeText(this, "ID não encontrado", Toast.LENGTH_SHORT).show();
        }
    }


    private void limparCampos() {
        etTitulo.setText("");
        etDescricao.setText("");
        etValor.setText("");
        etIdRemover.setText("");
    }
}
