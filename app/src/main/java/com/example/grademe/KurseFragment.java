package com.example.grademe;

import android.app.Activity;
import android.app.Notification;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.grademe.domain.ModulesModel;
import com.example.grademe.exceptions.NotLoggedInException;

/**
 * Created by user on 12/31/15.
 */

public class KurseFragment extends Fragment {

    View myView;
    private ListView listView;
    private ModulesModel modules;
    private SessionManager session;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        session = ((GradeMeApp)getActivity().getApplication()).getSessionManager();
        myView = inflater.inflate(R.layout.kurse_layout, container, false);
        listView = (ListView) myView.findViewById(R.id.listViewModules);

        modules = new ModulesModel(Long.parseLong(session.getUserDetails().get(session.KEY_ID)));
        assert modules != null : " modules ist null. (in class KurseFragment) Rest Schittstelle wahrscheinliche nicht erreicht";

        ListAdapter listAdapter = new ModuleListAdapter(getActivity(),modules.getModules().getValue());
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){


            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){

                }
                else{

                }
            }
        });
        listView.setAdapter(listAdapter);
        return myView;
    }



}
