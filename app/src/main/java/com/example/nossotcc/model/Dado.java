package com.example.nossotcc.model;

public class Dado {
    private String nome;
    private float valor;

    public Dado(String nome, float valor) {
        this.nome = nome;
        this.valor = valor;
    }

    public String getNome() {
        return nome;
    }

    public float getValor() {
        return valor;
    }
}


