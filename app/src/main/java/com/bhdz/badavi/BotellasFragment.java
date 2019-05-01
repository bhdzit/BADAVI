package com.bhdz.badavi;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BotellasFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BotellasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BotellasFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public static View view;
    public final String TAG="VentasFragment";

    private OnFragmentInteractionListener mListener;
    public static LayoutInflater layoutInflater;

    private ViewGroup scroll;

    public static ViewGroup viewGroup;

    public BotellasFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BotellasFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BotellasFragment newInstance(String param1, String param2) {
        BotellasFragment fragment = new BotellasFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){   // Inflate the scroll for this fragment

    view=inflater.inflate(R.layout.fragment_botellas,container,false);
        try {
            layoutInflater=inflater;
            viewGroup=container;
            new ConsultaBotellas().execute("http://192.168.201.57/bebidas_maquina.php").get();
/*
            //Se
            scroll = (ViewGroup) view.findViewById(R.id.content_botellas);
            //Layout horisontal
            LinearLayout contenedorBotellas = (LinearLayout) inflater.inflate(R.layout.conten_view, container, false);
            //
            LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.vertical_layout, container, false);
            for (int i = 1; i <= 10; i++) {

                View botella = inflater.inflate(R.layout.botella_view, container, false);
                TextView textView = (TextView) botella.findViewById(R.id.txtvbotella);
               // textView.setText(botellas.get(2).toString());


                contenedorBotellas.addView(botella);
                if (i % 2 == 0) {
                    linearLayout.addView(contenedorBotellas);
                    contenedorBotellas = (LinearLayout) inflater.inflate(R.layout.conten_view, container, false);
                }

            }
            scroll.addView(linearLayout);*/
        }
        catch (Exception e){
            Log.e(TAG,e.getMessage());

        }
    //  scroll.addView(contenedorBotellas);
        return view;//inflater.inflate(R.scroll.fragment_ventas, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}



class ConsultaBotellas extends AsyncTask<String, Void, String> {
    public final String TAG="Consulta";
    public static JSONArray Resultado;
    private ViewGroup scroll;
    public LayoutInflater inflater=BotellasFragment.layoutInflater;
    public ViewGroup container=BotellasFragment.viewGroup;
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

        try {
            ja = new JSONArray(result);
            //Resultado=ja;
            Log.e(TAG,"adasdad"+ja.get(1));


            //Se
            scroll = (ViewGroup) BotellasFragment.view.findViewById(R.id.content_botellas);
            //Layout horisontal
            LinearLayout contenedorBotellas = (LinearLayout) inflater.inflate(R.layout.conten_view, container, false);
            //
            LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.vertical_layout, container, false);

            for (int i = 1; i <= 10; i++) {
                JSONArray botellaja=null;
                int id=0;
                try{
                    botellaja=ja.getJSONArray(i-1);
                    id=botellaja.getInt(0);
                }catch (Exception e){

                }
                View botella = inflater.inflate(R.layout.botella_view, container, false);
                TextView textView = (TextView) botella.findViewById(R.id.txtvbotella);

                if(botellaja!=null&&id==i) {
                    textView.setText(botellaja.get(3).toString());
                }else{
                    textView.append(" "+i);
                }
                contenedorBotellas.addView(botella);
                if (i % 2 == 0) {
                    linearLayout.addView(contenedorBotellas);
                    contenedorBotellas = (LinearLayout) inflater.inflate(R.layout.conten_view, container, false);
                }

            }
            scroll.addView(linearLayout);




        } catch (JSONException e) {
            Log.e(TAG,"eroro->"+e.getMessage());
        }

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