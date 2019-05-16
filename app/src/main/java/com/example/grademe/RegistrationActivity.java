package com.example.grademe;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by tutlane on 08-01-2018.
 */

public class RegistrationActivity extends AppCompatActivity {

    private TextView login;
    EditText txtEmail;
    EditText txtPassword;
    EditText txtName;
    Button button;

    // Alert Dialog Manager
    AlertDialogManager alert = new AlertDialogManager();

    // Session Manager Class
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);
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
        session = new SessionManager(getApplicationContext());

        Toast.makeText(getApplicationContext(), "User Login Status: " + session.isLoggedIn(), Toast.LENGTH_LONG).show();

        // Email, Password input text
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPwd);
        txtName = findViewById(R.id.txtName);


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
                    session.createLoginSession(name, username, "teacher");
                    Intent intent = new Intent(RegistrationActivity.this, LoggedInMainActivity.class);
                    startActivity(intent);
                    finish();


                }else{
                    // user didn't entered username or password or name
                    // Show alert asking him to enter the details
                    alert.showAlertDialog(RegistrationActivity.this, "Login failed..", "Please enter username, password and name", false);
                }

            }
        });

    }
}