package com.example.nossotcc.model;

public class Metas {

    private int id;
    private String descricao;
    private boolean concluida;

    public Metas() {
    }

    public Metas(String descricao, boolean concluida) {
        this.descricao = descricao;
        this.concluida = concluida;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isConcluida() {
        return concluida;
    }

    public void setConcluida(boolean concluida) {
        this.concluida = concluida;
    }
}
