package com.example.nossotcc.datamodel;



public class MetaDataModel {
    public static final String TABELA = "metas";
    public static final String ID = "id";
    public static final String TITULO = "titulo";
    public static final String DESCRICAO = "descricao";
    public static final String VALOR = "valor";

    public static String criarTabela() {
        return "CREATE TABLE " + TABELA + " (" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                TITULO + " TEXT," +
                DESCRICAO + " TEXT," +
                VALOR + " REAL)";
    }
}

