package com.example.alfonso.era08;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;

public class MensajePreEncuesta3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mensaje_pre_encuesta3);
        //Ocultamos la action bar.
        getSupportActionBar().hide();

        ImageButton btnSiguiente = (ImageButton)findViewById(R.id.BtnSiguiente);
        ImageButton btnAnterior = (ImageButton)findViewById(R.id.BtnAnterior);


        RadioButton rbtnPagina1 = (RadioButton) findViewById(R.id.RbtnPagina1);
        RadioButton rbtnPagina2 = (RadioButton) findViewById(R.id.RbtnPagina2);
        RadioButton rbtnPagina3 = (RadioButton) findViewById(R.id.RbtnPagina3);
        RadioButton rbtnPagina4 = (RadioButton) findViewById(R.id.RbtnPagina4);

        //Marcamos la pagina actual por defecto.
        rbtnPagina3.setChecked(true);





        //Nos vamos a la pagina 1 al pulsar el buton de radio
        rbtnPagina1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creamos el Intent
                Intent intent =
                        new Intent(MensajePreEncuesta3.this, MensajePreEncuesta1.class);


                //Iniciamos la nueva actividad
                startActivity(intent);
            }
        });

        //Nos vamos a la pagina 2 al pulsar el buton de radio
        rbtnPagina2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creamos el Intent
                Intent intent =
                        new Intent(MensajePreEncuesta3.this, MensajePreEncuesta2.class);


                //Iniciamos la nueva actividad
                startActivity(intent);
            }
        });


        //Nos vamos a la pagina 4 al pulsar el buton de radio
        rbtnPagina4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creamos el Intent
                Intent intent =
                        new Intent(MensajePreEncuesta3.this, MensajePreEncuesta4.class);


                //Iniciamos la nueva actividad
                startActivity(intent);
            }
        });



        //Implementamos el evento click del botón
        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creamos el Intent
                Intent intent =
                        new Intent(MensajePreEncuesta3.this, MensajePreEncuesta4.class);


                //Iniciamos la nueva actividad
                startActivity(intent);
            }
        });

        //Implementamos el evento click del botón
        btnAnterior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creamos el Intent
                Intent intent =
                        new Intent(MensajePreEncuesta3.this, MensajePreEncuesta2.class);


                //Iniciamos la nueva actividad
                startActivity(intent);
            }
        });



    }






}




