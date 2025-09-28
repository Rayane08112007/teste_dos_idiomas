package com.example.nossotcc.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.example.nossotcc.database.AppDataBase;
import com.example.nossotcc.datamodel.GastoDataModel;
import com.example.nossotcc.model.Gasto;
import com.example.nossotcc.model.Usuario;

import java.util.ArrayList;
import java.util.HashMap;

public class GastoController extends AppDataBase {

    public GastoController(Context context) {
        super(context);
    }

    @Override
    public Usuario buscar(int id) {
        return null;
    }

    public boolean inserir(Gasto gasto) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put(GastoDataModel.DESCRICAO, gasto.getDescricao());
        valores.put(GastoDataModel.VALOR, gasto.getValor());
        valores.put(GastoDataModel.CATEGORIA, gasto.getCategoria());
        valores.put(GastoDataModel.DATA, gasto.getData());
        valores.put(GastoDataModel.FORMA, gasto.getFormaPagamento());
        valores.put(GastoDataModel.OBS, gasto.getObservacoes());
        long resultado = db.insert(GastoDataModel.TABELA, null, valores);
        db.close();
        return resultado > 0;
    }

    public ArrayList<Gasto> listar() {
        ArrayList<Gasto> lista = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + GastoDataModel.TABELA, null);
        if (cursor.moveToFirst()) {
            do {
                Gasto g = new Gasto();
                g.setId(cursor.getInt(cursor.getColumnIndexOrThrow(GastoDataModel.ID)));
                g.setDescricao(cursor.getString(cursor.getColumnIndexOrThrow(GastoDataModel.DESCRICAO)));
                g.setValor(cursor.getDouble(cursor.getColumnIndexOrThrow(GastoDataModel.VALOR)));
                g.setCategoria(cursor.getString(cursor.getColumnIndexOrThrow(GastoDataModel.CATEGORIA)));
                g.setData(cursor.getString(cursor.getColumnIndexOrThrow(GastoDataModel.DATA)));
                g.setFormaPagamento(cursor.getString(cursor.getColumnIndexOrThrow(GastoDataModel.FORMA)));
                g.setObservacoes(cursor.getString(cursor.getColumnIndexOrThrow(GastoDataModel.OBS)));
                lista.add(g);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return lista;
    }

    public ArrayList<Gasto> listarPorMes(String mesAno) {
        ArrayList<Gasto> lista = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + GastoDataModel.TABELA + " WHERE " + GastoDataModel.DATA + " LIKE ?",
                new String[]{mesAno + "%"}
        );
        if (cursor.moveToFirst()) {
            do {
                Gasto g = new Gasto();
                g.setId(cursor.getInt(cursor.getColumnIndexOrThrow(GastoDataModel.ID)));
                g.setDescricao(cursor.getString(cursor.getColumnIndexOrThrow(GastoDataModel.DESCRICAO)));
                g.setValor(cursor.getDouble(cursor.getColumnIndexOrThrow(GastoDataModel.VALOR)));
                g.setCategoria(cursor.getString(cursor.getColumnIndexOrThrow(GastoDataModel.CATEGORIA)));
                g.setData(cursor.getString(cursor.getColumnIndexOrThrow(GastoDataModel.DATA)));
                g.setFormaPagamento(cursor.getString(cursor.getColumnIndexOrThrow(GastoDataModel.FORMA)));
                g.setObservacoes(cursor.getString(cursor.getColumnIndexOrThrow(GastoDataModel.OBS)));
                lista.add(g);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return lista;
    }
}
