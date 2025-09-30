package com.example.nossotcc.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.nossotcc.R;
import com.example.nossotcc.controller.UsuarioController;


public class Login extends AppCompatActivity {

    UsuarioController controller;
    EditText username, password;
    Button signIn;
    TextView signUp;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initComponentes();



        controller = new UsuarioController(getApplicationContext());

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validarCampos()) {
                    String user = username.getText().toString().trim();
                    String pass = password.getText().toString().trim();

                    boolean isCheckUser = controller.usuarioeSenha(user, pass);

                    if (isCheckUser) {
                        Intent home = new Intent(Login.this, Home.class);
                        startActivity(home);
                        finish();
                    } else {

                        Toast.makeText(Login.this, "Email ou senha inválidos!", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(Login.this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
                }
            }

        });

        Button btnVoltar = findViewById(R.id.btnVoltar);
        btnVoltar.setOnClickListener(v -> {
            Intent intent = new Intent(Login.this, Pagina01.class);
            startActivity(intent);
            finish();
        });


        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cadastro = new Intent(Login.this, Cadastro.class);
                startActivity(cadastro);
            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


    }

    private boolean validarCampos() {
        return !username.getText().toString().isEmpty() &&
                !password.getText().toString().isEmpty();
    }

    @SuppressLint("WrongViewCast")
    private void initComponentes() {
        username = findViewById(R.id.email_campo);
        password = findViewById(R.id.senha_campo);
        signIn   = findViewById(R.id.logar);
        signUp   = findViewById(R.id.register_text);

    }
}
