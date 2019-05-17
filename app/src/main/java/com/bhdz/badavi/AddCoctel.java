package com.bhdz.badavi;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import org.json.JSONArray;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.R.layout.simple_spinner_item;


public class AddCoctel extends DialogFragment implements View.OnClickListener {
    public FloatingActionButton floatingActionButton;
    public View rootView;
    public LayoutInflater inflater;
    public ViewGroup container;
    public List<Mercancia> MercanciaList;
    public List<String> list;
    public List<String []> procesosList= new ArrayList<>();
    AdapterMercancia adapterMercancia;
    LinearLayout contendorMercancia;
    public  final String TAG="AddCoctel";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.inflater=inflater;
        this.container=container;


         rootView = inflater.inflate(R.layout.add_coctel_layout, container, false);
        contendorMercancia=rootView.findViewById(R.id.add_coctel_prcess_layout);
        rootView.findViewById(R.id.button_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dismiss();
            }
        });
        rootView.findViewById(R.id.button_action).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ViewGroup aux=rootView.findViewById(R.id.add_coctel_prcess_layout);
                  // String a= new Consultas().execute(Consultas.servidor+"incertCoctel.php?array=123").get();
                    String a="[";
                   for(int i=1;i<aux.getChildCount();i++){
                       String coctelname="";
                       if(i==1){
                           View view=aux.getChildAt(1);
                           EditText editText=(EditText) view;

                       }else
                      {
                          View mercanciaView=aux.getChildAt(i);
                          Spinner botella= (Spinner)mercanciaView.findViewById(R.id.option_mercancia);
                          EditText volumen= (EditText)mercanciaView.findViewById(R.id.smedida);
                          Spinner media=(Spinner)mercanciaView.findViewById(R.id.umedida);
                          String mercancia=
                                 "{"+"\"botella\":\""+botella.getSelectedItem().toString()
                                 +"\",\"volumen\":\""+volumen.getText().toString()+" "+ media.getSelectedItem().toString()+"\"}";

                          //String a= new Consultas().execute(Consultas.servidor+"incertCoctel.php?array="+coctelname).get();
                         a+=mercancia;
                          if(aux.getChildCount()-1>i)a+=",";
                      }
            //    [{"botella": REFRESCO TORONJA ,"volumen": ml},{"botella":REFRESCO TORONJA,"volumen": ml},{"botella":REFRESCO TORONJA,"volumen": ml},{"botella":REFRESCO TORONJA,"volumen": ml},]
            //    [{ "id": 6, "text": "10", "item": 0}, { "id": 7, "text": "45", "item": 1}, { "id": 8, "text": "3^2", "item": 2}, { "id": 9, "text": "6", "item": 3}, { "id": 10, "text": "666", "item": 4}]
                   }

                   a+="]";
                    Log.e(TAG,a);
                   dismiss();
                }
                catch (Exception e)
                {
                    Log.e(TAG,e.getMessage());
                }
            }
        });
        floatingActionButton=rootView.findViewById(R.id.add_options_ccotel);
        floatingActionButton.setOnClickListener(this);



        MercanciaList= new ArrayList<>();
        list= new ArrayList<>();
        try {
            String result =  new Consultas().execute(Consultas.servidor+"getMercancia.php").get();
            JSONArray ja = null;
            ja = new JSONArray(result);
            //Resultado=ja;

            for (int i =0;i<ja.length()-1;i++) {
                JSONArray j7=ja.getJSONArray(i);
                Mercancia mercancia= new Mercancia(j7.getInt(0),j7.getString(1),j7.getString(2),j7.getInt(3));

                MercanciaList.add(mercancia);
                list.add(mercancia.NombreMercancia);
               // Log.e(TAG,mercancia.toString());
            }

           //
           contendorMercancia.addView(addCoctelOptions(inflater,container));

        }catch (Exception e){
            rootView=inflater.inflate(R.layout.error_layout, container, false);

        }


        return rootView;



    }
    public View addCoctelOptions(LayoutInflater inflater,ViewGroup container){

        LinearLayout contenedorOptionsMercancia = (LinearLayout) inflater.inflate(R.layout.coctel_options_layout, container, false);

        Spinner spinner = (Spinner) contenedorOptionsMercancia.findViewById(R.id.umedida);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(),
                R.array.unidades, simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);



        Spinner spinner1=(Spinner) contenedorOptionsMercancia.findViewById(R.id.option_mercancia);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_item,list);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);




        return contenedorOptionsMercancia;
    }

    @Override
    public void dismiss() {
        CoctelesFragment.showDialog=false;
        super.dismiss();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onClick(View v) {
       // contendorMercancia=rootView.findViewById(R.id.add_coctel_prcess_layout);

        contendorMercancia.addView(addCoctelOptions(inflater,container));
    }
}