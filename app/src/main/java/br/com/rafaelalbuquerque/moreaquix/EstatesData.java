package br.com.rafaelalbuquerque.moreaquix;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;
/*
**CENTRO UNIVERSITÁRIO DOS GUARARAPES - UNIFG
* DESENVOLVIMENTO PARA DISPOSITIVOS MOVEIS
* PROJETO DE APLICATIVO
*
*       AUTORES:
       -Rafael Albuquerque da Costa
       -Lucas Veras
       -Yasmin Oliveira
       -Gerlandio Barbosa
       -Vinicius Adriano
       -Gabriel Azevedo
       *
     **VERSAO: 1.0
     **PARTE 2 CRIAÇÃO DO BANCO
 */
public class EstatesData extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "moreaqui4database";
    public static final int DATABASE_VERSION = 1;
    public static final String _ID = "id",TYPE = "tipo" ,SIZE = "tamanho",PHONE = "contato",STATUS = "detalhes",LATITUDE = "latitude",LONGITUDE = "longitude",TABLE_NAME = "imoveis";
    String DATABASE_CREATE = "CREATE TABLE " + TABLE_NAME + " (" +
            _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            TYPE + " TEXT," +
            SIZE + " TEXT," +
            PHONE + " TEXT," +
            LATITUDE + " DOUBLE," +
            LONGITUDE + " DOUBLE," +
            STATUS + " TEXT);";
    public EstatesData(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    //Retornar o banco de dados
    public SQLiteDatabase Open(){
        return getWritableDatabase();
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

}
