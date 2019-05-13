package com.bhdz.badavi;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CoctelesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CoctelesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CoctelesFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public final String TAG="CoctelesFragment";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public View view;
    private ViewGroup scroll;
    public static int BotellaId;
    private OnFragmentInteractionListener mListener;

    public CoctelesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CoctelesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CoctelesFragment newInstance(String param1, String param2) {
        CoctelesFragment fragment = new CoctelesFragment();
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
                             Bundle savedInstanceState) {


        view=inflater.inflate(R.layout.fragment_cocteles,container,false);

        try {
            String result = new Consultas().execute(Consultas.servidor+"getCocteles.php").get();
            Log.e(TAG,"->"+result);
            // if (result != null) {
            JSONArray ja = null;

            ja = new JSONArray(result);
            //Se
            scroll = (ViewGroup) view.findViewById(R.id.content_cocteles);
            //Layout horisontal
            LinearLayout contenedorBotellas = (LinearLayout) inflater.inflate(R.layout.conten_view, container, false);
            //
            LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.vertical_layout, container, false);

            for (int i = 1; i <= ja.length(); i++) {
                JSONArray cocteljason = null;

                try {
                    cocteljason = ja.getJSONArray(i-1);

                } catch (Exception e) {
                    Log.e(TAG, e.getMessage());
                }
                final View coctel = inflater.inflate(R.layout.coctel_view, container, false);
                coctel.setId(i);
                TextView textView = (TextView) coctel.findViewById(R.id.txtvbotella);
                if(cocteljason!=null)
                textView.setText(cocteljason.getString(1));


                final String botellastr = textView.getText().toString();
                coctel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        FragmentManager fragmentManager = NavigationActivity.fragmentManager;
                       AddCoctel newFragment = new AddCoctel();
                       newFragment.show(NavigationActivity.fragmentManager,"Cocteles");
                      //  new ModificarBotella().show(NavigationActivity.fragmentManager, "SimpleDialog");
                      //  BotellaId=botella.getId();

                    }
                });
                contenedorBotellas.addView(coctel);
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
         //   mostrarMsj("Error de la base de datos");
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
