package br.com.rafaelalbuquerque.moreaquix;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;
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
      **PARTE 2 EXIBIR VALORES
  */
public class Visualizar extends AppCompatActivity {

    private ListView lstimoveis;
    private Cursor wordsCursor;
    private EstatesData helper;
    private List<String> listaDeImoveis;
    private ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar);

        helper = new EstatesData(Visualizar.this);
        wordsCursor = helper.getReadableDatabase().rawQuery("SELECT * FROM "
                + helper.TABLE_NAME, null);

        lstimoveis = findViewById(R.id.lstimoveis);

        this.coleta();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();

        wordsCursor.close();
        helper.close();
    }
    //Codigo adaptado do android Prj_18
    public void coleta() {

        // cria a lista, caso ela não esteja criada
        if (listaDeImoveis == null) {
            listaDeImoveis = new ArrayList<>();

        } else {
            // limpa a sua lista
            listaDeImoveis.clear();

        }
        // aramazena os dados da busca na lista
        while (wordsCursor.moveToNext()) {
            String size = wordsCursor.getString(wordsCursor.getColumnIndex(helper.SIZE));
            String type = wordsCursor.getString(wordsCursor.getColumnIndex(helper.TYPE));
            String phone = wordsCursor.getString(wordsCursor.getColumnIndex(helper.PHONE));
            String status = wordsCursor.getString(wordsCursor.getColumnIndex(helper.STATUS));
            listaDeImoveis.add("Tipo: "+ type + ", Tamanho: "+ size + ", Contato: " + phone + ", Em construção : " + status);

        }
        // se o adapter for nulo, cria o adapter
        if (adapter == null) {
            adapter = new ArrayAdapter(Visualizar.this,
                    android.R.layout.simple_list_item_1,
                    android.R.id.text1, listaDeImoveis);
            lstimoveis.setAdapter(adapter);
        } else {
            // se não notifica que sua lista teve alteração
            adapter.notifyDataSetChanged();
        }
    }
}