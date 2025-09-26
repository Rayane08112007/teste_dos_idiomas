package com.example.nossotcc.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.example.nossotcc.R;

public class Pagina01 extends AppCompatActivity {

    Button btnLogin, btnCadastro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina01);

        btnLogin = findViewById(R.id.btnLogin);
        btnCadastro = findViewById(R.id.btnCadastro);

        btnLogin.setOnClickListener(v -> {
            Intent intent = new Intent(Pagina01.this, Login.class);
            startActivity(intent);
        });

        btnCadastro.setOnClickListener(v -> {
            Intent intent = new Intent(Pagina01.this, Cadastro.class);
            startActivity(intent);
        });
    }
}
