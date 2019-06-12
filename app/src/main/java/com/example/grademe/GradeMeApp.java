package com.example.grademe;

import android.app.Activity;
import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;


public class GradeMeApp extends Application {

    private SessionManager session;
    private RequestQueue queue;
    private String rest_url;

    @Override
    public void onCreate() {
        super.onCreate();
//        TODO REMOVE Database Bootstrap if in production
        rest_url = getResources().getString(R.string.rest_url);
        new RestDatabaseBootstrap(rest_url).execute();
        session = new SessionManager(this.getApplicationContext());
    }
    public SessionManager getSessionManager() {
        if(this.session== null){
            this.session = new SessionManager(getApplicationContext());
        }
        return this.session;
    }

    public RequestQueue getRequestQueue(Activity context) {
        this.queue = queue = Volley.newRequestQueue(context);
        return queue;
    }
    public String getRest_url(){
        if(rest_url == null){
            this.rest_url = getResources().getString(R.string.rest_url);
        }
        return rest_url;
    }
}
