package com.example.grademe;

import android.app.Application;


public class GradeMeApp extends Application {

    SessionManager session;

    @Override
    public void onCreate() {
        super.onCreate();
//        TODO REMOVE Database Bootstrap if in production
        RestDatabaseBootstrap.createMockData(getResources().getString(R.string.rest_url));
        session = new SessionManager(this.getApplicationContext());
    }
    public SessionManager getSessionManager() {
        return this.session;
    }

}
