package com.example.grademe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.grademe.datamapper.UserMapper;
import com.example.grademe.datatransferobject.UserDTO;
import com.example.grademe.request.GradeMeJsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;


public class RegistrationActivity extends AppCompatActivity {

    private TextView login;
    EditText txtEmail;
    EditText txtPassword;
    EditText txtName;
    Button button;
    CheckBox checkbox;
    String role = "student";
    boolean isTeacher;
    private GsonBuilder builder = new GsonBuilder();
    private Gson gson = builder.create();
    private String login_URL;
    private RequestQueue queue;




    // Alert Dialog Manager
    AlertDialogManager alert = new AlertDialogManager();

    // Session Manager Class
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);
        login_URL = ((GradeMeApp)getApplication()).getRest_url() + "v1/users/";
        queue = ((GradeMeApp)getApplication()).getRequestQueue(this);


        login = (TextView)findViewById(R.id.lnkLogin);
       // System.out.print(login.getText());
        login.setMovementMethod(LinkMovementMethod.getInstance());
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        //Session Manager
//        session = new SessionManager(getApplicationContext());
        session = new SessionManager(getApplicationContext());


        // Email, Password input text
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPwd);
        txtName = findViewById(R.id.txtName);

        txtEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        txtPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        txtName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        checkbox = findViewById(R.id.checkbox_teacher);
        checkbox.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(checkbox.isChecked())
                {
                    role = "teacher";
                    isTeacher = true;
                }
                else
                {
                    role = "student";
                    isTeacher = false;
                }
            }});

        button = findViewById(R.id.btnLogin);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Get username, password from EditText
                String username = txtEmail.getText().toString();
                String password = txtPassword.getText().toString();
                String name = txtName.getText().toString();
                // Check if username, password is filled
                if(username.trim().length() > 0 && password.trim().length() > 0 && name.trim().length() > 0){
                    // For testing puspose username, password is checked with sample data
                    // username = test
                    // password = test
                    // Staring LoggedInMainActivity

                    JSONObject jsonObject;
                    try{
                        jsonObject = new JSONObject(gson.toJson(new UserDTO(name,name,username,password,isTeacher)));
                    }catch (Exception e){
                        jsonObject = null;
                    }

                    GradeMeJsonObjectRequest jsonObjectRequest = new GradeMeJsonObjectRequest
                            (Request.Method.POST, login_URL, jsonObject, new Response.Listener<JSONObject>() {

                                @Override
                                public void onResponse(JSONObject response) {
                                    String json = response.toString();
                                    session.createLoginSession(UserMapper.mapUserDTOToUser(gson.fromJson(json,UserDTO.class)));
                                    // Starting MainActivity
                                    Intent intent = new Intent(RegistrationActivity.this, LoggedInMainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }, new Response.ErrorListener() {

                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    alert.showAlertDialog(RegistrationActivity.this, "Login fehlgeschlagen..", "Bitte Email, Passwort und Name eingeben", false);

                                }
                            },jsonObject.toString().length());
                    queue.add(jsonObjectRequest);

//                    User user;
//                    if(role.equals("teacher"))
//                    {
//                         user = new Teacher();
//                    }
//                    else
//                    {
//                        user = new Pupil();
//                    }
//                    user.setId(12345678L);
//                    user.setEmail(username);
//                    user.setPassword(password);
//                    user.setFirstName(name);
//                    session.createLoginSession(user);
//                    Intent intent = new Intent(RegistrationActivity.this, LoggedInMainActivity.class);
//                    startActivity(intent);
//                    finish();


                }else{
                    // user didn't entered username or password or name
                    // Show alert asking him to enter the details
                    alert.showAlertDialog(RegistrationActivity.this, "Login fehlgeschlagen..", "Bitte Email, Passwort und Name eingeben", false);
                }

            }
        });

    }
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}