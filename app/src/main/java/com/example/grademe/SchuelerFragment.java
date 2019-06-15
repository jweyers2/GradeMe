package com.example.grademe;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.grademe.datatransferobject.SubPuMoCaDTO;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SchuelerFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SchuelerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SchuelerFragment extends Fragment {


    private OnFragmentInteractionListener mListener;
    View view;
    private List<SubPuMoCaDTO> subPuMoCaDTOS;
    private ListView listView;
    private Button btnShowQR;
    FragmentManager fragmentManager;
    private Long qrcode;
    public SchuelerFragment() {
        // Required empty public constructor
    }


    public static SchuelerFragment newInstance(Long qrcode,List<SubPuMoCaDTO> subPuMoCaDTOS) {
        SchuelerFragment fragment = new SchuelerFragment();
        fragment.qrcode = qrcode;
        fragment.subPuMoCaDTOS = subPuMoCaDTOS;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //pupils.getQrcode()


        view = inflater.inflate(R.layout.fragment_schueler, container, false);
        btnShowQR = (Button) view.findViewById(R.id.btnShowQR);
        btnShowQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), QRShow.class);
                intent.putExtra("id", qrCode);
                startActivity(intent);
            }
        });
        fragmentManager = getFragmentManager();
        if(this.subPuMoCaDTOS != null && this.subPuMoCaDTOS.size() > 0 ){
            listView = (ListView) view.findViewById(R.id.listViewPupils);
            ListAdapter listAdapter = new SuPuMoCaListAdapter(getActivity(),subPuMoCaDTOS);
//            listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
//
//
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    fragmentManager.beginTransaction()
//                            .replace(R.id.content_frame
//                                    ,NotenFragment.newInstance(subPuMoCaDTOS.get(position).getMonthCategories()))
//                            .commit();
//                }
//            });
            listView.setAdapter(listAdapter);
            TextView textView = view.findViewById(R.id.noPupilsMessage);
            textView.setVisibility(View.INVISIBLE);
        }
        return view;

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
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
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
