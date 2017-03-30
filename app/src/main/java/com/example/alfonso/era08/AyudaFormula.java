package com.example.alfonso.era08;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.alfonso.era08.Clases.Formula;


/**
 * Created by Alfonso on 20/10/2015.
 * Ultima modificación: 04/08/2016

 */

   /*
       Para cada pagina se debe mostrar

       - El nombre de la formula.
       - Sus parametros, que deben contener
         * Valor minimo (si lo tiene)
         * Valor maximo (si lo tiene)
         * Criterios cada uno con su  Puntuacion
       - Su expresion (si la tiene)
       - Su referencia bibliografica
         */

public class AyudaFormula extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ayuda_formula);
        //Activo el boton atras
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final LinearLayout lytBase = (LinearLayout) findViewById(R.id.LytContenedor);

        //Recibimos la id de la formula
        Bundle bundle = this.getIntent().getExtras();
        //Construimos el mensaje a mostrar
        final String valorRecibido = bundle.getString("IdFormula");

        final Formula formulaActual = new Formula(valorRecibido, getApplicationContext());

        setTitle("Ayuda fórmula");

        //Se crea un parametro auxiliar para cuestiones de diseño con el TextView y el EditText
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);


        //EN esta pagina se mostrar la ayuda personalizada para cada formula.

        //Creo un layout auxiliar
        //antes de nada creamos un layaout con un label de nombre de la formula y a su derecho un boton de información.
        LinearLayout lytCabecera = new LinearLayout(this);
        lytCabecera.setOrientation(LinearLayout.HORIZONTAL);
        //Creo un textview que mostrará el nombre de la formula
        TextView txtNombreDeFormula = new TextView(this);
        txtNombreDeFormula.setText(formulaActual.getNombreCompleto());
        txtNombreDeFormula.setLayoutParams(param);
        Button btnAyuda = new Button(this);
        btnAyuda.setText("Ayuda");
        btnAyuda.setLayoutParams(param);
        lytCabecera.addView(txtNombreDeFormula);
        //Aplico un borde diferente a la cabecera.
        lytCabecera.setBackgroundResource(R.drawable.customborder2);
        assert lytBase != null;
        lytBase.addView(lytCabecera);


        //Iteramos

        for (int i = 0; i < formulaActual.contarParametros(); i++) {

            //Creamos un linear layout para cada iteraccón , en el estarán todos los elementos
            //No queremos mostrar los parametros de tipo resultado.
            LinearLayout lytDeParametro = new LinearLayout(this);
            lytDeParametro.setOrientation(LinearLayout.VERTICAL);
            lytDeParametro.setBackgroundColor(Color.parseColor("#D5F8F8"));
            //Aplico un borde diferente a este layout secundario
            lytDeParametro.setBackgroundResource(R.drawable.customborder2);
            //Creamos un TextView donde escribiremos el nombre del parametro actual
            TextView txtNombreDeParametro = new TextView(this);
            txtNombreDeParametro.setTypeface(null, Typeface.BOLD);
            if (formulaActual.getParametro(i).getMedida() != null)
                txtNombreDeParametro.setText("" +formulaActual.getParametro(i).getNombre()+ "(" +formulaActual.getParametro(i).getMedida()+ ")." );
            else
                txtNombreDeParametro.setText(formulaActual.getParametro(i).getNombre());

            txtNombreDeParametro.setLayoutParams(param);
            lytDeParametro.addView(txtNombreDeParametro);
            //Ahora dependiendo del tipo de parametro creamos un elemento u otro
            
            
            //Comprobamos que la formula tenga valor minimo, (ya que si tiene minimo tendrá maximo tambien)
            //Si tiene valores minimos y maximos los mostramos en pantalla.

            if (formulaActual.getParametro(i).getValorMaximo() != formulaActual.getParametro(i).getValorMinimo()) {

                TextView txtValorMinimo = new TextView(this);
                String strValorMinimo = "" + formulaActual.getParametro(i).getValorMinimo();
                txtValorMinimo.setText("Mínimo:" + strValorMinimo + "\t");
                TextView txtValorMaximo = new TextView(this);
                String strValorMaximo = "" + formulaActual.getParametro(i).getValorMaximo();
                txtValorMaximo.setText("Máximo:" + strValorMaximo + "");
                lytDeParametro.addView(txtValorMinimo);
                lytDeParametro.addView(txtValorMaximo);
            }
            if (formulaActual.getTipoFormula().equals("escala") ) { 
                //Creamos un TextView para la palabra puntuacion lblPuntuacion 
                TextView lblPuntuacion= new TextView(this);
                lblPuntuacion.setTypeface(null, Typeface.BOLD);
                lblPuntuacion.setText("Puntuación");
                lblPuntuacion.setBackgroundResource(R.drawable.customborder2);
                lytDeParametro.addView(lblPuntuacion);


                for (int j = 0; j < formulaActual.getParametro(i).contarCriterios(); j++) {
                    //Mostramos los criterios de cada parametro con sus puntuaciones
                    LinearLayout lytDeCriterio = new LinearLayout(this);

                    //Creamos un layout para los criterios.

                    TextView txtCriterio = new TextView(this);
                    //TextView TxTPuntuacion = new TextView(this);
                    //TextView TxTPuntuacion2 = new TextView(this);


                    if(formulaActual.getParametro(i).getTipo().equals("logico"))
                    {
                        txtCriterio.setText("\n" + "Puntos:" + formulaActual.getParametro(i).getCriterioPuntuacion(j).getPuntuacion() );

                    }
                    else {

                        String cadenaCriterio = cambiarFormatoCriterio(formulaActual.getParametro(i).getCriterioPuntuacion(j).getCriterio(), formulaActual.getParametro(i).getNombre() );
                        String puntos = "\n Puntos:";
                        txtCriterio.setText(cadenaCriterio + puntos + formulaActual.getParametro(i).getCriterioPuntuacion(j).getPuntuacion());
                        //TxTPuntuacion.setText(formulaActual.getParametro(i).getCriterioPuntuacion(j).getPuntuacion());
                    }

                    lytDeCriterio.addView(txtCriterio);
                    lytDeCriterio.setBackgroundResource(R.drawable.customborder2);

                    //lytDeCriterio.addView(TxTPuntuacion);
                    //lytDeCriterio.addView(TxTPuntuacion2);
                    lytDeParametro.addView(lytDeCriterio);


                }
            }


            lytBase.addView(lytDeParametro);

            //Muestra la expresion de la formula si existe.

            //Muestro los parametros (creamos otro lyt para esto)
            //Si existen valores minimos y maximos mostramos:
            // Valor comprendido entre ValorMinimo y ValorMaximo
            // Si tinene criterios:
            // Criterio :--> Con su criterio de puntuacion (cambiar && por Y)

            // Por ejemplo  x>= 3 Y < x <= 6  3 puntos.


            //Muestra la expresion de la formula si existe.


            //Muestro la referencia bibliografica


        }


        //Creamos un lyt para mostrar la Expresion de la formula si existe.
        LinearLayout lytDeExpresion = new LinearLayout(this);
        //Le aplicamos un color y un borde específico.
        lytDeExpresion.setOrientation(LinearLayout.VERTICAL);
        lytDeExpresion.setBackgroundColor(Color.parseColor("#D5F8F8"));
        lytDeExpresion.setBackgroundResource(R.drawable.customborder2);



        if (formulaActual.getTipoFormula().equals("general"))
        {
            
            if(formulaActual.getExpresion().equals(""))
            {
                TextView lblFormula = new TextView(this);
                lblFormula.setTypeface(null, Typeface.BOLD);
                lblFormula.setText("Formula");
                lytDeExpresion.addView(lblFormula);

                for (int i = 0; i < formulaActual.contarParametros(); i++) {

                    //Miramos todos los parametroso

                    //Si algún parametro tiene criterios de puntuacion siendo general, es que puede tener varias expresiones asi que las mostramos mostrando antes el criterio
                    if(formulaActual.getParametro(i).contarCriterios() > 0 )
                    {
                        for (int j = 0; j < formulaActual.getParametro(i).contarCriterios(); j++)
                        {
                            TextView txtNombreDeExpresion = new TextView(this);
                            //Mostramos todas las posibles expresiones de la formula
                            txtNombreDeExpresion.setText(formulaActual.getParametro(i).getCriterioPuntuacion(j).getCriterio() + ":" +  formulaActual.getParametro(i).getCriterioPuntuacion(j).getPuntuacion());
                            lytDeExpresion.addView(txtNombreDeExpresion);

                        }
                    }


                }
                lytBase.addView(lytDeExpresion);
            }


            else
            {
                TextView txtNombreDeExpresion = new TextView(this);
                txtNombreDeExpresion.setText(formulaActual.getExpresion());
                lytDeExpresion.addView(txtNombreDeExpresion);
                lytBase.addView(lytDeExpresion);

            }

        }

        //Creamos un layout para mostrar la bibliografía de la formula.
        LinearLayout lytBibliografia = new LinearLayout(this);
        lytBibliografia.setOrientation(LinearLayout.VERTICAL);
        lytBibliografia.setBackgroundColor(Color.parseColor("#D5F8F8"));
        //Creamos un textView para agregar el campo de bibliografia.
        lytBibliografia.setBackgroundResource(R.drawable.customborder2);
        TextView txtNombreBibliografia = new TextView(this);
        txtNombreBibliografia.setText(formulaActual.getBibliografia());
        lytBibliografia.addView(txtNombreBibliografia);
        lytBase.addView(lytBibliografia);


/*
        final Button btnRegresar = new Button(this);
        btnRegresar.setText("Regresar");
        btnRegresar.setBackgroundResource(R.drawable.seleccion);
        btnRegresar.setTextColor(Color.parseColor("#FFFFFF"));
        lytBase.addView(btnRegresar);


        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creamos el Intent
               finish();

            }
        });
*/


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



    public String cambiarFormatoCriterio (String original, String nombreParametro)
    {
        String cadenaCambiada = "";

        //Lo primero es separar todos los campos de la cadena separados con sus spacios utilizando la funcion split

        String vectorPalabras [] = original.split(" ");
        int contador = 0;

        //Ahora voy a recorrer los elementos utilizando un bucle cada vez que encuentre una x incremento el contador

        //en este buscle se pretender cambiar los siguientes simbolos por.

        //x --> Primera aparicion: nombreParametro

        //x --> Segunda o posterios aparicion es eliminado  "";

        //&& --> y

        // < --> Menor a

        // > --> Mayor a

        // Menor ó igual a

        //Mayor ó igual a

        // Puntuacion si es 1--> 1 punto.

        //Puntuación si es dipstinto de 1 --> X puntos.


        for(int i =0; i < vectorPalabras.length; i++ )
        {
         switch (vectorPalabras[i])
         {
             case "x" :
                 if (contador < 1) {
                     vectorPalabras[i] = nombreParametro;
                     contador++;
                 }
                 else {
                     vectorPalabras[i] = "";
                     contador++;
                 }
             break;

             case "&&":
                vectorPalabras[i]= "y";
             break;

             case "<":
                 vectorPalabras[i]= "Menor a";
             break;

             case ">":
                 vectorPalabras[i]= "Mayor a";
             break;

             case "<=":
                 vectorPalabras[i]= "Menor ó igual a";
             break;

             case ">=":
                 vectorPalabras[i]= "Mayor ó igual a";
             break;


         }
        }

        for (String vectorPalabra : vectorPalabras) {
            cadenaCambiada = cadenaCambiada + " " + vectorPalabra;
        }



        return cadenaCambiada;
    }

}
