package com.example.grademe;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.Notification;
import android.content.Intent;
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
import android.widget.Button;
import android.content.Intent;
import android.widget.Toast;

import com.example.grademe.domain.ModulesModel;
import com.example.grademe.exceptions.NotLoggedInException;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

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
                                ,SchuelerFragment.newInstance(modules.getModules().getValue().get(position).getPupils()))
                        .commit();
            }
        });
        listView.setAdapter(listAdapter);

        joinButton = (Button) myView.findViewById(R.id.joinModule);
        String role = session.getUserDetails().get(session.KEY_ROLE);
        if(role.equals("teacher"))
        {
            joinButton.setText("Kurs erstellen");
            joinButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // QR Scanner aufrufen
                    Intent intent = new Intent(getActivity(), QRGenerator.class);
                    startActivity(intent);
                }
            });
        }
        else
        {
            joinButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // QR Scanner aufrufen
                    Intent intent = new Intent(getActivity(), QRActivity.class);
                    startActivity(intent);
                }
            });
        }
        return myView;
    }



}
