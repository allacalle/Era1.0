package com.example.alfonso.era08;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.InputType;
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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.alfonso.era08.Clases.Formula;

import java.util.ArrayList;
import java.util.List;

public class CalcularFormula extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    String cadenaPrueba = "";
    int indiceCampoIncorrecto = 0 ;
    boolean existenCamposEdit = false;
    String idFormula;
    String nombreFormula;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calcular_formula);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Ponemos el nombre de la pantalla en la tool bar.
        setTitle("Calcular fórmula");



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        final LinearLayout lytBase = (LinearLayout) findViewById(R.id.LytContenedor);
        Button btnRecientes = (Button) findViewById(R.id.BtnRecientes) ;
        Button btnInicio = (Button) findViewById(R.id.BtnInicio) ;
        //Button BtnAyuda = (Button) findViewById(R.id.BtnAyuda) ;


        //Lo primero es capturar la abreviatura de la formula que se nos pasa en un bundle
        //Recuperamos la información pasada en el intent
        Bundle bundle = this.getIntent().getExtras();
        //Construimos el mensaje a mostrar
        final String valorRecibido = bundle.getString("IdFormula");
        idFormula = valorRecibido;

        //Se crea una formula con el id que nos han pasado.
        //Se crea una formula con el id que nos han pasado.
        final Formula formulaActual = new Formula(idFormula, getApplicationContext());
        //Sacamos el nombre de la formula para futuros usos.
        nombreFormula = formulaActual.getAbreviatura();
        //Parametro parametro = new Parametro("2" , getApplicationContext());
        //Lista para los editText
        final List<EditText> allEds = new ArrayList<EditText>();
        //Lista para los checkbox
        final List<CheckBox> allChs = new ArrayList<CheckBox>();
        //Lista para los radial group
        final List<RadioGroup> allRgs = new ArrayList<RadioGroup>();

        //Se crea un parametro auxiliar para cuestiones de diseño con el TextView y el EditText
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);

        LinearLayout.LayoutParams param2 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT, 3.0f);




        //antes de nada creamos un layaout con un label de nombre de la formula y a su derecho un boton de información.
        LinearLayout lytCabecera = new LinearLayout(this);
        lytCabecera.setOrientation(LinearLayout.HORIZONTAL);
        //Creamos un textView donde mostraremos el nombre de la Formula aplicandole un formato distinto.
        TextView txtNombreDeFormula = new TextView(this);
        txtNombreDeFormula.setText(formulaActual.getNombreCompleto());
        txtNombreDeFormula.setLayoutParams(param);
        txtNombreDeFormula.setTextSize(20);
        txtNombreDeFormula.setTypeface(null, Typeface.BOLD);
        txtNombreDeFormula.setGravity(Gravity.CENTER_HORIZONTAL);
        //Creamos el boton Ayuda que nos mostrará una ayuda detallada de la fórmula.
        Button btnAyuda = new Button(this);
        btnAyuda.setText("Ayuda");
        btnAyuda.setLayoutParams(param);
        lytCabecera.addView(txtNombreDeFormula);
        lytCabecera.setBackgroundResource(R.drawable.customborder);


        //tenemos que ver si alguno de los parametros es numero para mostrar o no el boton ayuda.
        for (int i = 0; i < formulaActual.contarParametros(); i++) {
            if (formulaActual.getParametro(i).getTipo().equals("numero"))
                existenCamposEdit = true;
        }

        lytBase.addView(lytCabecera);


        for (int i = 0; i < formulaActual.contarParametros(); i++) {
            //Creamos un linear lyt para cada iteraccón , en el estarán todos los elementos
            LinearLayout lytDeParametro = new LinearLayout(this);
            lytDeParametro.setOrientation(LinearLayout.HORIZONTAL);
            lytDeParametro.setBackgroundColor(Color.parseColor("#D5F8F8"));
            //Creamos un TextView para mostrar el nombre de cada parámetro de la fórmula.
            lytDeParametro.setBackgroundResource(R.drawable.customborder);
            TextView txtNombreDeParametro = new TextView(this);
            //Ponemos el nombre del parametro en negrita
            txtNombreDeParametro.setTypeface(null, Typeface.BOLD);
            //txtNombreDeParametro.setText(formulaActual.getParametro(i).getNombre());

            if (formulaActual.getParametro(i).getMedida() != null)
                txtNombreDeParametro.setText("" +formulaActual.getParametro(i).getNombre()+ "(" +formulaActual.getParametro(i).getMedida()+ ")." );
            else
                txtNombreDeParametro.setText(formulaActual.getParametro(i).getNombre());

            txtNombreDeParametro.setLayoutParams(param);
            lytDeParametro.addView(txtNombreDeParametro);
            //Ahora dependiendo del tipo de parametro creamos un elemento u otro

            switch (formulaActual.getParametro(i).getTipo()) {
                //Si es del tipo numero se crea una caja de texto
                case "numero":
                    EditText etxValorNumeroParametro = new EditText(this);
                    etxValorNumeroParametro.setLayoutParams(param2);
                    etxValorNumeroParametro.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
                    etxValorNumeroParametro.setInputType(InputType.TYPE_CLASS_PHONE);
                    //etxValorNumeroParametro.setId(formulaActual.getParametro(i).getIdParametro());
                    allEds.add(etxValorNumeroParametro);
                    lytDeParametro.addView(etxValorNumeroParametro);
                    break;
                //Se crea un checbox para cada uno de los criterios de puntuacion
                case "logico":
                    CheckBox cbxValorLogicoParametro = new CheckBox(this);
                    cbxValorLogicoParametro.setLayoutParams(param2);
                    //cbxValorLogicoParametro.setId(formulaActual.getParametro(i).getIdParametro());
                    allChs.add(cbxValorLogicoParametro);
                    lytDeParametro.addView(cbxValorLogicoParametro);
                    break;

                //Se crea un radial group con un radial button para cada una de las opciones
                case "lista":
                    RadioGroup rgpValorListaParametro = new RadioGroup(this);
                    rgpValorListaParametro.setLayoutParams(param);
                    for (int j = 0; j < formulaActual.getParametro(i).contarCriterios(); j++) {
                        RadioButton opcion = new RadioButton(this);
                        //Asignamos a cada uno de los botones de opcion como su id la de su criterio para poder encontrarlos luego
                        opcion.setId(formulaActual.getParametro(i).getCriterioPuntuacion(j).getIdCriterioPuntuacion());
                        opcion.setText(formulaActual.getParametro(i).getCriterioPuntuacion(j).getCriterio());
                        rgpValorListaParametro.addView(opcion);
                    }
                    allRgs.add(rgpValorListaParametro);
                    lytDeParametro.addView(rgpValorListaParametro);

                    break;
            }


            lytBase.addView(lytDeParametro);

        }

        /* Se creará un botón que al pulsarlo:
        1 Evalue que todos los datos introducidos sean correctos (Valores Prohibidos, minimos, maximos,
        seleccionar al menos una opcion los radio button)
        2 AlytBaseacenara todos los valores introducidos en un vector que pasaremos a la siguiente pantalla para realizar los calculos.
         2.1 Para coger los datos debemos acceder a los alytBaseacenes donde estan. Para ello sera necesario
          Crear un bucle que vaya entrando a cada uno de los list<> dependiendo del tipo de datos de cada alytBaseacen
          Como son 3 tipos distintos de list<> necesitamos 3 variables para ir alytBaseacenando la posicion actual de cada iterador.
          Cuando tengamos todos los valores podemos crear una cadena que contenta idParametro: valorParametro; IdParametro:valor...

         */

        //creamos el boton que al pulsar calculará el resultado de la formula.

        final ImageButton btnCalcular = new ImageButton(this);
        btnCalcular.setImageResource(R.drawable.check);
        btnCalcular.setBackgroundColor(Color.parseColor("#FFFFFF"));
        lytBase.addView(btnCalcular);

        LinearLayout lytContenedor = (LinearLayout) findViewById(R.id.LytContenedor);
        lytContenedor.setBackgroundResource(R.drawable.customborder2);






        //creamos una ventana emergente que mostrará mensajes de error o el resultado final de la fórmula.
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();

        alertDialog.setButton("Continuar..", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // here you can add functions
            }
        });

        //Creamos un TextView para mostrar el resultado en pantalla. Aumentamos su tamaño para que se vea mejor
        final TextView txtResultado = new TextView(this);
        txtResultado.setTextSize(30);
        lytBase.addView(txtResultado);



        btnCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Necesitamos 3 iteradores uno para cada tipo de list<>
                int iEdit = 0;
                int iCheck = 0;
                int iRadio = 0;
                String aux = "";
                cadenaPrueba = "";
                //Creamos una variable bool que sera true cuando todos los campos tengan valores correctos
                boolean camposCorrectos = true;
                //Creamos una varable donde alytBaseacenaremos el indice del parametro incorrecto para despues mostrarlo en pantalla.
                indiceCampoIncorrecto = -1;
                //Vamos a meter todos los resultados en un vector de cadenas
                String[] vectorResultados = new String[formulaActual.contarParametros()];
                //Vamos a extraer los valores introducidos

                //Creamos una variable String para colocar un mensaje de texto en los errores.
                String mensajeError = "";


                //Contamos el numero de parametros -1 porque no estamos interesados en el parametro resultado
                for (int i = 0; i < formulaActual.contarParametros(); i++) {
                    //Necesitamos 3 iteradores uno para cada tipo de list<>
                    switch (formulaActual.getParametro(i).getTipo()) {
                        case "numero":
                            vectorResultados[i] = allEds.get(iEdit).getText().toString();
                            iEdit++;
                            //Si el valor no es numerico
                            if (!isNumeric(vectorResultados[i])) {
                                //Como no es un numero ya hay un campo incorrecto.
                                camposCorrectos = false;
                                //Guardamos el indice del campo incorrecto para mostrarlo mas adelante.
                                indiceCampoIncorrecto = i;
                                mensajeError = "El parametro " +formulaActual.getParametro(i).getNombre() + " es incorrecto, debe introducir un numero valido";

                                //Comprobamos que el decimal este separado por una coma en vez de un punto.
                                if (esComaElDecimal(vectorResultados[i]))
                                {
                                    mensajeError = "El parametro " +formulaActual.getParametro(i).getNombre() + " es incorrecto, los numeros decimales deben ir separados por puntos" ;
                                }
                            }

                            //Si el valor es numero pero es menor que el valor numero es incorrecto
                            else if (Float.parseFloat(vectorResultados[i]) < formulaActual.getParametro(i).getValorMinimo()) {
                                camposCorrectos = false;
                                indiceCampoIncorrecto = i;
                                mensajeError = " El parametro "  +formulaActual.getParametro(i).getNombre() +  " es incorrecto, el numero introducido debe ser mayor o igual a " + String.format( "%.2f", formulaActual.getParametro(i).getValorMinimo()) ;
                            }

                            //Si es mayor que el valor maximo es incorrecto
                            else if (Float.parseFloat(vectorResultados[i]) > formulaActual.getParametro(i).getValorMaximo()) {
                                camposCorrectos = false;
                                indiceCampoIncorrecto = i;
                                mensajeError = "El parametro " +formulaActual.getParametro(i).getNombre() + " es incorrecto, el numero introducido debe ser menor o igual a " +String.format( "%.2f", formulaActual.getParametro(i).getValorMaximo()) ;

                            }


                            break;
                        case "lista":
                            aux = "" + allRgs.get(iRadio).getCheckedRadioButtonId();
                            vectorResultados[i] = aux;
                            if (aux.equals("-1")) {
                                camposCorrectos = false;
                                indiceCampoIncorrecto = i;
                                mensajeError = "El parametro " +formulaActual.getParametro(i).getNombre() +  " es incorrecto, debe seleccionar una opción";
                            }

                            iRadio++;
                            //Sino ha seleccionado ninguna opcion es incorrecto

                            break;
                        case "logico":
                            if (allChs.get(iCheck).isChecked())
                                vectorResultados[i] = "1";
                            else
                                vectorResultados[i] = "0";
                            iCheck++;
                            break;
                    }

                    if (!camposCorrectos) {

                        {

                            alertDialog.setMessage(mensajeError);
                            alertDialog.show();
                            break;

                        }

                    }


                    //Introdicimos los valores de los parametros en nuestra formula.
                    //formulaActual.introducirValoresFormula(vectorResultados);

                    //Calculamos los resultados.
                    // formulaActual.calcularFormula();

                    // txtResultado.setText(cadenaPrueba  + "\n Su resultado es \n" + formulaActual.getResultado().getValor());

                    //Agregamos la formula a recientes
                    //Esta funcion no debe tener parametros salvo el context????
                    // formulaActual.introducirRecientes(formulaActual.getIdFormula() , formulaActual.getResultado().getValor(), getApplicationContext());




                }
                if (camposCorrectos) {
                    //Introdicimos los valores de los parametros en nuestra formula.
                    formulaActual.introducirValoresFormula(vectorResultados);

                    //Calculamos los resultados.
                    formulaActual.calcularFormula();

                    //si el resultado es un numero lo redondeamos.


                    String resultadoCriterios = "";

                    //Vamos a mostrar los criterios y sus puntuaciones para poder evaluar que funciona correctamente.
                    //Bucle para recorrer los parametros
                    for (int i = 0; i < formulaActual.contarParametros(); i++) {

                        resultadoCriterios = resultadoCriterios + formulaActual.getParametro(i).getNombre() + ":" + formulaActual.getParametro(i).getValor() + "\n" ;

                    }



                    //Agregamos la formula a recientes
                    //Esta funcion no debe tener parametros salvo el context????
                    formulaActual.introducirRecientes(formulaActual.getIdFormula() , formulaActual.getResultado().getValor(), getApplicationContext());


                    String resultadoFinal ="";


                    if (formulaActual.getResultado().getMedida() == null)
                        resultadoFinal = formulaActual.getResultado().getValor() ;

                    else {
                        resultadoFinal = formulaActual.getResultado().getValor() + " " + formulaActual.getResultado().getMedida();

                    }


                    alertDialog.setMessage(resultadoFinal);
                    alertDialog.show();
                    //txtResultado.setText(resultadoCriterios);
                    txtResultado.setText(resultadoFinal);




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
        getMenuInflater().inflate(R.menu.calcular_formula, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.ayuda) {
            Intent intent =
                    new Intent(CalcularFormula.this, AyudaFormula.class);
            Bundle b = new Bundle();

            //Vamos a pasar el identificador de la formula que es un campo unico .
            b.putString("IdFormula", idFormula );
            //Añadimos la información al intent
            intent.putExtras(b);
            startActivity(intent);
            return true;
        }

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
                    new Intent(CalcularFormula.this, Inicio.class);
            startActivity(intent);
        }

        if (id == R.id.nav_configuracion) {

            Intent intent =
                    new Intent(CalcularFormula.this, CambiarConfiguracion.class);
            startActivity(intent);


        } else if (id == R.id.nav_reportar_error) {
            Intent intent =
                    new Intent(CalcularFormula.this, ReportarError.class);
            startActivity(intent);

        } else if (id == R.id.nav_solicitar_formula) {
            Intent intent =
                    new Intent(CalcularFormula.this, SolicitarFormula.class);
            startActivity(intent);

        } else if (id == R.id.nav_acerca) {
            Intent intent =
                    new Intent(CalcularFormula.this, AcercaDe.class);
            startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public boolean isNumeric(String str) {
        return str.matches("^-?[0-9]+([.][0-9]+)?$");
    }

    public boolean esComaElDecimal (String str) {
        return str.matches("^-?[0-9]+([,][0-9]+)?$");
    }


}
