package com.example.grademe.request;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class GradeMeJsonArrayRequest extends JsonArrayRequest{
    private int inputLength;

    public GradeMeJsonArrayRequest(int method, String url, JSONArray jsonRequest,
                                   Response.Listener<JSONArray> listener, Response.ErrorListener errorListener, int length) {
        super(method, url,jsonRequest, listener, errorListener);
        this.inputLength = length;
    }




    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> params = new HashMap<String, String>();
        params.put("Content-Type", "application/json");
        params.put("User-Agent", "GradeMe");
        params.put("Cache-Control", "no-cache");
        params.put("Host", "localhost:8080");
        params.put("accept-encoding", "gzip, deflate");
        params.put("Accept", "*/*");
        params.put("Connection", "keep-alive");
        if(inputLength > 0){
            params.put("content-length", String.valueOf(inputLength));
        }
        return params;
    }
}
