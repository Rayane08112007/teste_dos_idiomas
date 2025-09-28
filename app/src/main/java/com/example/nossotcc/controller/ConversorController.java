package com.example.nossotcc.controller;

import com.example.nossotcc.model.Conversao;

import java.util.HashMap;
import java.util.Map;

public class ConversorController {

    private Map<String, Double> taxasConversao;

    public ConversorController() {
        taxasConversao = new HashMap<>();
        taxasConversao.put("USD", 5.2);
        taxasConversao.put("EUR", 5.7);
        taxasConversao.put("VES", 0.0003);
        taxasConversao.put("HTG", 0.05);
        taxasConversao.put("CUP", 0.21);
        taxasConversao.put("BOB", 0.72);
        taxasConversao.put("ARS", 0.028);
        taxasConversao.put("CNY", 0.76);
        taxasConversao.put("INR", 0.063);
        taxasConversao.put("AOA", 0.0098);
        // adicione outras moedas que desejar
    }

    public interface ConversaoListener {
        void onSucesso(Conversao conversao);
        void onErro(String mensagem);
    }

    public void converter(Conversao conversao, ConversaoListener listener) {
        Double taxa = taxasConversao.get(conversao.getMoedaOrigem());
        if (taxa == null) {
            listener.onErro("Moeda não suportada");
            return;
        }

        double valorConvertido = conversao.getValorOriginal() * taxa;
        conversao.setValorConvertido(valorConvertido);
        listener.onSucesso(conversao);
    }
}
