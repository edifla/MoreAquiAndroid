package br.com.rafaelalbuquerque.moreaquix;

import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

public class RecordData extends AsyncTask<List<LocationEstate>, Void, Void> {

    private Exception erro;

    @Override
    protected Void doInBackground(List<LocationEstate>... lists){

        DaoImpl dao = new DaoImpl();

        SendData sendData = new SendData();

        Invoker invoker = new Invoker(DAO.HOST, DAO.PORT);

        for(LocationEstate estate : lists[0]){
            sendData.setKey(Long.valueOf(estate.hashCode()));
            sendData.setValue(estate.toString());

            Log.v("Serializable", String.valueOf(sendData.getKey()));

            invoker.invoke(dao, sendData);
        }

        return null;
    }
}