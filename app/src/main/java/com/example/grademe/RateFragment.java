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




public class RateFragment extends Fragment {

    View myView;
    private SessionManager session;
    FragmentManager fragmentManager;
    private Spinner gradeSpinner;
    private EditText commentEdit;
    private Button button;
    private String note = "1";



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentManager = getFragmentManager();
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.fragment_rate, container, false);

        gradeSpinner = (Spinner) myView.findViewById(R.id.gradeSpinner);
        String[] items = new String[]{"1", "2", "3","4","5","6"};
        ArrayAdapter adapter = new ArrayAdapter(getActivity(),
                android.R.layout.simple_spinner_dropdown_item,
                items);
        gradeSpinner.setAdapter(adapter);

        gradeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Object item = parent.getItemAtPosition(pos);
                note = item.toString();
            }
            public void onNothingSelected(AdapterView<?> parent) {
                note = "1";
            }
        });

        commentEdit = (EditText)myView.findViewById(R.id.commentEdit);
        commentEdit.setVisibility(View.VISIBLE);
        button = (Button) myView.findViewById(R.id.btnSubmit);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String comment = commentEdit.getText().toString();
                String grade = note;
                //TODO Noten Eintragung in Datenbank

            }
        });

        return myView;
    }


}
