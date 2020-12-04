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
      **PARTE 4 E 5 IMPLEMENTAÇÃO E MANIPULAÇÃO DO  MAPA
  */
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import android.database.Cursor;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap minimapa;
    private Cursor ponteiro;
    private EstatesData db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        db = new EstatesData(MapsActivity.this);
        ponteiro = db.getReadableDatabase().rawQuery("SELECT * FROM "
                + db.TABLE_NAME, null);


    }

        //Quando o mapa for iniciado irá percorrer o banco e adicionar as marcações nas Latitudes e longitudes encontradas.
    @Override
    public void onMapReady(GoogleMap googleMap) {
        minimapa = googleMap;
        //Habilitando o Zoom
        UiSettings uiSettings = minimapa.getUiSettings();
        uiSettings.setCompassEnabled(false);
        uiSettings.setZoomControlsEnabled(true);
        uiSettings.setMyLocationButtonEnabled(false);

        //Irá pegar o primeiro dado do banco para iniciar o mapa
        ponteiro.moveToFirst();
        String TamanahoI = ponteiro.getString(ponteiro.getColumnIndex(db.SIZE));
        String TipoCAsaI = ponteiro.getString(ponteiro.getColumnIndex(db.TYPE));
        String ContatoI = ponteiro.getString(ponteiro.getColumnIndex(db.PHONE));
        double cordenadaLatI= ponteiro.getDouble(ponteiro.getColumnIndex(db.LATITUDE));
        double cordenadaLongI = ponteiro.getDouble(ponteiro.getColumnIndex(db.LONGITUDE));
        LatLng markInicial = new LatLng(cordenadaLatI, cordenadaLongI);
        minimapa.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.marcador)).position(markInicial).title(TipoCAsaI + " - " + TamanahoI + " - " + ContatoI));

        //Move o mapa para a localização
        minimapa.moveCamera(CameraUpdateFactory.newLatLngZoom(markInicial, 15));
            //Feito usando a logica de listagem do android PRJ_17
            while (ponteiro.moveToNext()){
                String Tamanaho = ponteiro.getString(ponteiro.getColumnIndex(db.SIZE));
                String TipoCAsa = ponteiro.getString(ponteiro.getColumnIndex(db.TYPE));
                String Contato = ponteiro.getString(ponteiro.getColumnIndex(db.PHONE));
                double cordenadaLat = ponteiro.getDouble(ponteiro.getColumnIndex(db.LATITUDE));
                double cordenadaLong = ponteiro.getDouble(ponteiro.getColumnIndex(db.LONGITUDE));
                //Adicionando as marcações
                LatLng marks = new LatLng(cordenadaLat , cordenadaLong);
                minimapa.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.marcador)).position(marks).title(TipoCAsa + " - " + Tamanaho + " - " + Contato));

            }
    }

}