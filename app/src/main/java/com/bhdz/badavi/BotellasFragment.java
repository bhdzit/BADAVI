package com.bhdz.badavi;

import android.app.AlertDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


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
    public static final String TAG="BotellasFragment";
    public static int BotellaId;
    private OnFragmentInteractionListener mListener;
    public static LayoutInflater layoutInflater;

    private ViewGroup scroll;
    public static Context context;

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

        Log.e(TAG,"BotellasFragment");
    view=inflater.inflate(R.layout.fragment_botellas,container,false);
        Log.e(TAG,"BotellasFragment");
        try {
            String result = new Consultas().execute(Consultas.servidor+"bebidas_maquina.php").get();
            Log.e(TAG,"->"+result);
           // if (result != null) {
                JSONArray ja = null;

                ja = new JSONArray(result);
                //Se
                scroll = (ViewGroup) BotellasFragment.view.findViewById(R.id.content_botellas);
                //Layout horisontal
                LinearLayout contenedorBotellas = (LinearLayout) inflater.inflate(R.layout.conten_view, container, false);
                //
                LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.vertical_layout, container, false);
                int numeroBotella=0;
                for (int i = 1; i <= 10; i++) {
                    JSONArray botellaja = null;
                    int id = 0;
                    try {
                        botellaja = ja.getJSONArray(numeroBotella);
                        id = botellaja.getInt(0);
                    } catch (Exception e) {
                        Log.e(TAG, e.getMessage());
                    }
                    final View botella = inflater.inflate(R.layout.botella_view, container, false);
                    botella.setId(i);
                    TextView textView = (TextView) botella.findViewById(R.id.txtvbotella);

                    if (botellaja != null && id == i) {
                        textView.setText(botellaja.get(3).toString()+"-"+botellaja.getString(4));
                        numeroBotella++;
                    } else {
                        textView.append(" " + i);
                    }

                    final String botellastr = textView.getText().toString();
                    botella.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            new ModificarBotella().show(NavigationActivity.fragmentManager, "SimpleDialog");
                            BotellaId=botella.getId();

                        }
                    });
                    contenedorBotellas.addView(botella);
                    if (i % 2 == 0) {
                        linearLayout.addView(contenedorBotellas);
                        contenedorBotellas = (LinearLayout) inflater.inflate(R.layout.conten_view, container, false);
                    }

                }
                scroll.addView(linearLayout);

            // }
            }
        catch(Exception e){
                Log.e(TAG, "->" + e.getMessage());
                mostrarMsj("Error de la base de datos");
                view = inflater.inflate(R.layout.error_layout, container, false);
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

    public  void mostrarMsj(String a) {
        Context context = getContext();
        CharSequence text = a;
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
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

