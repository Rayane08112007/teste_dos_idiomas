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
        return "CREATE TABLE " + TABELA + " (" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DESCRICAO + " TEXT, " +
                VALOR + " REAL, " +
                CATEGORIA + " TEXT, " +
                DATA + " TEXT, " +
                FORMA + " TEXT, " +
                OBS + " TEXT)";
    }
}
