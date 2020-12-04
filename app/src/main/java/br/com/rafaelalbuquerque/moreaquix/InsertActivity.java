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
      **PARTE 1 COLETA DE DADOS (INSERT ACTIVITY)
  */
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;
import android.widget.CheckBox;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;


public class InsertActivity extends AppCompatActivity {
    //Variaveis de manipulação do banco
    private EstatesData helper;
    private ImoveisDAO DAO;
    private Cursor ponteiro;
    //Variaveis da Create para testes
    private EstatesData sqliteOpenHelper;
    private SQLiteDatabase database;
    private String[] columns = {EstatesData._ID, EstatesData.SIZE,EstatesData.TYPE,EstatesData.PHONE,EstatesData.STATUS};
    //Coletando os Widget do xml
    private EditText numero;
    private ImageView confirma;
    private CheckBox construcao;
    //Variaveis de validações
    private boolean checked = false;
    private boolean checked2 = false;
    //Variaveis armazenando o conteudo escolhido pelo usuário
    private boolean conf;
    public String type;
    public String size;
    public String inConstruction = "Nao";
    public String phone;
    // Mapa
    private FusedLocationProviderClient client;
    public double latitude;
    public double longitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_local);
        numero = (EditText) findViewById(R.id.contato);
        confirma = (ImageView) findViewById(R.id.confirma);
        construcao = (CheckBox) findViewById(R.id.construcao);
        client = LocationServices.getFusedLocationProviderClient(this);
        helper = new EstatesData(InsertActivity.this);
        DAO = new ImoveisDAO(this);
        ponteiro = helper.getReadableDatabase().rawQuery("SELECT * FROM "
                + helper.TABLE_NAME, null);

        pegarLocalizacao();
    }

        //Pegando a opção marcada no Radio de tipos
    public void onRadioButtonClicked(View v) {
        //Verifica se o algum dos radios foram selecionados
        checked = ((RadioButton) v).isChecked();

        // Verifica qual dos radios foi escolhido
        switch (v.getId()) {
            case R.id.rdcasa:
                if (checked) {
                    type = "Casa";
                }
                break;
            case R.id.rdapartamento:
                if (checked) {
                    type = "Ap";
                }
                break;
            case R.id.rdloja:
                if (checked) {
                    type = "Loja";
                }
                break;
            case R.id.rdnaosei:
                if (checked) {
                    type = "Não soube";
                }
                break;
        }
    }
        //Pegando a opção marcada no Radio de tamanho
    public void onRadioButtonClicked2(View v) {
        // Verifica se o algum dos radios foram selecionados
        checked2 = ((RadioButton) v).isChecked();
        // Verifica qual dos radios foi escolhido
        switch (v.getId()) {
            case R.id.rd_pequeno:
                if (checked2) {
                    size = "Pequeno";
                }
                break;
            case R.id.rd_medio:
                if (checked2) {
                    size = "Medio";
                }
                break;
            case R.id.rd_grande:
                if (checked2) {
                    size = "Grande";
                }
                break;
            case R.id.rdnaosei2:
                if (checked2) {
                    size = "Indefinido";
                }
                break;
        }
    }
             //Pegando a opção marcada no checkbox
    public void checagem(View v){
        conf =((CheckBox)v).isChecked();
        if(conf){
            //Setei o valor do Construction quando ele Selecionar
            inConstruction = "Sim";
        } else{
            //inserindo valor do Construction!
            inConstruction = "não";
        }
    }
    /*
    Código retirado e adaptado dos vídeos do professor @Tiago Aguiar
    https://www.youtube.com/playlist?list=PLJ0AcghBBWSi97S8_xQP7dv6D_VHTt8k5
     */
    public void pegarLocalizacao(){
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        client.getLastLocation()
                .addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                            Log.i("Teste", latitude + "" +  longitude);
                        }else{
                            Log.i("Teste", "nada");
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }
        //Validações
    public void validar(View v) {

        if (numero.length() == 11 && checked && checked2) {
            enviar();
            Intent intent = new Intent(InsertActivity.this, MoreAqui5Activity.class);
            startActivity(intent);
        } else if (numero.length() == 11 && checked && !checked2) {
            Toast.makeText(this, "Você deve preencher os 2 campos.", Toast.LENGTH_SHORT).show();
        } else if (numero.length() == 11 && !checked && checked2) {
            Toast.makeText(this, "Você deve marcar preencher os 2 campos.", Toast.LENGTH_SHORT).show();
        } else if (numero.length() == 0 && checked && checked2) {
            Toast.makeText(this, "Você deve inserir um número para contato.", Toast.LENGTH_SHORT).show();
        } else if (numero.length() > 0 && numero.length() < 11 && checked && checked2) {
            Toast.makeText(this, "Você deve inserir um número de telefone valido.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Preencha os campos corretamente!", Toast.LENGTH_SHORT).show();
        }
    }
    public void enviar(){
        phone = "" + numero.getText();
        LocationEstate e = new LocationEstate(type,size,phone,inConstruction,latitude,longitude);
        DAO.create(e);
    }


}