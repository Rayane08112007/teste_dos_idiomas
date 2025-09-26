package com.example.nossotcc.model;

public class Usuario {

    private int id;
    private String nome;
    private String email;
    private String nacionalidade;
    private String nascimento;
    private String senha;
    private String genero;

    public Usuario() {}


    public Usuario(int id, String nome, String email, String senha, String nacionalidade, String nascimento, String genero) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.nacionalidade = nacionalidade;
        this.nascimento = nascimento;
        this.genero = genero;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getUserNome() { return nome; }
    public void setUserNome(String nome) { this.nome = nome; }

    public String getUserEmail() { return email; }
    public void setUserEmail(String email) { this.email = email; }

    public String getPassword() { return senha; }
    public void setPassword(String senha) { this.senha = senha; }

    public String getNacionalidade() { return nacionalidade; }
    public void setNacionalidade(String nacionalidade) { this.nacionalidade = nacionalidade; }

    public String getNascimento() { return nascimento; }
    public void setNascimento(String nascimento) { this.nascimento = nascimento; }

    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }
}

