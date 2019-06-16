package com.example.grademe;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.grademe.datatransferobject.CategoryRatingDTO;

import java.util.List;

/**
 * Created by user on 12/31/15.
 */

public class NotenFragment extends Fragment {

    View myView;
    private ListView listView;
    private List<CategoryRatingDTO> grades;
    private Button btnBewerten;
    private SessionManager session;
    FragmentManager fragmentManager;
    private Button btnNewGrade;

    public static NotenFragment newInstance(List<CategoryRatingDTO> grades) {

        NotenFragment fragment = new NotenFragment();
        fragment.grades = grades;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        session = ((GradeMeApp)getActivity().getApplication()).getSessionManager();

        fragmentManager = getFragmentManager();
        myView = inflater.inflate(R.layout.fragment_noten, container, false);
        listView = (ListView) myView.findViewById(R.id.listViewNoten);
        btnNewGrade = (Button) myView.findViewById(R.id.btnSubmit);
        btnNewGrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame,RateFragment.newInstance(grades))
                        .commit();
            }
        });

        ListAdapter listAdapter = new CategoryRatingListAdapter(getActivity(),grades);
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){


            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame,RateFragment.newInstanceEdit(grades,grades.get(position)))
                        .commit();
            }
        });
        //listView.setAdapter(listAdapter);

        String role = session.getUserDetails().get(session.KEY_ROLE);
        if(role.equals("teacher"))
        {
            btnNewGrade.setVisibility(View.VISIBLE);
        }
        else
        {
            btnNewGrade.setVisibility(View.INVISIBLE);
        }
        return myView;
    }



}
