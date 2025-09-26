package com.example.nossotcc.datamodel;

public class GastoDataModel {

    public static final String TABELA = "gastos";

    public static final String ID = "id";
    public static final String DESCRICAO = "descricao";
    public static final String VALOR = "valor";
    public static final String CATEGORIA = "categoria";
    public static final String DATA = "data";
    public static final String FORMA = "forma_pagamento";
    public static final String OBS = "observacoes";

    public static String criarTabela() {
        String queryCriarTabela = "";

        queryCriarTabela += "CREATE TABLE " + TABELA + " (";
        queryCriarTabela += ID + " INTEGER PRIMARY KEY AUTOINCREMENT, ";
        queryCriarTabela += DESCRICAO + " TEXT, ";
        queryCriarTabela += VALOR + " REAL, ";
        queryCriarTabela += CATEGORIA + " TEXT, ";
        queryCriarTabela += DATA + " TEXT, ";
        queryCriarTabela += FORMA + " TEXT, ";
        queryCriarTabela += OBS + " TEXT";
        queryCriarTabela += ")";

        return queryCriarTabela;
    }
}


