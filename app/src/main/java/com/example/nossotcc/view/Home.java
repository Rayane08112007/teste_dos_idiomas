package com.example.nossotcc.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.example.nossotcc.R;
import com.example.nossotcc.controller.ConversorController;
import com.example.nossotcc.model.Conversao;

public class Home extends AppCompatActivity {

    private EditText etValor;
    private TextView tvResultado;
    private Button btnConverter;
    private String moedaOrigemUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        etValor = findViewById(R.id.Valor);
        tvResultado = findViewById(R.id.dsjValor);
        btnConverter = findViewById(R.id.btnConverter);

        Button btnMetas = findViewById(R.id.Metas);
        Button btnChatbot = findViewById(R.id.Chatbot);
        Button btnInvestimento = findViewById(R.id.btnInvestimento);
        Button btnGastos = findViewById(R.id.btnGastos);
        Button btnFacilitador = findViewById(R.id.btnFacilitador);


        // pega a moeda do usuário salva no cadastro
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
            Intent intent = new Intent(Home.this, Metas.class);
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
            Intent intent = new Intent(Home.this, Gastos.class);
            startActivity(intent);
        });

        btnFacilitador.setOnClickListener(v -> {
            Intent intent = new Intent(Home.this, Facilitador.class);
            startActivity(intent);
        });
    }


}
