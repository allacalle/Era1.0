package com.example.alfonso.era08;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.alfonso.era08.Clases.ArrayAdapterSearchView;
import com.example.alfonso.era08.Clases.FormulasSQLiteHelper;
import com.example.alfonso.era08.Clases.ColorTool;

/**
 * Created by Alfonso on 18/03/2016.
 * Ultima modificación: 20/07/2016

 */

public class Inicio extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnTouchListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

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

        ImageView iv = (ImageView) findViewById (R.id.image);
        if (iv != null) {
            iv.setOnTouchListener (this);
        }



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.inicio, menu);
        return true;
    }




    @Override
    public void onBackPressed() {

        return;
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_recientes) {
            Intent intent =
                    new Intent(Inicio.this, FormulasRecientes.class);

            //Iniciamos la nueva actividad
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);





    }






    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_configuracion) {

            Intent intent =
                    new Intent(Inicio.this, CambiarConfiguracion.class);
            startActivity(intent);


        } else if (id == R.id.nav_reportar_error) {
            Intent intent =
                    new Intent(Inicio.this, ReportarError.class);
            startActivity(intent);

        } else if (id == R.id.nav_solicitar_formula) {
            Intent intent =
                    new Intent(Inicio.this, SolicitarFormula.class);
            startActivity(intent);

        } else if (id == R.id.nav_acerca) {
            Intent intent =
                    new Intent(Inicio.this, AcercaDe.class);
            startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public boolean onTouch (View v, MotionEvent ev)
    {
        boolean handledHere = false;


        final int evX = (int) ev.getX();
        final int evY = (int) ev.getY();

        // If we cannot find the imageView, return.
        ImageView imageView = (ImageView) v.findViewById (R.id.image);
        if (imageView == null) return false;

        // When the action is Down, see if we should show the "pressed" image for the default image.
        // We do this when the default image is showing. That condition is detectable by looking at the
        // tag of the view. If it is null or contains the resource number of the default image, display the pressed image.

        // Now that we know the current resource being displayed we can handle the DOWN and UP events.

        // On the UP, we do the click action.
        // The hidden image (image_areas) has three different hotspots on it.
        // The colors are red, blue, and yellow.
        // Use image_areas to determine which region the user touched.
        int touchColor = getHotspotColor (R.id.image_areas, evX, evY);

        // Compare the touchColor to the expected values. Switch to a different image, depending on what color was touched.
        // Note that we use a Color Tool object to test whether the observed color is close enough to the real color to
        // count as a match. We do this because colors on the screen do not match the map exactly because of scaling and
        // varying pixel density.
        ColorTool ct = new ColorTool ();
        int tolerance = 25;
        if (ct.closeMatch (Color.RED, touchColor, tolerance))
        {
            //Creamos el Intent
            Intent intent =
                    new Intent(Inicio.this, FormulasFrecuencia.class);
            //Creamos la información a pasar entre actividades
            Bundle b = new Bundle();
            b.putString("Prioridad", "Alta");

            //Añadimos la información al intent
            intent.putExtras(b);


            //Iniciamos la nueva actividad
            startActivity(intent);

        }
        else if (ct.closeMatch (Color.GREEN, touchColor, tolerance))
        {
            //Creamos el Intent
            Intent intent =
                    new Intent(Inicio.this, FormulasFrecuencia.class);
            //Creamos la información a pasar entre actividades
            Bundle b = new Bundle();
            b.putString("Prioridad", "Baja");

            //Añadimos la información al intent
            intent.putExtras(b);


            //Iniciamos la nueva actividad
            startActivity(intent);
        }

        else if (ct.closeMatch (Color.YELLOW, touchColor, tolerance))
        {
            //Creamos el Intent
            Intent intent =
                    new Intent(Inicio.this, FormulasFrecuencia.class);
            //Creamos la información a pasar entre actividades
            Bundle b = new Bundle();
            b.putString("Prioridad", "Media");

            //Añadimos la información al intent
            intent.putExtras(b);


            //Iniciamos la nueva actividad
            startActivity(intent);
        }


        // If the next image is the same as the last image, go back to the default.
        // toast ("Current image: " + currentResource + " next: " + nextImage);

        // end switch


        return handledHere;
    }




    public int getHotspotColor (int hotspotId, int x, int y) {
        ImageView img = (ImageView) findViewById (hotspotId);
        if (img == null) {
            Log.d ("ImageAreasActivity", "Hot spot image not found");
            return 0;
        } else {
            img.setDrawingCacheEnabled(true);
            Bitmap hotspots = Bitmap.createBitmap(img.getDrawingCache());
            if (hotspots == null) {
                Log.d ("ImageAreasActivity", "Hot spot bitmap was not created");
                return 0;
            } else {
                img.setDrawingCacheEnabled(false);
                return hotspots.getPixel(x, y);
            }
        }
    }





}

