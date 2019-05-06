package com.bhdz.badavi;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import android.widget.SearchView;

public class ModificarBotella extends DialogFragment implements SearchView.OnQueryTextListener{

    public static Context context;
    public final String TAG="ModificarBotella";
    public static List<String>listaidBotellas= new ArrayList<>();
    public static List<String>listaidMarcas=new ArrayList<>();
    public RecyclerView rv;
    public List<String> botellasList;
    Adapter rvadapter;
    public SearchView searchView;

    ListView lvBotellas;
    public ModificarBotella(){

    }
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return createDialogo();
    }
    public AlertDialog createDialogo() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        context=this.getContext();
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View v = inflater.inflate(R.layout.modificar_botella_layout, null);

    searchView=v.findViewById(R.id.searvbotella);
    searchView.setOnQueryTextListener(this);
        new ConsultaMercancia().execute("http://192.168.201.52/getMercancia.php");

        builder.setView(v);
        v.findViewById(R.id.btnOk).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listaidBotellas.add("dadad");

                listaidBotellas.add("dadad");

                listaidBotellas.add("dadad");

                listaidBotellas.add("dadad");

                listaidBotellas.add("dadad");
                rvadapter.notifyDataSetChanged();
           }
        });
        botellasList= new ArrayList<>();
        rvadapter = new Adapter(listaidBotellas);


        rv=v.findViewById(R.id.rvBotellas);
        rv.setLayoutManager(new LinearLayoutManager(v.getContext()));

        Log.e(TAG,"->"+listaidBotellas.size());
        rv.setAdapter(rvadapter);

        return builder.create();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        Log.e(TAG,newText);
        return false;
    }
}

class ConsultaMercancia extends AsyncTask<String, Void, String> {
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
    // onPostExecute displays the results of the AsyncTask.
    @Override
    protected void onPostExecute(String result) {

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
        ArrayAdapter adapter = new ArrayAdapter<String>(ModificarBotella.context,
                android.R.layout.simple_spinner_dropdown_item, lsitMercacnia);
        ArrayAdapter adapter1 = new ArrayAdapter<String>(ModificarBotella.context,
                android.R.layout.simple_spinner_dropdown_item, listMarcas);
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
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
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
class setBotellas extends  AsyncTask<String, Void, String> {
    public final String TAG = "Consulta";
    //public static JSONArray Resultado;
    // private ViewGroup scroll;
    public List<String> lsitMercacnia;
    public List<String> listMarcas;
    public LayoutInflater inflater = BotellasFragment.layoutInflater;

    @Override
    protected String doInBackground(String... urls) {

        // params comes from the execute() call: params[0] is the url.
        try {
            return downloadUrl(urls[0]);
        } catch (IOException e) {
            return "Unable to retrieve web page. URL may be invalid.";
        }
    }

    // onPostExecute displays the results of the AsyncTask.
    @Override
    protected void onPostExecute(String result) {


    }


    private String downloadUrl(String myurl) throws IOException {
        Log.i("URL", "" + myurl);
        myurl = myurl.replace(" ", "%20");
        InputStream is = null;
        // Only display the first 500 characters of the retrieved
        // web page content.
        int len = 500;

        try {
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
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