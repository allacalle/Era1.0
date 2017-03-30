package com.example.alfonso.era08;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.LinearLayout;

public class AyudaEncuesta extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ayuda_encuesta);
        //Boton atras
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        LinearLayout lytContenedor = (LinearLayout) findViewById(R.id.LytContenedor);
        lytContenedor.setBackgroundResource(R.drawable.customborder2);

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
