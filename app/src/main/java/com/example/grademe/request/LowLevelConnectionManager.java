package com.example.grademe.request;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class LowLevelConnectionManager {

    public static String sendRequest(String rest_url,String jsonInputString,String requestType){
        URL url = null;
        HttpURLConnection con = null;
        String response ="";
        try{
            url = new URL (rest_url);
            con = (HttpURLConnection)url.openConnection();
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setRequestMethod(requestType);
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("User-Agent", "PostmanRuntime/7.13.0");
            con.setRequestProperty("Cache-Control", "no-cache");
            con.setRequestProperty("Host", "localhost:8080");
            con.setRequestProperty("accept-encoding", "gzip, deflate");
            con.setRequestProperty("content-length", String.valueOf(jsonInputString.length()));
            con.setRequestProperty("Accept", "*/*");
            con.setRequestProperty("Connection", "keep-alive");

            OutputStream os = con.getOutputStream();
            byte[] input = jsonInputString.getBytes("UTF-8");
            os.write(input, 0, input.length);
            os.flush();
            os.close();

            int responseCode=con.getResponseCode();
            BufferedReader br;
            String line = "";
            if(responseCode == con.HTTP_OK){
                br=new BufferedReader(new InputStreamReader(con.getInputStream()));
                while ((line=br.readLine()) != null) {
                    response+=line;
                }
            }
//
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("server response", response);
        return response;
    }
}
