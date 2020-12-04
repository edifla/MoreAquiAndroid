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
      **MAIN ACTIVITY PARTE 1 TELA PRINCIPAL
  */
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

public class MoreAqui5Activity extends AppCompatActivity{
    private ImageView btn_novo;
    private ImageView btn_visu;
    private ImageView btn_mapa;

    private static EstatesData db;
    private String[] requerimentos = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_novo = findViewById(R.id.btn_novo);
        btn_mapa = findViewById(R.id.btn_mapa);
        btn_visu = findViewById(R.id.btn_visu);
        db = new EstatesData(getBaseContext());
        btn_novo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MoreAqui5Activity.this, InsertActivity.class);
                startActivity(intent);
            }

        });
        btn_visu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MoreAqui5Activity.this, Visualizar.class);
                startActivity(intent);
            }

        });
        btn_mapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MoreAqui5Activity.this, MapsActivity.class);
                startActivity(intent);
            }

        });
        Requerimentos.validarRequerimentos(requerimentos, this, 1);


    }
        public void enviarservidor(View v){
            try {
                List<LocationEstate> estates = ImoveisDAO.puxarlista(db.Open());

                new RecordData().execute(estates);
                Toast.makeText(this, "Funcionou", Toast.LENGTH_SHORT).show();

                db.close();
            }catch(Exception e){
                Toast.makeText(this, "Nao pego", Toast.LENGTH_SHORT).show();
            }
        }

}