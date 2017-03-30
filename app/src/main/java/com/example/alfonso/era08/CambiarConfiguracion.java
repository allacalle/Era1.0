package com.example.alfonso.era08;

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
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.alfonso.era08.Clases.FormulasSQLiteHelper;

import java.util.ArrayList;
import java.util.List;

public class CambiarConfiguracion extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private String cadena = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambiar_configuracion);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("Configuración");



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

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
        //Obtenemos la prioridad de la fórmula tambien
        Cursor cursorAbreviatura = db.rawQuery(" SELECT F.Abreviatura, P.Tipo  FROM Formulas F,Prioridad P WHERE F.IdFormula = P.IdFormula ", null);
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
        numeroFormulas = cursorAbreviatura.getCount();
        //Movemos el cursor a la primera.
        cursorAbreviatura.moveToFirst();

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
            String TipoPrioridad =  cursorAbreviatura.getString(1);
            //Creamos un linear layout auxiliar donde iremos introduciendo los elementos que queremos mostrar
            LinearLayout lytFormula = new LinearLayout(this);
            lytFormula.setOrientation(LinearLayout.HORIZONTAL);
            lytFormula.setBackgroundResource(R.drawable.customborder);
            //El nombre de la formula
            TextView txtNombreFormula = new TextView(this);
            txtNombreFormula.setText(cursorAbreviatura.getString(0));
            txtNombreFormula.setLayoutParams(lytFormato);
            cursorAbreviatura.moveToNext();
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

            switch(TipoPrioridad)
            {
                case "Alta":
                    RbtnAlta.setChecked(true);
                    break;
                case "Media":
                    RbtnMedia.setChecked(true);
                    break;
                case "Baja":
                    RbtnBaja.setChecked(true);
                    break;
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
        //btnAceptar.setTextColor(Color.parseColor("#FFFFFF"));
        lytBase.addView(btnAceptar);
        /*
        TextView txtPieBoton = new TextView(this);
        txtPieBoton.setText("Guardar configuración");
        txtPieBoton.setGravity(Gravity.CENTER);
        txtPieBoton.setTypeface(null, Typeface.BOLD);
        lytBase.addView(txtPieBoton);
        */

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
                    Intent intent = new Intent(CambiarConfiguracion.this, ResultadosEncuesta.class);

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






    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.cambiar_configuracion, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {

            case R.id.refrescar:
                Intent intent =
                        new Intent(CambiarConfiguracion.this, CambiarConfiguracion.class);
                //Iniciamos la nueva actividad
                startActivity(intent);

                return true;


            case R.id.ayuda:

                Intent intent2 =
                        new Intent(CambiarConfiguracion.this, AyudaEncuesta.class);
                //Iniciamos la nueva actividad
                startActivity(intent2);

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_inicio) {

            Intent intent =
                    new Intent(CambiarConfiguracion.this, Inicio.class);
            startActivity(intent);


        } else if (id == R.id.nav_reportar_error) {
            Intent intent =
                    new Intent(CambiarConfiguracion.this, ReportarError.class);
            startActivity(intent);

        } else if (id == R.id.nav_solicitar_formula) {
            Intent intent =
                    new Intent(CambiarConfiguracion.this, SolicitarFormula.class);
            startActivity(intent);

        } else if (id == R.id.nav_acerca) {
            Intent intent =
                    new Intent(CambiarConfiguracion.this, AcercaDe.class);
            startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
