package com.example.grademe;

import android.app.FragmentManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.grademe.datatransferobject.CategoryRatingDTO;
import com.example.grademe.datatransferobject.MonthCategoryDTO;
import com.example.grademe.datatransferobject.RatingDTO;
import com.example.grademe.datatransferobject.SubjectDTO;
import com.example.grademe.datatransferobject.UserDTO;
import com.example.grademe.domainvalue.Rating;
import com.example.grademe.request.GradeMeJsonArrayRequest;
import com.example.grademe.request.GradeMeJsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class RateFragment extends Fragment {

    View myView;
    private SessionManager session;
    FragmentManager fragmentManager;
    private Spinner gradeSpinner;
    private EditText commentEdit;
    GsonBuilder builder = new GsonBuilder();
    Gson gson = builder.create();
    private int i = 0;
    private Button button;
    private Rating note;
    private List<CategoryRatingDTO> categoryRatingDTOS;
    private CategoryRatingDTO editRat;
    boolean monthFull = false;

    public static RateFragment newInstance(List<CategoryRatingDTO> categoryRatingDTOS) {
        RateFragment fragment = new RateFragment();
        fragment.categoryRatingDTOS = categoryRatingDTOS;
        int i=0;
        for(;i < categoryRatingDTOS.size(); i++)
        {
            if(categoryRatingDTOS.get(i).getRatingTeacher() == null)
            {
                break;
            }
        }
        if(categoryRatingDTOS.get(i).getRatingTeacher() == null)
        {
            fragment.editRat = categoryRatingDTOS.get(i);
        }
        else
        {
            fragment.monthFull = true;
        }

        return fragment;
    }

    public static RateFragment newInstanceEdit(List<CategoryRatingDTO> categoryRatingDTOS,CategoryRatingDTO editRat) {
        RateFragment fragment = new RateFragment();
        fragment.categoryRatingDTOS = categoryRatingDTOS;
        fragment.editRat = editRat;
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        session = ((GradeMeApp)getActivity().getApplication()).getSessionManager();
        fragmentManager = getFragmentManager();
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.fragment_rate, container, false);
        TextView alertView = (TextView) myView.findViewById(R.id.alertView);

        gradeSpinner = (Spinner) myView.findViewById(R.id.gradeSpinner);
        //String[] items = new String[]{"1", "2", "3","4","5","6"};

        ArrayAdapter<Rating> adapter = new ArrayAdapter<Rating>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item,
                Rating.values());
        gradeSpinner.setAdapter(adapter);

        gradeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView parent, View view, int pos, long id) {
                Object item = parent.getItemAtPosition(pos);
                note = (Rating)item;
            }
            public void onNothingSelected(AdapterView<?> parent) {
                note = Rating.SEHR_GUT;
            }
        });

        commentEdit = (EditText)myView.findViewById(R.id.commentEdit);
        commentEdit.setVisibility(View.VISIBLE);
        button = (Button) myView.findViewById(R.id.btnSubmit);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String comment = commentEdit.getText().toString();
                //TODO Noten Eintragung in Datenbank



                    String catId = editRat.getId().toString();
                    String login_URL= getActivity().getString(R.string.rest_url) + "v1/ratings/" + catId+ "/user/"+ session.getUserDetails().get(session.KEY_ID);
                    RequestQueue queue = ((GradeMeApp)getActivity().getApplication()).getRequestQueue(getActivity());
                    JSONObject jsonObject;
                    try{
                       // Rating rating = Rating.
                        jsonObject = new JSONObject(gson.toJson(new RatingDTO(note,comment)));
                    }catch (Exception e){
                        jsonObject = null;
                    }
                   // (int method, String url, JSONObject jsonRequest, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener, int length)
                    GradeMeJsonObjectRequest jsonObjectRequest = new GradeMeJsonObjectRequest
                            (Request.Method.PUT, login_URL, jsonObject, new Response.Listener<JSONObject>() {

                                @Override
                                public void onResponse(JSONObject response) {
                                    String json = response.toString();
                                    CategoryRatingDTO newCat = gson.fromJson(json,CategoryRatingDTO.class);
                                    int newIndex = categoryRatingDTOS.indexOf(editRat);
                                    categoryRatingDTOS.remove(editRat);

                                    categoryRatingDTOS.add(newIndex,newCat);
                                    fragmentManager.beginTransaction()
                                    .replace(R.id.content_frame
                                    ,NotenFragment.newInstance(categoryRatingDTOS))
                                    .commit();


//                        TODO very MESSY: just a workaround
 //                                   NotenFragment.this.listAdapter = new SubjectsListAdapter(getActivity(), Arrays.asList(s));
 //                                   MonthFragment.this.listView.setAdapter(MonthFragment.this.listAdapter);
//                        ((BaseAdapter)KurseFragment.this.listAdapter).notifyDataSetChanged();
//                        KurseFragment.this.reloadSubjects();
//                        ((BaseAdapter) KurseFragment.this.listAdapter).notifyDataSetInvalidated();
                                }
                            }, new Response.ErrorListener() {

                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            },jsonObject.toString().length());
                    queue.add(jsonObjectRequest);




            }
        });
        if(monthFull)
        {
            alertView.setVisibility(View.VISIBLE);
            commentEdit.setVisibility(View.INVISIBLE);
            gradeSpinner.setVisibility(View.INVISIBLE);
            button.setVisibility(View.INVISIBLE);
        }
        else
        {
            alertView.setVisibility(View.INVISIBLE);
        }

        return myView;
    }


}
