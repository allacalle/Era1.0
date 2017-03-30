package com.example.alfonso.era08;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.RadioButton;

public class MensajePreEncuesta2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Ocultamos la Action Bar para esta actividad
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_mensaje_pre_encuesta2);
        //Ocultamos la action bar.
        getSupportActionBar().hide();

        //Ocultamos la action bar.
        //getSupportActionBar().hide();

        //Typeface font = Typeface.createFromAsset(getAssets(), "16020_FUTURAM.ttf");
        //txt.setTypeface(font);

        //Obtenemos una referencia a los controles de la interfaz
        //
        ImageButton btnSiguiente = (ImageButton)findViewById(R.id.BtnSiguiente);
        ImageButton btnAnterior = (ImageButton)findViewById(R.id.BtnAnterior);

        RadioButton rbtnPagina1 = (RadioButton) findViewById(R.id.RbtnPagina1);
        RadioButton rbtnPagina2 = (RadioButton) findViewById(R.id.RbtnPagina2);
        RadioButton rbtnPagina3 = (RadioButton) findViewById(R.id.RbtnPagina3);
        RadioButton rbtnPagina4 = (RadioButton) findViewById(R.id.RbtnPagina4);

        //Marcamos la pagina actual por defecto.
        rbtnPagina2.setChecked(true);





        //Nos vamos a la pagina 1 al pulsar el buton de radio
        rbtnPagina1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creamos el Intent
                Intent intent =
                        new Intent(MensajePreEncuesta2.this, MensajePreEncuesta1.class);


                //Iniciamos la nueva actividad
                startActivity(intent);
            }
        });

        //Nos vamos a la pagina 3 al pulsar el buton de radio
        rbtnPagina3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creamos el Intent
                Intent intent =
                        new Intent(MensajePreEncuesta2.this, MensajePreEncuesta3.class);


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
                        new Intent(MensajePreEncuesta2.this, MensajePreEncuesta4.class);


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
                        new Intent(MensajePreEncuesta2.this, MensajePreEncuesta3.class);


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
                        new Intent(MensajePreEncuesta2.this, MensajePreEncuesta1.class);


                //Iniciamos la nueva actividad
                startActivity(intent);
            }
        });







    }


}
