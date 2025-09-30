package com.example.nossotcc.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.nossotcc.datamodel.GastoDataModel;
import com.example.nossotcc.datamodel.MetaDataModel;
import com.example.nossotcc.datamodel.UsuarioDataModel;
import com.example.nossotcc.model.Usuario;

public class AppDataBase extends SQLiteOpenHelper {

    private static final String DB_NAME = "MVC.sqlite";
    private static final int VERSION = 3;

    public AppDataBase(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(UsuarioDataModel.criarTabela());
        db.execSQL(MetaDataModel.criarTabela());
        db.execSQL(GastoDataModel.criarTabela());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + UsuarioDataModel.TABELA);
        db.execSQL("DROP TABLE IF EXISTS " + MetaDataModel.TABELA);
        db.execSQL("DROP TABLE IF EXISTS " + GastoDataModel.TABELA);
        onCreate(db);
    }

    public boolean insert(String tabela, ContentValues dados) {
        SQLiteDatabase db = getWritableDatabase();
        boolean retorno = false;
        try {
            retorno = db.insert(tabela, null, dados) > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retorno;
    }

    public boolean checkUserPassword(String email, String password) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + UsuarioDataModel.TABELA +
                        " WHERE " + UsuarioDataModel.EMAIL + " = ? AND " + UsuarioDataModel.SENHA + " = ?",
                new String[]{email, password}
        );
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public boolean checkUser(String username) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + UsuarioDataModel.TABELA + " WHERE email = ?",
                new String[]{username}
        );
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }


    public Usuario buscar(int id) {
        return null;
    }
}
