package com.example.nossotcc.controller;

import com.example.nossotcc.model.Dado;

import java.util.ArrayList;
import java.util.List;

public class DadoController {
    private static final List<Dado> listaDados = new ArrayList<>();

    public static void adicionarDado(String nome, float valor) {
        listaDados.add(new Dado(nome, valor));
    }

    public static List<Dado> getDados() {
        return listaDados;
    }
}

