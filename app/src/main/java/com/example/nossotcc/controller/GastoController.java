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

public class GastoController extends AppDataBase {

    SQLiteDatabase db;

    public GastoController(Context context) {
        super(context);
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
        db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + GastoDataModel.TABELA, null);

        if (cursor.moveToFirst()) {
            do {
                Gasto gasto = new Gasto();
                gasto.setId(cursor.getInt(cursor.getColumnIndexOrThrow(GastoDataModel.ID)));
                gasto.setDescricao(cursor.getString(cursor.getColumnIndexOrThrow(GastoDataModel.DESCRICAO)));
                gasto.setValor(cursor.getDouble(cursor.getColumnIndexOrThrow(GastoDataModel.VALOR)));
                gasto.setCategoria(cursor.getString(cursor.getColumnIndexOrThrow(GastoDataModel.CATEGORIA)));
                gasto.setData(cursor.getString(cursor.getColumnIndexOrThrow(GastoDataModel.DATA)));
                gasto.setFormaPagamento(cursor.getString(cursor.getColumnIndexOrThrow(GastoDataModel.FORMA)));
                gasto.setObservacoes(cursor.getString(cursor.getColumnIndexOrThrow(GastoDataModel.OBS)));

                lista.add(gasto);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return lista;
    }
    public ArrayList<Gasto> listarPorMes(String mesAno) {
        ArrayList<Gasto> lista = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + GastoDataModel.TABELA +
                " WHERE " + GastoDataModel.DATA + " LIKE ?", new String[]{mesAno + "%"});
        if(cursor.moveToFirst()) {
            do {
                Gasto gasto = new Gasto();
                gasto.setId(cursor.getInt(cursor.getColumnIndexOrThrow(GastoDataModel.ID)));
                gasto.setDescricao(cursor.getString(cursor.getColumnIndexOrThrow(GastoDataModel.DESCRICAO)));
                gasto.setValor(cursor.getDouble(cursor.getColumnIndexOrThrow(GastoDataModel.VALOR)));
                gasto.setCategoria(cursor.getString(cursor.getColumnIndexOrThrow(GastoDataModel.CATEGORIA)));
                gasto.setData(cursor.getString(cursor.getColumnIndexOrThrow(GastoDataModel.DATA)));
                gasto.setFormaPagamento(cursor.getString(cursor.getColumnIndexOrThrow(GastoDataModel.FORMA)));
                gasto.setObservacoes(cursor.getString(cursor.getColumnIndexOrThrow(GastoDataModel.OBS)));
                lista.add(gasto);
            } while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return lista;
    }

    @Override
    public Usuario buscar(int id) {
        return null;
    }
}



