package com.example.nossotcc.model;

public class Gasto {
    private int id;
    private String descricao;
    private double valor;
    private String categoria;
    private String data;
    private String formaPagamento;
    private String observacoes;

    public Gasto() {}

    public Gasto(int id, String descricao, double valor, String categoria, String data, String formaPagamento, String observacoes) {
        this.id = id;
        this.descricao = descricao;
        this.valor = valor;
        this.categoria = categoria;
        this.data = data;
        this.formaPagamento = formaPagamento;
        this.observacoes = observacoes;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public double getValor() { return valor; }
    public void setValor(double valor) { this.valor = valor; }
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    public String getData() { return data; }
    public void setData(String data) { this.data = data; }
    public String getFormaPagamento() { return formaPagamento; }
    public void setFormaPagamento(String formaPagamento) { this.formaPagamento = formaPagamento; }
    public String getObservacoes() { return observacoes; }
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }
}

