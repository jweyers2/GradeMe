package com.example.grademe;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.grademe.datatransferobject.MonthCategoryDTO;
import com.example.grademe.datatransferobject.SubPuMoCaDTO;
import com.example.grademe.datatransferobject.SubjectDTO;
import com.example.grademe.request.GradeMeJsonArrayRequest;
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

public class MonthFragment extends Fragment {

    View view;
    private ListView listView;

    private SessionManager session;
    private Long qrcode;
    private Button joinButton;
    private List<MonthCategoryDTO> monthCategoryDTOS;
    GsonBuilder builder = new GsonBuilder();
    Gson gson = builder.create();
    FragmentManager fragmentManager;
    private ListAdapter listAdapter;
    private Button btnShowQR;

    public static MonthFragment newInstanceStudent(Long qrcode,Long userId,List<SubPuMoCaDTO> monthCategoryDTOS) {
        MonthFragment fragment = new MonthFragment();
        fragment.qrcode = qrcode;
        for(SubPuMoCaDTO spm: monthCategoryDTOS){
            if(spm.getPupil().getId() == userId){
                fragment.monthCategoryDTOS = spm.getMonthCategories();
                break;
            }
        }
        return fragment;
    }

    public static MonthFragment newInstance(Long qrcode,Long userId,List<MonthCategoryDTO> monthCategoryDTOS) {
        MonthFragment fragment = new MonthFragment();
        fragment.qrcode = qrcode;
        fragment.monthCategoryDTOS = monthCategoryDTOS;
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_schueler, container, false);
        btnShowQR = (Button) view.findViewById(R.id.btnShowQR);
        btnShowQR.setVisibility(View.INVISIBLE);
        btnShowQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        fragmentManager = getFragmentManager();
        if(monthCategoryDTOS.size()>0){
            listView = (ListView) view.findViewById(R.id.listViewPupils);
            ListAdapter listAdapter = new MonthCategoryListAdapter(getActivity(),monthCategoryDTOS);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){


                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    fragmentManager.beginTransaction()
                            .replace(R.id.content_frame
                                    ,NotenFragment.newInstance(monthCategoryDTOS.get(position).getCategoryRatings()))
                            .commit();
                }
            });
            listView.setAdapter(listAdapter);
            TextView textView = view.findViewById(R.id.noPupilsMessage);
            textView.setVisibility(View.INVISIBLE);
        }


        return view;
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
                        SubjectDTO[] s = MonthFragment.this.gson.fromJson(json, SubjectDTO[].class);
//                        MonthFragment.this.subjectDTOList = Arrays.asList(s);

//                        TODO very MESSY: just a workaround
                        MonthFragment.this.listAdapter = new SubjectsListAdapter(getActivity(),Arrays.asList(s));
                        MonthFragment.this.listView.setAdapter(MonthFragment.this.listAdapter);
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
