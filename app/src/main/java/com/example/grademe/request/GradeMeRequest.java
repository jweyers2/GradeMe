package com.example.grademe.request;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class GradeMeRequest extends JsonObjectRequest {
    private int inputLength;

    public GradeMeRequest(int method, String url, JSONObject jsonRequest, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener, int length) {
        super(method, url, jsonRequest, listener, errorListener);
        inputLength = length;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> params = new HashMap<String, String>();
        params.put("User-Agent", "Nintendo Gameboy");
        params.put("Accept-Language", "fr");

        params.put("Content-Type", "application/json");
        params.put("User-Agent", "GradeMe");
        params.put("Cache-Control", "no-cache");
        params.put("Host", "localhost:8080");
        params.put("accept-encoding", "gzip, deflate");
        params.put("content-length", String.valueOf(inputLength));
        params.put("Accept", "*/*");
        params.put("Connection", "keep-alive");


        return params;
    }
}
