package com.example.nossotcc.view;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nossotcc.R;
import com.example.nossotcc.controller.UsuarioController;
import com.example.nossotcc.model.Usuario;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Cadastro extends AppCompatActivity {

    private EditText nome, email, dataNasc, senha, confSenha;
    private Spinner nacionalidade;
    private RadioButton feminino, masculino, naoInformar, outros;
    private CheckBox cbMostrarSenha, cbMostrarConfirmarSenha;
    private Button cadastrar, btnVoltar;
    private UsuarioController usuarioController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        initComponents();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.paises,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        nacionalidade.setAdapter(adapter);

        usuarioController = new UsuarioController(getApplicationContext());

        dataNasc.setOnClickListener(v -> {
            Calendar c = Calendar.getInstance();
            int ano = c.get(Calendar.YEAR);
            int mes = c.get(Calendar.MONTH);
            int dia = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dpd = new DatePickerDialog(Cadastro.this, (view, year, month, dayOfMonth) -> {
                month += 1; // ajuste d
                String diaStr = (dayOfMonth < 10 ? "0" : "") + dayOfMonth;
                String mesStr = (month < 10 ? "0" : "") + month;
                dataNasc.setText(year + "-" + mesStr + "-" + diaStr); // YYYY-MM-DD
            }, ano, mes, dia);

            dpd.show();
        });


        btnVoltar.setOnClickListener(v -> {
            Intent intent = new Intent(Cadastro.this, Pagina01.class);
            startActivity(intent);
            finish();
        });


        cadastrar.setOnClickListener(view -> {
            String nomeTxt = nome.getText().toString().trim();
            String emailTxt = email.getText().toString().trim();
            String dataNascTxt = dataNasc.getText().toString().trim();
            String nacionalidadeTxt = nacionalidade.getSelectedItem().toString();
            String senhaTxt = senha.getText().toString();
            String confSenhaTxt = confSenha.getText().toString();

            Map<String, String> paisParaMoeda = new HashMap<>();
            paisParaMoeda.put("Venezuelanos", "VES");
            paisParaMoeda.put("Haitianos", "HTG");
            paisParaMoeda.put("Cubanos", "CUP");
            paisParaMoeda.put("Bolivianos", "BOB");
            paisParaMoeda.put("Argentinos", "ARS");
            paisParaMoeda.put("China", "CNY");
            paisParaMoeda.put("Índia", "INR");
            paisParaMoeda.put("Angola", "AOA");

            String genero = "";
            if (feminino.isChecked()) genero = "Feminino";
            else if (masculino.isChecked()) genero = "Masculino";
            else if (naoInformar.isChecked()) genero = "Prefiro não informar";
            else if (outros.isChecked()) genero = "Outros";

            // Validações
            if (nomeTxt.isEmpty() || emailTxt.isEmpty() || senhaTxt.isEmpty() || dataNascTxt.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos obrigatórios!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!senhaTxt.equals(confSenhaTxt)) {
                Toast.makeText(this, "As senhas não conferem!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (usuarioController.checkUser(emailTxt)) {
                Toast.makeText(this, "Usuário já existe!", Toast.LENGTH_SHORT).show();
                return;
            }

            Usuario usuario = new Usuario();
            usuario.setUserNome(nomeTxt);
            usuario.setUserEmail(emailTxt);
            usuario.setPassword(senhaTxt);
            usuario.setNacionalidade(nacionalidadeTxt);
            usuario.setNascimento(dataNascTxt);
            usuario.setGenero(genero);

            if (usuarioController.incluir(usuario)) {
                String moedaUsuario = paisParaMoeda.getOrDefault(nacionalidadeTxt, "USD");

                SharedPreferences prefs = getSharedPreferences("user_prefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("moeda_origem", moedaUsuario);
                editor.putString("nomeUsuario", nomeTxt);
                editor.apply();

                Intent intent = new Intent(Cadastro.this, Home.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Erro ao cadastrar usuário!", Toast.LENGTH_SHORT).show();
            }
        });


        cbMostrarSenha.setOnCheckedChangeListener((buttonView, isChecked) -> {
            senha.setInputType(isChecked ? android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                    : android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD);
            senha.setSelection(senha.getText().length());
        });

        cbMostrarConfirmarSenha.setOnCheckedChangeListener((buttonView, isChecked) -> {
            confSenha.setInputType(isChecked ? android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                    : android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD);
            confSenha.setSelection(confSenha.getText().length());
        });
    }

    private void initComponents() {
        nome = findViewById(R.id.nome_campo);
        email = findViewById(R.id.email_campo);
        dataNasc = findViewById(R.id.data_nasc);
        nacionalidade = findViewById(R.id.Nacionalidade);
        senha = findViewById(R.id.senha);
        confSenha = findViewById(R.id.conf_senha);

        feminino = findViewById(R.id.Feminino);
        masculino = findViewById(R.id.Masculino);
        naoInformar = findViewById(R.id.NaoInformar);
        outros = findViewById(R.id.Outros);

        cbMostrarSenha = findViewById(R.id.cbMostrarSenha);
        cbMostrarConfirmarSenha = findViewById(R.id.cbMostrarConfirmarSenha);

        cadastrar = findViewById(R.id.cadastrar);
        btnVoltar = findViewById(R.id.btnVoltar);
    }
}
