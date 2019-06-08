package com.example.grademe;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Button;

import com.example.grademe.Model.ModulesModel;

/**
 * Created by user on 12/31/15.
 */

public class KurseFragment extends Fragment {

    View myView;
    private ListView listView;
    private ModulesModel modules;
    private SessionManager session;
    private Button joinButton;
    FragmentManager fragmentManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        session = ((GradeMeApp)getActivity().getApplication()).getSessionManager();

        fragmentManager = getFragmentManager();
        myView = inflater.inflate(R.layout.kurse_layout, container, false);
        listView = (ListView) myView.findViewById(R.id.listViewModules);

        modules = new ModulesModel(Long.parseLong(session.getUserDetails().get(session.KEY_ID)));
        assert modules != null : " modules ist null. (in class KurseFragment) Rest Schittstelle wahrscheinliche nicht erreicht";

        ListAdapter listAdapter = new ModuleListAdapter(getActivity(),modules.getModules().getValue());
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){


            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame
                                ,SchuelerFragment.newInstance(modules.getModules().getValue().get(position).getQrcode(),modules.getModules().getValue().get(position).getPupils()))
                        .commit();
            }
        });
        listView.setAdapter(listAdapter);

        joinButton = (Button) myView.findViewById(R.id.joinModule);
        String role = session.getUserDetails().get(session.KEY_ROLE);
        if(role.equals("teacher"))
        {
           // joinButton.setV;
             joinButton.setText("Kurs erstellen");
            //TODO Kurs Erstellung aufrufen
        }
        else
        {
            joinButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), QRActivity.class);
                    startActivity(intent);
                }
            });
        }
        return myView;
    }



}
