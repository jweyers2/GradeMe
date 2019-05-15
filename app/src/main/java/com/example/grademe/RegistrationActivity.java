package com.example.grademe;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by tutlane on 08-01-2018.
 */

public class RegistrationActivity extends AppCompatActivity {

    private TextView login;

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


        Button button = findViewById(R.id.btnLogin);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText fullName = findViewById(R.id.txtName);
                EditText eMail = findViewById(R.id.txtEmail);;
                EditText password = findViewById(R.id.txtPwd);;
                System.out.println(fullName.getText() + " "+ eMail.getText() +" "+ password.getText());
                //Send request here
                Intent intent = new Intent(RegistrationActivity.this, LoggedInMainActivity.class);
                startActivity(intent);
            }
        });

    }
}