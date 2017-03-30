package com.example.alfonso.era08;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.alfonso.era08.Clases.FormulasSQLiteHelper;

public class FormulasRecientes extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulas_recientes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("Recientes");



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        LinearLayout lytBase = (LinearLayout) findViewById(R.id.LytContenedor);

        //Abro la base de datos.
        FormulasSQLiteHelper usdbh =
                new FormulasSQLiteHelper(this, "DbEra", null, 1);

        SQLiteDatabase db = usdbh.getReadableDatabase();

        //creamos un cursos, en el string(0) tenemos el parametro, en el string(1) tenemos el tipo de formula
        Cursor Contador = db.rawQuery("SELECT COUNT(*) FROM Recientes", null);
        Contador.moveToFirst();
        int numeroCampos = Contador.getInt(0);
        Contador.close();
        Cursor c = db.rawQuery("SELECT IdFormula,Fecha FROM Recientes ORDER BY Fecha DESC ", null);
        c.moveToFirst();

        String cadena = "";

        //Solo quiero mostrar las 5 primeras como mucho.
        if (numeroCampos > 7)
            numeroCampos = 7;

        for (int i = 0; i < numeroCampos; i++) {

            //Creamos un objeto drawable para dar formato a los elementos auxiliares.
            GradientDrawable drawable = new GradientDrawable();
            drawable.setShape(GradientDrawable.RECTANGLE);
            drawable.setStroke(5, Color.parseColor("#BDBDBD"));


            //Cojo cada una de las ids de las formulas
            final int idFormula = c.getInt(0);

            //Busco en la tabla Formulas el nombre abreviado de las IDS y se lo asigno al boton.

            //Creamos un cursor para las formulas
            Cursor cursorFormula = db.rawQuery("SELECT Abreviatura FROM Formulas WHERE IdFormula = '" + idFormula + "'  ", null);
            cursorFormula.moveToFirst();
            String abreviatura = cursorFormula.getString(0);
            cursorFormula.close();


            //Busco en la tabla Prioridad la prioridad asignada a esas IDS y dependiendo de la que sea asigno un color a ese boton.
            Cursor cursorPrioridad = db.rawQuery("SELECT Tipo FROM Prioridad WHERE IdFormula = '" + idFormula + "'  ", null);
            cursorPrioridad.moveToFirst();
            String prioridad = cursorPrioridad.getString(0);
            cursorPrioridad.close();
            //Creo un boton
            final Button btnFormula = new Button(this);
            btnFormula.setBackgroundResource(R.drawable.customborderbotonformula);


            //Aplico al drawable el color de la prioridad actual

            switch (prioridad) {
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

            btnFormula.setBackgroundDrawable(drawable);





            //Le asigno el texto
            btnFormula.setText(abreviatura);
            btnFormula.setId(idFormula);

            //Le aplico el layout

            TextView txtvacio = new TextView(this);
            lytBase.addView(txtvacio);

            //Meto el boton en el layout
            lytBase.addView(btnFormula);


            //Definimos la funcion del boton
            btnFormula.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Creamos el Intent
                    Intent intent =
                            new Intent(FormulasRecientes.this, CalcularFormula.class);

                    //Creamos la información a pasar entre actividades
                    Bundle b = new Bundle();
                    String cadenaId= "";
                    cadenaId = cadenaId + btnFormula.getId() ;
                    b.putString("IdFormula", (String) cadenaId);

                    //Añadimos la información al intent
                    intent.putExtras(b);

                    //Iniciamos la nueva actividad
                    startActivity(intent);

                }
            });





            //Paso al valor siguiente del cursor
            c.moveToNext();
        }

        c.close();
        db.close();






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
        getMenuInflater().inflate(R.menu.formulas_recientes, menu);
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
                    new Intent(FormulasRecientes.this, Inicio.class);
            startActivity(intent);
        }

        if (id == R.id.nav_configuracion) {

            Intent intent =
                    new Intent(FormulasRecientes.this, CambiarConfiguracion.class);
            startActivity(intent);


        } else if (id == R.id.nav_reportar_error) {
            Intent intent =
                    new Intent(FormulasRecientes.this, ReportarError.class);
            startActivity(intent);

        } else if (id == R.id.nav_solicitar_formula) {
            Intent intent =
                    new Intent(FormulasRecientes.this, SolicitarFormula.class);
            startActivity(intent);

        } else if (id == R.id.nav_acerca) {
            Intent intent =
                    new Intent(FormulasRecientes.this, AcercaDe.class);
            startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
