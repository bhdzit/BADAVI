package com.bhdz.badavi;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import android.support.v7.widget.SearchView;

public class ModificarBotella extends DialogFragment implements SearchView.OnQueryTextListener{

    public static Context context;
    public final String TAG="ModificarBotella";
    public static int idMercancia;
    public RecyclerView rvMercancia,rvMarcas;
    public List<Mercancia> MercanciaList,prueba;
    AdapterMercancia adapterMercancia;
    AdapaterMarca adapaterMarca;
    public static SearchView searchViewMercancia,searchViewMarca;
    public static ProgressDialog mProgressDialog;
    ListView lvBotellas;
    public ModificarBotella(){

    }
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return createDialogo();
    }
    public AlertDialog createDialogo() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        mProgressDialog = new ProgressDialog(this.getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View v = inflater.inflate(R.layout.modificar_botella_layout, null);

    searchViewMercancia =v.findViewById(R.id.searvbotella);
    searchViewMercancia.setOnQueryTextListener(this);

    searchViewMarca=v.findViewById(R.id.searvMarca);
    searchViewMarca.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String s) {
            return false;
        }

        @Override
        public boolean onQueryTextChange(String s) {
            adapaterMarca.filter(s);
            return false;
        }
    });
        MercanciaList= new ArrayList<>();
        prueba= new ArrayList<>();
        try {
        String result =  new Consultas().execute(Consultas.servidor+"getMercancia.php").get();
            JSONArray ja = null;
            ja = new JSONArray(result);
            //Resultado=ja;

            for (int i =0;i<ja.length()-1;i++) {
                JSONArray j7=ja.getJSONArray(i);
                Mercancia mercancia= new Mercancia(j7.getInt(0),j7.getString(1),j7.getString(2),j7.getInt(3));

                MercanciaList.add(mercancia);
                Log.e(TAG,mercancia.toString());
            }

        } catch (ExecutionException e) {
            Log.e(TAG,e.getMessage());
        } catch (InterruptedException e) {
            //e.printStackTrace();
            Log.e(TAG,e.getMessage());
        } catch (JSONException e) {
            Log.e(TAG,e.getMessage());
//            e.printStackTrace();
        }

        builder.setView(v);
        v.findViewById(R.id.btnOk).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
    try {
        final String queryMercancia=searchViewMercancia.getQuery().toString();
        final String queryMarca=searchViewMarca.getQuery().toString();
        if(queryMercancia.isEmpty() ||queryMarca.isEmpty()){

             AlertDialog alertDialog=createSimpleDialog("Error", "Campo Mercancia o Marca vacio", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            alertDialog.show();
        }
        else{
            if(AdapterMercancia.Mercancia.size()==0&&AdapaterMarca.marcas.size()!=0){
                AlertDialog alertDialog=createSimpleDialog("Aviso", "La Botella "+queryMercancia+" No esta en la base de datos \n ¿Desea Agragrla a la BD?", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        try {

                            String a=    new Consultas().execute(Consultas.servidor+"insert_marca_bebida.php?tabla=TIPOS_BEBIDA&value="+queryMercancia.toUpperCase()+"&columna=NOMBRE").get();
                            String c=    new Consultas().execute(Consultas.servidor+"setMercancia.php?botella="+queryMercancia+"&marca="+queryMarca).get();

                            JSONArray jsonmercancia = null;
                            jsonmercancia = new JSONArray(c);
                            Mercancia aux= new Mercancia(jsonmercancia.getInt(0),jsonmercancia.getString(1),jsonmercancia.getString(2),jsonmercancia.getInt(3));

                            AdapterMercancia.Mercancia.add(aux);
                            adapterMercancia.notifyDataSetChanged();
                            Log.e(TAG,a);
                        } catch (Exception e) {
                            Log.e(TAG,e.getMessage());
                        }
                    }

                });
                alertDialog.show();
            }
            if(AdapaterMarca.marcas.size()==0&&AdapterMercancia.Mercancia.size()!=0){

                AlertDialog alertDialog=createSimpleDialog("Aviso", "La Marca "+queryMarca+" No esta en la base de datos \n ¿Desea Agragrla a la BD?", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        try {

                            String a=    new Consultas().execute(Consultas.servidor+"insert_marca_bebida.php?tabla=MARCA&value="+queryMarca.toUpperCase()+"&columna=MARCA").get();
                            String c=    new Consultas().execute(Consultas.servidor+"setMercancia.php?botella="+queryMercancia+"&marca="+queryMarca).get();

                            JSONArray jsonmercancia = null;
                            jsonmercancia = new JSONArray(c);
                            Mercancia aux= new Mercancia(jsonmercancia.getInt(0),jsonmercancia.getString(1),jsonmercancia.getString(2),jsonmercancia.getInt(3));

                            AdapaterMarca.marcas.add(aux);
                            adapaterMarca.notifyDataSetChanged();
                            Log.e(TAG,a);
                        } catch (Exception e) {
                            Log.e(TAG,e.getMessage());
                        }
                    }

                });

                alertDialog.show();

            }
            if(AdapaterMarca.marcas.size()==0&&AdapterMercancia.Mercancia.size()==0){




                AlertDialog alertDialog=createSimpleDialog("Aviso", "La Marca "+queryMarca.toUpperCase()+" y La Botella "+queryMercancia.toUpperCase()+" No esta en la base de datos \n ¿Desea Agragrla a la BD?", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        try {

                            String a=    new Consultas().execute(Consultas.servidor+"insert_marca_bebida.php?tabla=MARCA&value="+queryMarca+"&columna=MARCA").get();
                            String b=    new Consultas().execute(Consultas.servidor+"insert_marca_bebida.php?tabla=TIPOS_BEBIDA&value="+queryMercancia+"&columna=NOMBRE").get();
                            String c=    new Consultas().execute(Consultas.servidor+"setMercancia.php?botella="+queryMercancia+"&marca="+queryMarca).get();

                            JSONArray jsonmercancia = null;
                            jsonmercancia = new JSONArray(c);
                            Mercancia aux= new Mercancia(jsonmercancia.getInt(0),jsonmercancia.getString(1),jsonmercancia.getString(2),jsonmercancia.getInt(3));
                            AdapaterMarca.marcas.add(aux);
                            adapaterMarca.notifyDataSetChanged();
                            AdapterMercancia.Mercancia.add(aux);
                            adapterMercancia.notifyDataSetChanged();

                            //notifyAll();
                            Log.e(TAG,""+jsonmercancia.get(1));
                        } catch (Exception e) {
                            Log.e(TAG,e.getMessage());
                        }
                    }

                });

                alertDialog.show();

            }


            if(AdapaterMarca.marcas.size()!=0&&AdapterMercancia.Mercancia.size()!=0) {
                String a = new Consultas().execute(Consultas.servidor + "updateEspacio_maquina.php?id=" + BotellasFragment.BotellaId + "&botella=" + searchViewMercancia.getQuery().toString() + "&marca=" + searchViewMarca.getQuery().toString()).get();
                NavigationActivity.actualizarFragment(new BotellasFragment());
                Log.e(TAG, "->" + a);
                dismiss();
            }

        }

       //         //createSimpleDialog();
        //dismiss();
    }catch (Exception e){
        Log.e(TAG, "" + e.getMessage());
    }
            }
        });


        prueba.addAll(MercanciaList);
        adapterMercancia = new AdapterMercancia(MercanciaList);
        adapaterMarca = new AdapaterMarca(prueba);


        rvMercancia =v.findViewById(R.id.rvBotellas);
        rvMercancia.setLayoutManager(new LinearLayoutManager(v.getContext()));

        rvMarcas=v.findViewById(R.id.rvMarcas);
        rvMarcas.setLayoutManager(new LinearLayoutManager(v.getContext()));



        rvMercancia.setAdapter(adapterMercancia);
        rvMarcas.setAdapter(adapaterMarca);

        return builder.create();
    }


    public AlertDialog createSimpleDialog(String titulo, String msj, DialogInterface.OnClickListener ok) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(titulo)
                .setMessage(msj)
                .setPositiveButton("OK",ok);

       return   builder.create();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
      //  Log.e(TAG,newText);
        adapterMercancia.filter(newText);
        return false;
    }
}
/*
class Consulta extends AsyncTask<String, Void, String> {
    public final String TAG="Consulta";
    //public static JSONArray Resultado;
   // private ViewGroup scroll;
    public List<String> lsitMercacnia;
    public List<String> listMarcas;
    public LayoutInflater inflater=BotellasFragment.layoutInflater;

    @Override
    protected String doInBackground(String... urls) {

        // params comes from the execute() call: params[0] is the url.
        try {
            return downloadUrl(urls[0]);
        } catch (IOException e) {
            return "Unable to retrieve web page. URL may be invalid.";
        }
    }

    @Override
    protected void onPreExecute() {

    }


    // onPostExecute displays the results of the AsyncTask.
    @Override
    protected void onPostExecute(String result) {
/*
        JSONArray ja = null;
        lsitMercacnia=new ArrayList<>();
        listMarcas=new ArrayList<>();
        try {
            ja = new JSONArray(result);
            //Resultado=ja;
            Log.e(TAG,"adasdad"+ja.get(1));
            for (int i =0;i<ja.length()-1;i++) {
                JSONArray mercancia=ja.getJSONArray(i);
                Log.e(TAG,mercancia.get(1).toString());
                ModificarBotella.listaidBotellas.add(mercancia.get(0).toString());
                ModificarBotella.listaidMarcas.add(mercancia.get(3).toString());
                lsitMercacnia.add(mercancia.get(1).toString());
                listMarcas.add(mercancia.get(2).toString());
            }

        } catch (JSONException e) {
            Log.e(TAG,"eroro->"+e.getMessage());
        }
    //    ModificarBotella.spMarcas.setAdapter(adapter1);
    //    ModificarBotella.spBotellas.setAdapter(adapter);

    }


    private String downloadUrl(String myurl) throws IOException {
        Log.i("URL",""+myurl);
        myurl = myurl.replace(" ","%20");
        InputStream is = null;
        // Only display the first 500 characters of the retrieved
        // web page content.
        int len = 500;

        try {
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000  milliseconds)
            conn.setConnectTimeout(15000 milliseconds);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the query
            conn.connect();
            int response = conn.getResponseCode();
            Log.d("respuesta", "The response is: " + response);
            is = conn.getInputStream();

            // Convert the InputStream into a string
            String contentAsString = readIt(is, len);
            return contentAsString;

            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    public String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
        Reader reader = null;
        reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[len];
        reader.read(buffer);
        return new String(buffer);
    }
}
*/