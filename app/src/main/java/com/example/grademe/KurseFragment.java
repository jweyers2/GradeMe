package com.example.grademe;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.grademe.datatransferobject.SubjectDTO;
import com.example.grademe.request.GradeMeJsonArrayRequest;
import com.example.grademe.request.GradeMeJsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Created by user on 12/31/15.
 */

public class KurseFragment extends Fragment {

    View myView;
    private ListView listView;
    private List<SubjectDTO> subjectDTOList;
    private SessionManager session;
    private Button joinButton;
    GsonBuilder builder = new GsonBuilder();
    Gson gson = builder.create();
    FragmentManager fragmentManager;
    private ListAdapter listAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        session = ((GradeMeApp)getActivity().getApplication()).getSessionManager();

        fragmentManager = getFragmentManager();
        myView = inflater.inflate(R.layout.kurse_layout, container, false);
        listView = (ListView) myView.findViewById(R.id.listViewModules);
        subjectDTOList = new ArrayList<SubjectDTO>();
        getSubjectDTOList(Long.parseLong(session.getUserDetails().get(session.KEY_ID)));

        assert subjectDTOList != null : " modules ist null. (in class KurseFragment) Rest Schittstelle wahrscheinliche nicht erreicht";

        listAdapter = new SubjectsListAdapter(getActivity(),subjectDTOList);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){


            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(session.getUserDetails().get(session.KEY_ROLE).equals("teacher")){
                    Log.d("KURSE FRAGMENT: ", " USER IS TEacher ");
                    fragmentManager.beginTransaction()
                            .replace(R.id.content_frame
                                    ,SchuelerFragment.newInstance(subjectDTOList.get(position).getQrcode(),subjectDTOList.get(position).getSubPuMoCas()))
                            .commit();
                }else{
                    fragmentManager.beginTransaction()
                            .replace(R.id.content_frame
                                    ,MonthFragment.newInstanceStudent(subjectDTOList.get(position).getQrcode(),Long.parseLong(session.getUserDetails().get(session.KEY_ID)),subjectDTOList.get(position).getSubPuMoCas()))
                            .commit();
                }

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
//TODO NOT WORKING
    public void reloadSubjects(){
//        ((GradeMeApp) getActivity().getApplication()).getRequestQueue(getActivity()).getCache().clear();
        subjectDTOList = new ArrayList<SubjectDTO>();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        if (Build.VERSION.SDK_INT >= 26) {
            ft.setReorderingAllowed(false);
        }
        ft.detach(this).attach(this).commit();
//        getSubjectDTOList(Long.parseLong(session.getUserDetails().get(session.KEY_ID)));
    }
    private void getSubjectDTOList(Long userId){
        String login_URL= getActivity().getString(R.string.rest_url) + "v1/subjects/user/" + userId;;
        RequestQueue queue = ((GradeMeApp)getActivity().getApplication()).getRequestQueue(getActivity());
        GradeMeJsonArrayRequest jsonObjectRequest = new GradeMeJsonArrayRequest
                (Request.Method.GET, login_URL, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        String json = response.toString();
                        Type listType = new TypeToken<ArrayList<SubjectDTO>>(){}.getType();
                        SubjectDTO[] s = KurseFragment.this.gson.fromJson(json, SubjectDTO[].class);
                        KurseFragment.this.subjectDTOList = Arrays.asList(s);

//                        TODO very MESSY: just a workaroudn
                        KurseFragment.this.listAdapter = new SubjectsListAdapter(getActivity(),Arrays.asList(s));
                        KurseFragment.this.listView.setAdapter(KurseFragment.this.listAdapter);
//                        ((BaseAdapter)KurseFragment.this.listAdapter).notifyDataSetChanged();
//                        KurseFragment.this.reloadSubjects();
//                        ((BaseAdapter) KurseFragment.this.listAdapter).notifyDataSetInvalidated();
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                },0);
        queue.add(jsonObjectRequest);
    }


}
