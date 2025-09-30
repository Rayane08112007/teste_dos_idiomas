package com.example.nossotcc.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import com.example.nossotcc.database.AppDataBase;
import com.example.nossotcc.datamodel.GastoDataModel;
import com.example.nossotcc.model.Gasto;
import java.util.ArrayList;
import java.util.List;

public class GastoController extends AppDataBase {

    public GastoController(Context context) {
        super(context);
    }

    public boolean salvar(Gasto gasto) {
        ContentValues valores = new ContentValues();
        valores.put(GastoDataModel.DESCRICAO, gasto.getDescricao());
        valores.put(GastoDataModel.VALOR, gasto.getValor());
        valores.put(GastoDataModel.CATEGORIA, gasto.getCategoria());
        valores.put(GastoDataModel.DATA, gasto.getData());
        valores.put(GastoDataModel.FORMA_PAGAMENTO, gasto.getFormaPagamento());
        valores.put(GastoDataModel.OBSERVACOES, gasto.getObservacoes());
        valores.put(GastoDataModel.RECORRENTE, gasto.isRecorrente() ? 1 : 0);
        valores.put(GastoDataModel.TAG, gasto.getTag());
        return insert(GastoDataModel.TABELA, valores);
    }

    public List<Gasto> listar() {
        List<Gasto> lista = new ArrayList<>();
        Cursor cursor = getWritableDatabase().rawQuery("SELECT * FROM " + GastoDataModel.TABELA, null);

        if (cursor.moveToFirst()) {
            do {
                Gasto g = new Gasto();
                g.setId(cursor.getInt(cursor.getColumnIndexOrThrow(GastoDataModel.ID)));
                g.setDescricao(cursor.getString(cursor.getColumnIndexOrThrow(GastoDataModel.DESCRICAO)));
                g.setValor(cursor.getDouble(cursor.getColumnIndexOrThrow(GastoDataModel.VALOR)));
                g.setCategoria(cursor.getString(cursor.getColumnIndexOrThrow(GastoDataModel.CATEGORIA)));
                g.setData(cursor.getString(cursor.getColumnIndexOrThrow(GastoDataModel.DATA)));
                g.setFormaPagamento(cursor.getString(cursor.getColumnIndexOrThrow(GastoDataModel.FORMA_PAGAMENTO)));
                g.setObservacoes(cursor.getString(cursor.getColumnIndexOrThrow(GastoDataModel.OBSERVACOES)));
                g.setRecorrente(cursor.getInt(cursor.getColumnIndexOrThrow(GastoDataModel.RECORRENTE)) == 1);
                g.setTag(cursor.getString(cursor.getColumnIndexOrThrow(GastoDataModel.TAG)));
                lista.add(g);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return lista;
    }

    @Override
    public com.example.nossotcc.model.Usuario buscar(int id) {
        return null;
    }
}
