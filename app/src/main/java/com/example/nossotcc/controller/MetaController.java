package com.example.nossotcc.controller;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.nossotcc.database.AppDataBase;
import com.example.nossotcc.datamodel.MetaDataModel;
import com.example.nossotcc.model.Meta;

import java.util.ArrayList;
import java.util.List;

public class MetaController {

    private AppDataBase db;

    public MetaController(Context context) {
        db = new AppDataBase(context) {
            @Override
            public com.example.nossotcc.model.Usuario buscar(int id) {
                return null; // Não usamos aqui
            }
        };
    }


    public boolean adicionarMeta(String titulo, String descricao, double valor) {
        ContentValues cv = new ContentValues();
        cv.put(MetaDataModel.TITULO, titulo);
        cv.put(MetaDataModel.DESCRICAO, descricao);
        cv.put(MetaDataModel.VALOR, valor);
        return db.insert(MetaDataModel.TABELA, cv);
    }


    public List<Meta> listarMetas() {
        List<Meta> lista = new ArrayList<>();
        Cursor cursor = db.getWritableDatabase().query(
                MetaDataModel.TABELA,
                null, null, null, null, null, MetaDataModel.ID + " ASC"
        );

        if (cursor.moveToFirst()) {
            do {
                Meta meta = new Meta();
                meta.setId(cursor.getInt(cursor.getColumnIndexOrThrow(MetaDataModel.ID)));
                meta.setTitulo(cursor.getString(cursor.getColumnIndexOrThrow(MetaDataModel.TITULO)));
                meta.setDescricao(cursor.getString(cursor.getColumnIndexOrThrow(MetaDataModel.DESCRICAO)));
                meta.setValor(cursor.getDouble(cursor.getColumnIndexOrThrow(MetaDataModel.VALOR)));
                lista.add(meta);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return lista;
    }


    public boolean removerMeta(int id) {
        return db.getWritableDatabase().delete(
                MetaDataModel.TABELA,
                MetaDataModel.ID + "=?",
                new String[]{String.valueOf(id)}
        ) > 0;
    }
}



