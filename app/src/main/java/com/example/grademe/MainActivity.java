package com.example.grademe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText txtEmail;
    EditText txtPassword;
    Button button;

    // Alert Dialog Manager
    AlertDialogManager alert = new AlertDialogManager();

    // Session Manager Class
    SessionManager session;

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
        session = new SessionManager(getApplicationContext());

        Toast.makeText(getApplicationContext(), "User Login Status: " + session.isLoggedIn(), Toast.LENGTH_LONG).show();

        // Email, Password input text
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPwd);

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
                    if(username.equals("test") && password.equals("test")){

                        // Creating user login session
                        // For testing i am stroing name, email as follow
                        // Use user real data
                        session.createLoginSession("Android Hive", "anroidhive@gmail.com", "student");

                        // Staring MainActivity
                        Intent intent = new Intent(MainActivity.this, LoggedInMainActivity.class);
                        startActivity(intent);
                        finish();

                    }else{
                        // username / password doesn't match
                        alert.showAlertDialog(MainActivity.this, "Login failed..", "Username/Password is incorrect", false);
                    }
                }else{
                    // user didn't entered username or password
                    // Show alert asking him to enter the details
                    alert.showAlertDialog(MainActivity.this, "Login failed..", "Please enter username and password", false);
                }

            }
        });
    }
}