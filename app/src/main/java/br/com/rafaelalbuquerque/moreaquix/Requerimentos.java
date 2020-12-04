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
      **PARTE 4 REQUISIÇÃO DE PERMIÇÃO PARA O USUARIO
  */
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;
/*
    Requerimentos para permitir localização ao app

    Código retirado dos vídeos do professor @Tiago Aguiar
    https://www.youtube.com/playlist?list=PLJ0AcghBBWSi97S8_xQP7dv6D_VHTt8k5

 */
public class Requerimentos {

    public static boolean validarRequerimentos(String[] requerimentos, Activity activity, int requestCode){

        if (Build.VERSION.SDK_INT >= 23 ){
            List<String> listaPermissoes = new ArrayList<>();
            for ( String permissao : requerimentos ){
                Boolean temPermissao = ContextCompat.checkSelfPermission(activity, permissao) == PackageManager.PERMISSION_GRANTED;
                if ( !temPermissao ) listaPermissoes.add(permissao);
            }
            if ( listaPermissoes.isEmpty() ) return true;
            String[] novasPermissoes = new String[ listaPermissoes.size() ];
            listaPermissoes.toArray( novasPermissoes );
            ActivityCompat.requestPermissions(activity, novasPermissoes, requestCode );
        }
        return true;
    }
}
