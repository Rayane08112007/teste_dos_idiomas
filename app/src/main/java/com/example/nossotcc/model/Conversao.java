package com.example.nossotcc.model;

public class Conversao {
    private String moedaOrigem;
    private double valorOriginal;
    private double valorConvertido;

    public Conversao(String moedaOrigem, double valorOriginal) {
        this.moedaOrigem = moedaOrigem;
        this.valorOriginal = valorOriginal;
    }

    public String getMoedaOrigem() {
        return moedaOrigem;
    }

    public double getValorOriginal() {
        return valorOriginal;
    }

    public double getValorConvertido() {
        return valorConvertido;
    }

    public void setValorConvertido(double valorConvertido) {
        this.valorConvertido = valorConvertido;
    }
}
