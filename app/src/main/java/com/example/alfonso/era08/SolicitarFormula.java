package com.example.alfonso.era08;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.Editable;
import android.text.TextWatcher;
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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SolicitarFormula extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitar_formula);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("Solicitar fórmula");



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ImageButton btnSolicitarFormula = (ImageButton) findViewById(R.id.BtnSolicitarFormula) ;


        final EditText etxNombre = (EditText) findViewById(R.id.EtxNombre);
        final EditText etxReferencia = (EditText) findViewById(R.id.EtxReferencia);

        final TextView txtContadorNombre = (TextView) findViewById(R.id.TxtContadorNombre);
        final TextView txtContadorReferencia = (TextView) findViewById(R.id.TxtContadorReferencia);


        //Contador maximo 50 caracteres.

        etxNombre.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {

                    txtContadorNombre.setText(s.length() + "/50");
                }

        });



        //Contaor maximo 120 caracteres
        etxReferencia.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {

                txtContadorReferencia.setText(s.length() + "/120");
            }

        });





        LinearLayout  lytContenedor = (LinearLayout) findViewById(R.id.LytContorno);
        lytContenedor.setBackgroundResource(R.drawable.customborder2);






        btnSolicitarFormula.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String textoMensaje = "Nombre Formula:" + etxNombre.getText().toString() + "\n"  + "Referencia:" +  etxReferencia.getText().toString()  ;


                String[] to = { "eracontacto@yopmail.com", "eracontacto@yopmail.com" };
                String[] cc = { "eracontacto@yopmail.com" };
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.putExtra(Intent.EXTRA_EMAIL, to);
                emailIntent.putExtra(Intent.EXTRA_CC, cc);
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Solicitar Formula");
                emailIntent.putExtra(Intent.EXTRA_TEXT, textoMensaje);
                emailIntent.setType("message/rfc822");
                startActivity(Intent.createChooser(emailIntent, "Email "));

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
        getMenuInflater().inflate(R.menu.solicitar_formula, menu);
        return true;
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_inicio) {
            // Handle the camera action
            Intent intent =
                    new Intent(SolicitarFormula.this, Inicio.class);
            startActivity(intent);

        } else if (id == R.id.nav_configuracion) {
            Intent intent =
                    new Intent(SolicitarFormula.this, CambiarConfiguracion.class);
            startActivity(intent);

        } else if (id == R.id.nav_reportar_error) {

            Intent intent =
                    new Intent(SolicitarFormula.this, ReportarError.class);
            startActivity(intent);

        } else if (id == R.id.nav_acerca) {
            Intent intent =
                    new Intent(SolicitarFormula.this, AcercaDe.class);
            startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
