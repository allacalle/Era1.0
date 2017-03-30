package com.example.alfonso.era08;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebHistoryItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.alfonso.era08.Clases.Formula;
import com.example.alfonso.era08.Clases.FormulasSQLiteHelper;

import static android.view.Gravity.CENTER;

public class FormulasFrecuencia extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulas_frecuencia);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("ERA");



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        Bundle bundle = this.getIntent().getExtras();
        //Construimos el mensaje a mostrar
        final String valorRecibido = bundle.getString("Prioridad");

        //Cargamos los botones que tenemos en nuestro xml.
        Button BtnRecientes = (Button) findViewById(R.id.BtnRecientes) ;
        Button BtnInicio = (Button) findViewById(R.id.BtnInicio) ;


        //Primero se debe abrir la base de datos
        FormulasSQLiteHelper usdbh =
                new FormulasSQLiteHelper(this, "DbEra", null, 1);

        SQLiteDatabase db = usdbh.getWritableDatabase();

        LinearLayout lytBase = (LinearLayout) findViewById(R.id.LytContenedor);

        Cursor cursorPrioridad = null;

        //Dependiendo de la prioridad recibida en la actividad anterior cargamos un listado de formulas cuya prioridad sea la seleccionada.
        //utilizamos la propiedad JOIN para hacer una consulta sobre 2 tablas que tienen un valor en comun, en este caso la IdFormula


        switch (valorRecibido)
        {
            case "Alta":
                cursorPrioridad = db.rawQuery(" SELECT F.IdFormula,F.Abreviatura  FROM Formulas F,Prioridad P WHERE F.IdFormula = P.IdFormula AND P.Tipo ='Alta' ", null);
                break;
            case "Media":
                cursorPrioridad = db.rawQuery(" SELECT F.IdFormula,F.Abreviatura  FROM Formulas F,Prioridad P WHERE F.IdFormula = P.IdFormula AND P.Tipo ='Media' ", null);
                break;
            case "Baja" :
                cursorPrioridad = db.rawQuery(" SELECT F.IdFormula,F.Abreviatura  FROM Formulas F,Prioridad P WHERE F.IdFormula = P.IdFormula AND P.Tipo ='Baja' ", null);
                break;


        }

        cursorPrioridad.moveToFirst();

        //Contamos el numero de formulas
        int numeroFormulas;
        numeroFormulas = cursorPrioridad.getCount();

        //Se crea una cabecera informatica indicando la frecuencia de las formulas de la pantalla
        LinearLayout lytCabeza = new LinearLayout (this);
        lytCabeza.setGravity(CENTER);
        TextView txtCabecera = new TextView(this);
        txtCabecera.setTextSize(20);
        txtCabecera.setTypeface(null, Typeface.BOLD);
        ImageView imgCabecera = new ImageView(this);




        switch (valorRecibido) {
            case "Alta":
                txtCabecera.setText("Mucho uso");
                imgCabecera.setImageResource(R.drawable.checkedrojo);
                break;
            case "Media":
                txtCabecera.setText("Algo de uso");
                imgCabecera.setImageResource(R.drawable.checkedamarillo);

                break;
            case "Baja":
                txtCabecera.setText("Poco uso");
                imgCabecera.setImageResource(R.drawable.checkedverde);

                break;
        }

        lytCabeza.addView(txtCabecera);
        lytCabeza.addView(imgCabecera);
        lytBase.addView(lytCabeza);



        for( int i=0; i < numeroFormulas; i++ )
        {

            //Creamos un objeto drawable para dar formato a los elementos auxiliares.
            GradientDrawable drawable = new GradientDrawable();
            drawable.setShape(GradientDrawable.RECTANGLE);
            drawable.setStroke(5, Color.parseColor("#BDBDBD"));

            //Asignamos un color a los elementos dependiendo de la prioridad que haya sido seleccionada.

            switch (valorRecibido) {
                case "Alta":
                    drawable.setColor(Color.parseColor("#FF8A80"));
                    break;
                case "Media":
                    drawable.setColor(Color.parseColor("#FFF59D"));
                    break;
                case "Baja":
                    drawable.setColor(Color.parseColor("#CCFF90"));
                    break;
            }


            final Button btnFormula = new Button(this);
            btnFormula.setText(cursorPrioridad.getString(1));
            btnFormula.setId(cursorPrioridad.getInt(0));
            cursorPrioridad.moveToNext();
            TextView txtvacio = new TextView(this);
            //Le aplico el layout al boton de cada formula para darle un mejor formato
            btnFormula.setBackgroundResource(R.drawable.customborderbotonformula);
            lytBase.addView(txtvacio);
            lytBase.addView(btnFormula);

            btnFormula.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Creamos el Intent
                    Intent intent =
                            new Intent(FormulasFrecuencia.this, CalcularFormula.class);

                    //Creamos la información a pasar entre actividades
                    Bundle b = new Bundle();
                    String cadenaId= "";
                    cadenaId = cadenaId + btnFormula.getId() ;
                    //Vamos a pasar el identificador de la formula que es un campo unico .
                    b.putString("IdFormula", cadenaId );

                    //Añadimos la información al intent
                    intent.putExtras(b);

                    //Iniciamos la nueva actividad
                    startActivity(intent);

                }
            });


        }



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
        getMenuInflater().inflate(R.menu.formulas_frecuencia, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();



        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_inicio) {
            // Handle the camera action
            Intent intent =
                    new Intent(FormulasFrecuencia.this, Inicio.class);
            startActivity(intent);
        }

        if (id == R.id.nav_configuracion) {

            Intent intent =
                    new Intent(FormulasFrecuencia.this, CambiarConfiguracion.class);
            startActivity(intent);


        } else if (id == R.id.nav_reportar_error) {
            Intent intent =
                    new Intent(FormulasFrecuencia.this, ReportarError.class);
            startActivity(intent);

        } else if (id == R.id.nav_solicitar_formula) {
            Intent intent =
                    new Intent(FormulasFrecuencia.this, SolicitarFormula.class);
            startActivity(intent);

        } else if (id == R.id.nav_acerca) {
            Intent intent =
                    new Intent(FormulasFrecuencia.this, AcercaDe.class);
            startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
