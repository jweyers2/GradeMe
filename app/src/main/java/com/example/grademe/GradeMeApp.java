package com.example.grademe;

import android.app.Application;

public class GradeMeApp extends Application {

    SessionManager session;

    @Override
    public void onCreate() {
        super.onCreate();
        session = new SessionManager(this.getApplicationContext());
    }
    public SessionManager getSessionManager() {
        return this.session;
    }

}
