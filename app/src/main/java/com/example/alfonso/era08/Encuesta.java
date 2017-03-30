package com.example.alfonso.era08;


/**
 * Created by Alfonso on 14/05/2016.
 * Ultima modificación: 20/07/2016

 */

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.alfonso.era08.Clases.FormulasSQLiteHelper;

import java.util.ArrayList;
import java.util.List;


public class Encuesta extends AppCompatActivity {

    private String cadena = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encuesta);

        /*Esta pantalla debe ser muy parecida a la pantalla de encuesta aqui se debe mostrar

        1. Mostramos todas las formulas de la aplicación
        2. Mostramos a su lado los botones de alta, media, baja
        3. Rellenamos estos botones con la prioridad que tiene ahora la formula
        4. Creamos 2 botones abajo:
            4.1: Boton cambiar configuración: Boton que al pulsarlo cambia la configuración de las formulas.
            4.2: Boton Volver: Nos regresa a la pantalla de inicio sin hacer los cambios.

        */

        //Abrimos la base de datos en modo escritura ya que cambiaremos la tabla Prioridad.
        FormulasSQLiteHelper usdbh =
                new FormulasSQLiteHelper(this, "DbEra", null, 1);
        final SQLiteDatabase db = usdbh.getWritableDatabase();
        final List<RadioGroup> allRgs = new ArrayList<RadioGroup>();



        //Hacer una consulta para coger todas las formulas de la base de datos.
        Cursor abreviatura = db.rawQuery(" SELECT  Abreviatura FROM Formulas", null);
        final LinearLayout lytBase = (LinearLayout) findViewById(R.id.LytContenedor);

        //Se crea un parametro auxiliar para cuestiones de diseño con el TextView y el EditText
        LinearLayout.LayoutParams lytFormato = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);

        //Creamos otro parametro para el formato del texto de las columnas

        LinearLayout.LayoutParams lytFormato2 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT, 2.0f);


        //Creamos un alertDialog para mostrar los mensajes de Error al usuario.
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();

        alertDialog.setButton("Continuar..", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // here you can add functions
            }
        });


        //Contamos el numero de formulas
        int numeroFormulas;
        numeroFormulas = abreviatura.getCount();
        //Movemos el cursor a la primera.
        abreviatura.moveToFirst();

        //Declaramos una variable j para asignar identificadores unicos a los botones que iremos creando.
        int j = 0;

        //Vamos a crear primero la cabecera
        LinearLayout lytCabecera = new LinearLayout(this);
        lytCabecera.setOrientation(LinearLayout.HORIZONTAL);
        lytCabecera.setBackgroundResource(R.drawable.customborder);


        TextView txtCabecera1 = new TextView(this);
        txtCabecera1.setText("Formulas");
        txtCabecera1.setLayoutParams(lytFormato);

        TextView txtCabecera2 = new TextView(this);
        txtCabecera2.setText("Frecuencia");
        txtCabecera2.setLayoutParams(lytFormato2);

        //TextView textPrioridad = new TextView(this);
        //textPrioridad.setText("Prioridades");

        //textPrioridad.setTypeface(null, Typeface.BOLD);
        txtCabecera1.setTypeface(null, Typeface.BOLD);
        txtCabecera1.setTextSize(20);
        txtCabecera2.setTextSize(20);
        txtCabecera2.setTypeface(null, Typeface.BOLD);


        lytCabecera.addView(txtCabecera1);
        lytCabecera.addView(txtCabecera2);


        lytBase.addView(lytCabecera);






        for(int i=0;i< numeroFormulas; i++)
        {
            //Creamos un linear layout auxiliar donde iremos introduciendo los elementos que queremos mostrar
            LinearLayout lytFormula = new LinearLayout(this);
            lytFormula.setOrientation(LinearLayout.HORIZONTAL);
            lytFormula.setBackgroundResource(R.drawable.customborder);
            //El nombre de la formula
            TextView txtNombreFormula = new TextView(this);
            txtNombreFormula.setText(abreviatura.getString(0));
            txtNombreFormula.setLayoutParams(lytFormato);
            abreviatura.moveToNext();
            txtNombreFormula.setTypeface(null, Typeface.BOLD);
            lytFormula.addView(txtNombreFormula);
            //Otro Linear Layout para los botones
            LinearLayout auxBotones = new LinearLayout(this);

            //Los botones de alta,media,baja
            RadioGroup rgpPrioridad = new RadioGroup(this);
            rgpPrioridad.setOrientation(LinearLayout.HORIZONTAL);
            RadioButton RbtnAlta = new RadioButton(this);
            RbtnAlta.setButtonDrawable(R.drawable.selectradio);
            //Alta.setText("Alta");
            //Asignamos color e id a la Alta
            //RbtnAlta.getHintTextColors(Color.parseColor("#FF8A80"));
            RbtnAlta.setId(j);
            RadioButton RbtnMedia  = new RadioButton(this);
            RbtnMedia.setButtonDrawable(R.drawable.selectradio);
            //Media.setText("Media");
            //Asignamos color e id a la Media
            //RbtnMedia.setBackgroundColor(Color.parseColor("#FFF59D"));
            RbtnMedia.setId(j+1);
            RadioButton RbtnBaja = new RadioButton(this);
            RbtnBaja.setButtonDrawable(R.drawable.selectradio);
            //Baja.setText("Baja");
            //Asignamos color e id a la Baja
            //RbtnBaja.setBackgroundColor(Color.parseColor("#CCFF90"));
            RbtnBaja.setId(j + 2);


            //Damos color a cada uno de los botones dependiendo de su frecuencia de uso
            if(Build.VERSION.SDK_INT>=21) {


                //Color rojo para las frecuencias altas
                ColorStateList colorStateListAlta = new ColorStateList(
                        new int[][]{

                                new int[]{-android.R.attr.state_enabled}, //disabled
                                new int[]{android.R.attr.state_enabled} //enabled
                        },
                        new int[]{

                                Color.BLACK //disabled
                                , Color.parseColor("#FD5652") //enabled

                        }
                );

                //Color amarillo para las frecuencias medias
                ColorStateList colorStateListMedia = new ColorStateList(
                        new int[][]{

                                new int[]{-android.R.attr.state_enabled}, //disabled
                                new int[]{android.R.attr.state_enabled} //enabled
                        },
                        new int[]{

                                Color.BLACK //disabled
                                , Color.parseColor("#FDBD41") //enabled

                        }
                );

                //Color verde para las frecuencias bajas
                ColorStateList colorStateListBaja = new ColorStateList(
                        new int[][]{

                                new int[]{-android.R.attr.state_enabled}, //disabled
                                new int[]{android.R.attr.state_enabled} //enabled
                        },
                        new int[]{

                                Color.BLACK //disabled
                                , Color.parseColor("#57D269") //enabled

                        }
                );


                RbtnAlta.setButtonTintList(colorStateListAlta);//Color rojo
                RbtnMedia.setButtonTintList(colorStateListMedia);//Color amarillo
                RbtnBaja.setButtonTintList(colorStateListBaja);//Color verde

                //rbtnPagina2.setButtonTintList(colorStateList);//set the color tint list


                //rbtnPagina1.invalidate(); //could not be necessary
            }


            rgpPrioridad.addView(RbtnAlta);
            rgpPrioridad.addView(RbtnMedia);
            rgpPrioridad.addView(RbtnBaja);
            //auxBotones.addView(rgpPrioridad);
            lytFormula.addView(rgpPrioridad);
            lytBase.addView(lytFormula);
            //lytBase.addView(auxBotones);
            allRgs.add(rgpPrioridad);
            j = j +3;




        }

        //Creamos un vector de String con los valores Alto,Medio,Bajo cada posicion del vector coincidira con
        // la id de los botones creados, asi sera muy facil tomar su valor.

        final String vectorPrioridad [] = new String[numeroFormulas*3] ;

        j = 0;


        for (int i =0; i < numeroFormulas ; i++)
        {
            vectorPrioridad[j] = "Alta";
            vectorPrioridad[j+1] = "Media";
            vectorPrioridad[j+2] = "Baja";
            j = j+3;


        }


        //Creamos un boton aceptar, para aceptar los resultados de la encuesta.
        //final Button btnAceptar = new Button(this);
        final ImageButton btnAceptar = new ImageButton(this);
        //btnAceptar.setText("Aceptar Resultados");
        btnAceptar.setImageResource(R.drawable.check);
        btnAceptar.setBackgroundColor(Color.parseColor("#FFFFFF"));
        lytBase.addView(btnAceptar);
        /*
        TextView txtPieBoton = new TextView(this);
        txtPieBoton.setText("Envíar formulario");
        txtPieBoton.setTextSize(20);
        txtPieBoton.setGravity(Gravity.CENTER_HORIZONTAL);
        lytBase.addView(txtPieBoton);
        */


        //Creamos un botón limpiar para reiniciar la encuesta y borrar los campos seleccionados.
        //final Button btnLimpiar = new Button(this);
        //final ImageButton btnLimpiar = new ImageButton(this);

        //btnLimpiar.setText("Reiniciar encuesta");
        //btnLimpiar.setBackgroundResource(R.drawable.customborder);
        //btnLimpiar.setTextColor(Color.parseColor("#FFFFFF"));
        //lytBase.addView(btnLimpiar);

        //Creamos un boton inicio para volver al principio de la explicación.
        //final Button btnInicio = new Button(this);
        //btnInicio.setText("Explicamelo de nuevo");
        //btnInicio.setBackgroundResource(R.drawable.customborder);
        //btnInicio.setTextColor(Color.parseColor("#FFFFFF"));
        //lytBase.addView(btnInicio);
        //final TextView resultado = new TextView(this);
        //cadena = "";



        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //Voy a coger las ids de los botones.
                //Creo un bucle para recorrer la lista de radial group

                boolean noseleccionado = false;
                String cadenaResultado = "";
                int idFormula;

                for (int i = 0; i < allRgs.size(); i++) {
                    if (allRgs.get(i).getCheckedRadioButtonId() == -1) {
                        cadena = " Por favor debe rellenar todas las posibles opciones antes de continuar ";
                        noseleccionado = true;
                        alertDialog.setMessage(cadena);
                        alertDialog.show();
                    }
                }



                if (!noseleccionado) {


                    for (int i = 0; i < allRgs.size(); i++) {
                        cadena = cadena + "Formula" + (i + 1) + ": Valor:" + vectorPrioridad[allRgs.get(i).getCheckedRadioButtonId()] + "\n";
                        //Ahora mismo se coge asi porque estan en orden, si ese orden se cambiase habra que crear un vector con los valores de la id en cada posicion
                        //db.execSQL("INSERT INTO Prioridad (IdPrioridad,IdFormula,Tipo)  VALUES ('" + (i + 1) + "','" + (i + 1) + "','" + vectorPrioridad[allRgs.get(i).getCheckedRadioButtonId()] + "');");
                        cadenaResultado = cadenaResultado + vectorPrioridad[allRgs.get(i).getCheckedRadioButtonId()];
                        if (i != (allRgs.size() -1) )
                            cadenaResultado = cadenaResultado + "," ;

                        //resultado.setText(cadenaResultado);
                    }


                    //Debemos meter todos estos valores seleccionados en una cadena de texto que analizaremos en ResultadosEncuesta
                    //resultado.setText(cadena);
                    Intent intent = new Intent(Encuesta.this, ResultadosEncuesta.class);

                    //Creamos la información a pasar entre actividades
                    Bundle b = new Bundle();
                    b.putString("Resultado", cadenaResultado);

                    //Añadimos la información al intent
                    intent.putExtras(b);

                    //Iniciamos la nueva actividad
                    startActivity(intent);

                }


            }
        });






        //resultado.setText(cadena);












    }


    @Override
    public boolean onOptionsItemSelected (MenuItem item)
    {
        switch (item.getItemId())
        {

            case R.id.refrescar:
                Intent intent =
                        new Intent(Encuesta.this, Encuesta.class);
                //Iniciamos la nueva actividad
                startActivity(intent);

                return true;


            case R.id.ayuda:

                Intent intent2 =
                        new Intent(Encuesta.this, AyudaEncuesta.class);
                //Iniciamos la nueva actividad
                startActivity(intent2);

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.encuesta, menu);
        return true;
    }


}



//Controles necesarios el boton reinciar encuesta debe aparecer en el panel superior con un icono especial

//Debe haber un boton de ayuda con el texto donde explique el funcionamiento de la encuesta (probar con una ventana emergente)

