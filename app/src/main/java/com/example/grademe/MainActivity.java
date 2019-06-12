package com.example.grademe;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.example.grademe.datamapper.UserMapper;
import com.example.grademe.datatransferobject.RegisterDTO;
import com.example.grademe.datatransferobject.UserDTO;
import com.example.grademe.domain.Teacher;
import com.example.grademe.domain.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    EditText txtEmail;
    EditText txtPassword;
    Button button;

    // Alert Dialog Manager
    AlertDialogManager alert = new AlertDialogManager();
    private String login_URL;
    SessionManager session;
    private RequestQueue queue;
    private GsonBuilder builder = new GsonBuilder();
    private Gson gson = builder.create();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView register = (TextView)findViewById(R.id.lnkRegister);
        register.setMovementMethod(LinkMovementMethod.getInstance());
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });

        //Session Manager
        session = ((GradeMeApp) getApplication()).getSessionManager();
        queue = ((GradeMeApp)getApplication()).getRequestQueue(this);
        login_URL = ((GradeMeApp)getApplication()).getRest_url() + "v1/users/login";
        // Email, Password input text
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPwd);
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



        button = findViewById(R.id.btnLogin);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                // Get username, password from EditText
                String username = txtEmail.getText().toString();
                String password = txtPassword.getText().toString();

                // Check if username, password is filled
                if(username.trim().length() > 0 && password.trim().length() > 0){
                    // For testing puspose username, password is checked with sample data
                    // username = test
                    // password = test
//                    JSONObject jsonObject;
//                    try{
//                        jsonObject = new JSONObject(gson.toJson(new RegisterDTO(username,password)));
//                    }catch (Exception e){
//                        jsonObject = null;
//                    }
//
//                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
//                            (Request.Method.GET, login_URL, jsonObject, new Response.Listener<JSONObject>() {
//
//                                @Override
//                                public void onResponse(JSONObject response) {
//                                    String json = response.toString();
//                                    session.createLoginSession(UserMapper.mapUserDTOToUser(gson.fromJson(json,UserDTO.class)));
//
//                                    // Starting MainActivity
//                                    Intent intent = new Intent(MainActivity.this, LoggedInMainActivity.class);
//                                    startActivity(intent);
//                                    finish();
//                                }
//                            }, new Response.ErrorListener() {
//
//                                @Override
//                                public void onErrorResponse(VolleyError error) {
//                                    alert.showAlertDialog(MainActivity.this, "Login fehlgeschlagen..", "Email/Password ist nicht korrekt", false);
//
//                                }
//                            });
//                    queue.add(jsonObjectRequest);
//                   TODO DEPRECATED ERASE WHEN RESTAPI LOGIN SUFFICIENTLY TESTED
//                    if(username.equals("test") && password.equals("test")){
//
                        // Creating user login session
                        // For testing i am stroing name, email as follow
                        // Use user real data
                        User mockedUser = new Teacher();
                        mockedUser.setId(1L);
                        mockedUser.setEmail("anroidhive@gmail.com");
                        mockedUser.setFirstName("Android Hive");
                        mockedUser.setLastName("Harald");
                        session.createLoginSession(mockedUser);

                        // Staring MainActivity
                        Intent intent = new Intent(MainActivity.this, LoggedInMainActivity.class);
                        startActivity(intent);
                        finish();
//
//                    }else{
//                        // username / password doesn't match
//                        alert.showAlertDialog(MainActivity.this, "Login fehlgeschlagen..", "Email/Password ist nicht korrekt", false);
//                    }
                }else{
                    // user didn't entered username or password
                    // Show alert asking him to enter the details
                    alert.showAlertDialog(MainActivity.this, "Login fehlgeschlagen..", "Bitte Email und Passwort eingeben", false);
                }

            }
        });
    }
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}