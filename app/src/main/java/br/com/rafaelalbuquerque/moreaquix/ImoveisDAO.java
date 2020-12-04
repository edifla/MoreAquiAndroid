package br.com.rafaelalbuquerque.moreaquix;
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
      **PARTE 3 E 2 (SALVANDO NO BANCO E ENVIANDO PARA O SERVIDOR) MANIPULAÇÃO DO BANCO
  */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;


class ImoveisDAO {
    /*
            Declarando as variaveis
     */
    private SQLiteDatabase database;
    private String[] columns = {EstatesData._ID, EstatesData.SIZE,EstatesData.TYPE,EstatesData.PHONE,EstatesData.STATUS};
    private EstatesData sqliteOpenHelper;
    public ImoveisDAO(Context context) {
        sqliteOpenHelper = new EstatesData(context);
    }

    public void open() throws SQLException {
        database = sqliteOpenHelper.getWritableDatabase();
    }

    public void close() {
        sqliteOpenHelper.close();
    }
    /*
            Inserindo no banco através da função LocationEstate
     */
    public LocationEstate create(LocationEstate e) {

        database = sqliteOpenHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        // inserindo  os valores na tabela
        values.put(EstatesData.TYPE, e.TYPE);
        values.put(EstatesData.SIZE, e.SIZE);
        values.put(EstatesData.PHONE, e.PHONE);
        values.put(EstatesData.STATUS, e.STATUS);
        values.put(EstatesData.LATITUDE, e.LATITUDE);
        values.put(EstatesData.LONGITUDE, e.LONGITUDE);
        long insertId = database.insert(EstatesData.TABLE_NAME, null, values);

        // realizando leitura do Estate inserido
        Cursor cursor = database.query(EstatesData.TABLE_NAME,
                columns, EstatesData._ID + " = " + insertId, null, null, null, null);
        cursor.moveToFirst();

        // criando um Estate para retornar
        LocationEstate novoEstate = new LocationEstate(e.SIZE,e.TYPE,e.PHONE,e.STATUS,e.LATITUDE,e.LONGITUDE);
        cursor.close();
        return novoEstate;
    }



    public static List<LocationEstate> puxarlista(@NonNull SQLiteDatabase databaseb){
        List<LocationEstate> listagem = new ArrayList<LocationEstate>();

        try {
            //Fazendo query select
            Cursor cursor = databaseb.rawQuery("SELECT * FROM "+ EstatesData.TABLE_NAME+";", null);
            //Movendo o curso para o primeiro da lista
            if(cursor.moveToFirst()){
                do{
                    //Criando objeto para por os dados
                    LocationEstate data = new LocationEstate(
                            cursor.getString(cursor.getColumnIndex(EstatesData.TYPE)),
                            cursor.getString(cursor.getColumnIndex(EstatesData.SIZE)),
                            cursor.getString(cursor.getColumnIndex(EstatesData.PHONE)),
                            cursor.getString(cursor.getColumnIndex(EstatesData.STATUS)),
                            cursor.getDouble(cursor.getColumnIndex(EstatesData.LATITUDE)),
                            cursor.getDouble(cursor.getColumnIndex(EstatesData.LONGITUDE)));
                    //Adicionando a lista
                    listagem.add(data);
                    //Alterando para próxima linha
                }while(cursor.moveToNext());

                return listagem;

            }else{

                return null;

            }
        }catch (Exception e){
            Log.e("Erro ao obter lista: ", e.toString());

            return null;

        }
    }
}
