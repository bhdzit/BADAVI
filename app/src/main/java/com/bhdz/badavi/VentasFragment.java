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


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link VentasFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link VentasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VentasFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public View view;
    public final String TAG="VentasFragment";
    private OnFragmentInteractionListener mListener;

    public VentasFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VentasFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VentasFragment newInstance(String param1, String param2) {
        VentasFragment fragment = new VentasFragment();
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
    private ViewGroup layout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_ventas,container,false);
        Log.e(TAG,""+view.getWidth());
        layout=(ViewGroup)view.findViewById(R.id.content_botellas);

        LinearLayout linearLayout=(LinearLayout)inflater.inflate(R.layout.conten_view,null,false);
            for(int i=1;i<10;i++){

                View botella=inflater.inflate(R.layout.botella_view,container,false);
                TextView textView=(TextView) botella.findViewById(R.id.txtvbotella);
                textView.append(" "+i);
                linearLayout.addView(botella);
            if(i%2==0){
                layout.addView(linearLayout);
                linearLayout=(LinearLayout)inflater.inflate(R.layout.conten_view,null,false);

            }
            }

      //  layout.addView(linearLayout);
        return view;//inflater.inflate(R.layout.fragment_ventas, container, false);
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
