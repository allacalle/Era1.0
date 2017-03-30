package com.example.alfonso.era08;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

/**
 * Created by Alfonso on 08/02/2016.
 * Ultima modificación: 02/08/2016

 */


public class MensajePostEncuesta extends AppCompatActivity {

    private ImageButton btnInicio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mensaje_post_encuesta);

        btnInicio = (ImageButton)findViewById(R.id.BtnInicio);



        //Implementamos el evento click del botón
        btnInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creamos el Intent
                Intent intent =
                        new Intent(MensajePostEncuesta.this, Inicio.class);


                //Iniciamos la nueva actividad
                startActivity(intent);
            }
        });


    }
}
