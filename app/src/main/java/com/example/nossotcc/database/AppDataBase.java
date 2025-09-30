package com.example.nossotcc.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.example.nossotcc.datamodel.MetaDataModel;
import com.example.nossotcc.datamodel.UsuarioDataModel;
import com.example.nossotcc.model.Usuario;


public abstract class AppDataBase extends SQLiteOpenHelper {

    SQLiteDatabase db;

    private static final String DB_NAME = "MVC.sqlite";
    private static final int version = 2;

    public AppDataBase(Context context) {
        super(context, DB_NAME, null, version);
        db = getWritableDatabase();
        boolean retorno = false;

    }

    public boolean insert (String tabela, ContentValues dados) {
        db = getWritableDatabase();
        boolean retorno = false;

        try {
            retorno = db.insert(tabela, null, dados) > 0;
        } catch (Exception e) {
            e.getMessage();
        }

        return retorno;
    }
    public boolean checkUserPassword (String email,String password){
        db = getWritableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + UsuarioDataModel.TABELA +
                        " WHERE " + UsuarioDataModel.EMAIL + " = ? AND " + UsuarioDataModel.SENHA + " = ?",
                new String[] {email, password}
        );
        return cursor.getCount() > 0;
    }

    public boolean checkUser (String username){
        db =getWritableDatabase();
        boolean retorno = false;
        Cursor cursor = db.rawQuery(" SELECT * FROM " + UsuarioDataModel.TABELA + "  WHERE email = ? ",  new String[]{username} );
        return cursor.getCount() > 0;
    }

    @Override
    public void onCreate (SQLiteDatabase db){
        db.execSQL(UsuarioDataModel.criarTabela());
        db.execSQL(MetaDataModel.criarTabela());
    }

    @Override
    public void onUpgrade (SQLiteDatabase db,int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + UsuarioDataModel.TABELA);
        db.execSQL("DROP TABLE IF EXISTS " + MetaDataModel.TABELA);
//        db.execSQL("DROP TABLE IF EXISTS " + DocumentoDataModel.TABELA);
        onCreate(db);
    }


    public abstract Usuario buscar(int id);
}