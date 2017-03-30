package com.example.alfonso.era08;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.alfonso.era08.Clases.FormulasSQLiteHelper;


/**
 * Created by Alfonso on 08/02/2016.
 * Ultima modificación: 20/07/2016

 */


public class ResultadosEncuesta extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encuesta);

        //Activo el boton atras
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Recuperamos la información pasada en el intent
        Bundle bundle = this.getIntent().getExtras();
        //Construimos el mensaje a mostrar
        String valorRecibido = bundle.getString("Resultado");
        //creamos el layout dinamico como pros!
        LinearLayout lytBase = (LinearLayout) findViewById(R.id.LytContenedor);
        //TextView texto = new TextView(this);
        //texto.setText(valorRecibido);
        //lytBase.addView(texto);

        //Se crea un parametro auxiliar para cuestiones de diseño con el TextView y el EditText
        LinearLayout.LayoutParams lytformato = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);

        //Creamos otro parametro para el formato del texto de las columnas

        LinearLayout.LayoutParams lytformato2 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT, 2.5f);

        //Vamos a crear primero la cabecera
        LinearLayout lytCabecera = new LinearLayout(this);
        lytCabecera.setOrientation(LinearLayout.HORIZONTAL);
        lytCabecera.setBackgroundResource(R.drawable.customborder);


        //TextView txtCabecera1 = new TextView(this);
        //txtCabecera1.setText("Formulas");
        //txtCabecera1.setLayoutParams(lytformato);

        //TextView txtCabecera2 = new TextView(this);
        //txtCabecera2.setText("Prioridad");
        //txtCabecera2.setLayoutParams(lytformato2);

        //TextView textPrioridad = new TextView(this);
        //textPrioridad.setText("Prioridades");

        //textPrioridad.setTypeface(null, Typeface.BOLD);
        //txtCabecera1.setTypeface(null, Typeface.BOLD);
        //txtCabecera2.setTypeface(null, Typeface.BOLD);



        //lytCabecera.addView(txtCabecera1);
        //lytCabecera.addView(txtCabecera2);
        //lytCabecera.addView(textPrioridad);


        //lytBase.addView(lytCabecera);



        //Tenemos que abrir la base de datos
        FormulasSQLiteHelper usdbh =
                new FormulasSQLiteHelper(this, "DbEra", null, 1);
        final SQLiteDatabase db = usdbh.getWritableDatabase();

        //Si existen datos en la tabla prioridad los borramos todos
        //db.execSQL("Delete  FROM Prioridad Where IdPrioridad >= 0");

        //Hacer una consulta de las formulas exactamente igual que en la pantalla de encuesta
        final Cursor identificadores = db.rawQuery(" SELECT  IdFormula,Abreviatura FROM Formulas", null);
        identificadores.moveToFirst();
        final int numeroFormulas;
        numeroFormulas = identificadores.getCount();


        //Meter la cadena de la encuesta en un vector utilizando la funcion split ','
        final String[] prioridad =  valorRecibido.split(",");


        //Creamos una caja de texto con el titulo "Resultado del formulario"
        TextView txtCabecera1 = new TextView(this);
        txtCabecera1.setText("Resultado del formulario");
        //Negrita
        txtCabecera1.setTypeface(null, Typeface.BOLD);
        //Centrado
        txtCabecera1.setGravity(Gravity.CENTER_HORIZONTAL);
        lytBase.addView(txtCabecera1);


        //Mostramos al usuario los valores que ha elegido para cada formula
            String cadenaAuxiliar="";
        for(int i =0; i< numeroFormulas; i++)
        {
            //Creamos un linear layout auxiliar donde iremos introduciendo los elementos que queremos mostrar
            LinearLayout auxTexto = new LinearLayout(this);
            auxTexto.setOrientation(LinearLayout.HORIZONTAL);
            auxTexto.setBackgroundResource(R.drawable.customborder);
            //El nombre de la formula
            TextView txtAbreviatura = new TextView(this);
            txtAbreviatura.setText(identificadores.getString(1));
            txtAbreviatura.setLayoutParams(lytformato);
            identificadores.moveToNext();
            txtAbreviatura.setTypeface(null, Typeface.BOLD);
            txtAbreviatura.setGravity(Gravity.CENTER_VERTICAL);
            auxTexto.addView(txtAbreviatura);




            ImageView imgPrioridad = new ImageView(this);

            if(prioridad[i].equals("Alta"))
                imgPrioridad.setBackgroundResource(R.drawable.checkedrojo);
            if(prioridad[i].equals("Media"))
                imgPrioridad.setBackgroundResource(R.drawable.checkedamarillo);
            if(prioridad[i].equals("Baja"))
                imgPrioridad.setBackgroundResource(R.drawable.checkedverde);


            auxTexto.addView(imgPrioridad);

            TextView TxtPrioridad = new TextView(this);
            TxtPrioridad.setText(prioridad[i]);
            TxtPrioridad.setLayoutParams(lytformato2);
            TxtPrioridad.setTypeface(null, Typeface.BOLD);
            TxtPrioridad.setGravity(Gravity.CENTER_VERTICAL);






            auxTexto.addView(TxtPrioridad);

            lytBase.addView(auxTexto);


        }



        //Creamos dos botones uno para volver a la pantalla anterior y otro para aceptar las formulas

        //Vamos a crear primero la cabecera
         LinearLayout lytPie = new LinearLayout(this);
        lytPie.setOrientation(LinearLayout.HORIZONTAL);
        lytPie.setBackgroundResource(R.drawable.customborder);



        final ImageButton btnAceptar = new ImageButton(this);
        //btnAceptar.setText("Aceptar Resultados");
        btnAceptar.setImageResource(R.drawable.check);
        btnAceptar.setLayoutParams(lytformato);
        btnAceptar.setBackgroundColor(Color.parseColor("#FFFFFF"));

        final ImageButton btnCorregir = new ImageButton(this);
        //btnAceptar.setText("Aceptar Resultados");
        btnCorregir.setImageResource(R.drawable.corregir);
        btnCorregir.setLayoutParams(lytformato);
        btnCorregir.setBackgroundColor(Color.parseColor("#FFFFFF"));



        lytPie.addView(btnCorregir);
        lytPie.addView(btnAceptar);
        lytBase.addView(lytPie);


        btnCorregir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });

        //Si pulsamos el boton de aceptar los resultados
        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creamos el Intent
                Intent intent =
                        new Intent(ResultadosEncuesta.this, MensajePostEncuesta.class);

                db.execSQL("Delete  FROM Prioridad Where 1 ");


                identificadores.moveToFirst();
                for(int i=0; i < numeroFormulas; i++)
                {
                    db.execSQL("INSERT INTO Prioridad (IdPrioridad,IdFormula,Tipo) VALUES('" + i + "','" + identificadores.getInt(0) + "','" + prioridad[i] + "')");
                    identificadores.moveToNext();
                }

                //Iniciamos la nueva actividad
                startActivity(intent);

                //En la tabla Prioridad Metemos la id de la formula y su valor de prioridad.

            }
        });


    }


    //Botron atrasssssss
    @Override
    public boolean onOptionsItemSelected (MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                Log.i("ActionBar", "Atrás");
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

}