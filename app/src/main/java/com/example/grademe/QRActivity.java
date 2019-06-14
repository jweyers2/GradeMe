
package com.example.grademe;

import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;
import android.widget.TextView;
import android.widget.Button;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.grademe.datamapper.UserMapper;
import com.example.grademe.datatransferobject.RegisterDTO;
import com.example.grademe.datatransferobject.UserDTO;
import com.example.grademe.request.GradeMeJsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.example.grademe.R;

public class QRActivity extends AppCompatActivity {

    // Alert Dialog Manager
    AlertDialogManager alert = new AlertDialogManager();
    //qr code scanner object
    private IntentIntegrator qrScan;
    private TextView textViewName, textViewAddress;
    private Button buttonScan;
    SessionManager session;
    private RequestQueue queue;
    private GsonBuilder builder = new GsonBuilder();
    private Gson gson = builder.create();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        qrScan = new IntentIntegrator(this);
        qrScan.initiateScan();
        //Session Manager
        session = ((GradeMeApp) getApplication()).getSessionManager();
        queue = ((GradeMeApp)getApplication()).getRequestQueue(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //if qrcode has nothing in it
            if (result.getContents() == null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            } else {
                //if qr contains data
                try {


                    //converting the data to json
                    String qrcode = (String)result.getContents();
                    String userId = session.getUserDetails().get(session.KEY_ID);
                    String login_URL = getApplication().getResources().getString(R.string.rest_url) + "v1/subjects/" + qrcode+ "/pupil/" + String.valueOf(userId);
                            //TODO Kurs beitreten
                    Log.d("####  URL ADD PUPIL",login_URL);

                    GradeMeJsonObjectRequest jsonObjectRequest = new GradeMeJsonObjectRequest
                            (Request.Method.PUT, login_URL, null, new Response.Listener<JSONObject>() {

                                @Override
                                public void onResponse(JSONObject response) {
                                    String json = response.toString();

                                    // Starting MainActivity
                                    Intent intent = new Intent(QRActivity.this, LoggedInMainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }, new Response.ErrorListener() {

                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    alert.showAlertDialog(QRActivity.this, "Login fehlgeschlagen..", "Email/Password ist nicht korrekt", false);

                                    Log.d("#############",error.toString());
                                }
                            },0);
                    queue.add(jsonObjectRequest);

                    //setting values to textviews
                } catch (Exception e) {
                    e.printStackTrace();
                    //if control comes here
                    //that means the encoded format not matches
                    //in this case you can display whatever data is available on the qrcode
                    //to a toast
                    Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }

        Intent intent = new Intent(QRActivity.this, LoggedInMainActivity.class);
        startActivity(intent);
    }

}
