package com.example.nossotcc.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
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

    private UsuarioController controller;
    private EditText username, password;
    private Button signIn;
    private TextView signUp;
    private CheckBox exibir_senha;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        findViewById(R.id.btnVoltar).setOnClickListener(v -> {
            startActivity(new Intent(Login.this, Pagina01.class));
            finish();
        });

        initComponentes();

        controller = new UsuarioController(getApplicationContext());


        signIn.setOnClickListener(v -> {
            if (validarCampos()) {
                String user = username.getText().toString().trim().toLowerCase();
                String pass = password.getText().toString().trim();

                boolean isCheckUser = controller.usuarioeSenha(user, pass);

                if (isCheckUser) {
                    startActivity(new Intent(Login.this, Home.class));
                    finish();
                } else {
                    Toast.makeText(Login.this, "Email ou senha inválidos!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(Login.this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
            }
        });


        signUp.setOnClickListener(v -> startActivity(new Intent(Login.this, Cadastro.class)));


        exibir_senha.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                password.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
            password.setSelection(password.getText().length());
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

    private void initComponentes() {
        username = findViewById(R.id.email_campo);
        password = findViewById(R.id.senha_campo);
        signIn   = findViewById(R.id.logar);
        signUp   = findViewById(R.id.register_text);
        exibir_senha = findViewById(R.id.exibir_senha);
    }
}
