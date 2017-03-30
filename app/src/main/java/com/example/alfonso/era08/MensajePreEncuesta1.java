package com.example.alfonso.era08;


import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MensajePreEncuesta1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mensaje_pre_encuesta1);
        //Ocultamos la action bar.
        getSupportActionBar().hide();



        //Aplico la fuente futura medium.
        TextView txt = (TextView) findViewById(R.id.TxtTextoMensajePreEncuesta1);

        ImageButton btnSiguiente = (ImageButton) findViewById(R.id.BtnSiguiente);

        RadioGroup rgpPaginas = (RadioGroup) findViewById(R.id.RgpPagina);

        RadioButton rbtnPagina1 = (RadioButton) findViewById(R.id.RbtnPagina1);
        RadioButton rbtnPagina2 = (RadioButton) findViewById(R.id.RbtnPagina2);
        RadioButton rbtnPagina3 = (RadioButton) findViewById(R.id.RbtnPagina3);
        RadioButton rbtnPagina4 = (RadioButton) findViewById(R.id.RbtnPagina4);







        //Marcamos la pagina actual por defecto.
        rbtnPagina1.setChecked(true);


        //Nos vamos a la pagina 2 al pulsar el buton de radio
        rbtnPagina2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creamos el Intent
                Intent intent =
                        new Intent(MensajePreEncuesta1.this, MensajePreEncuesta2.class);


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
                        new Intent(MensajePreEncuesta1.this, MensajePreEncuesta3.class);


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
                        new Intent(MensajePreEncuesta1.this, MensajePreEncuesta4.class);


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
                        new Intent(MensajePreEncuesta1.this, MensajePreEncuesta2.class);


                //Iniciamos la nueva actividad
                startActivity(intent);
            }
        });






    }




}
