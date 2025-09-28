package com.example.nossotcc.datamodel;

public class MetasDataModel {

    public static final String TABELA = "metas";

    public static final String ID = "id";
    public static final String DESCRICAO = "descricao";
    public static final String CONCLUIDA = "concluida";

    public static String criarTabela() {
        String queryCriarTabela = "";

        queryCriarTabela += "CREATE TABLE " + TABELA + " (";
        queryCriarTabela += ID + " INTEGER PRIMARY KEY AUTOINCREMENT, ";
        queryCriarTabela += DESCRICAO + " TEXT, ";
        queryCriarTabela += CONCLUIDA + " INTEGER";
        queryCriarTabela += ")";

        return queryCriarTabela;
    }
}
