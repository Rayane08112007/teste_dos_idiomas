package com.example.nossotcc.datamodel;

public class UsuarioDataModel {
    public static final String TABELA = "usuarios";
    public static final String ID = "id";
    public static final String NOME = "nome";
    public static final String EMAIL = "email";
    public static String NACIONALIDADE = "nacionalidade";
    public static String NASCIMENTO = "nascimento";
    public static final String SENHA = "senha";
    public static String GENERO = "genero";
    public static String queryCriarTabela = "";


    public static String criarTabela() {
        queryCriarTabela += "CREATE TABLE " + TABELA + "(";
        queryCriarTabela += "ID " + "INTEGER PRIMARY KEY AUTOINCREMENT, ";
        queryCriarTabela += "NOME " + "text, ";
        queryCriarTabela += "EMAIL " + "text, ";
        queryCriarTabela += "NACIONALIDADE " + "text, ";
        queryCriarTabela += "NASCIMENTO " + "text, ";
        queryCriarTabela += "SENHA " + "text, ";
        queryCriarTabela += "GENERO " + "text";
        queryCriarTabela += ")";

        return queryCriarTabela;

    }
}


