package com.example.nossotcc.model;

public class Meta {
    private int id;
    private String titulo;
    private String descricao;
    private double valor;

    public Meta() {}

    public Meta(int id, String titulo, String descricao, double valor) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.valor = valor;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public double getValor() { return valor; }
    public void setValor(double valor) { this.valor = valor; }

    @Override
    public String toString() {
        return id + " - " + titulo + " - " + descricao + " - R$" + valor;
    }
}


