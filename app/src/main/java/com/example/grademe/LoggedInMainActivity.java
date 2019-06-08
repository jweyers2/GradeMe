package com.example.grademe;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.widget.TextView;
import android.text.Html;

import com.example.grademe.domain.User;
import com.example.grademe.exceptions.NotLoggedInException;


public class LoggedInMainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    // Alert Dialog Manager
    AlertDialogManager alert = new AlertDialogManager();

    // Session Manager Class
    SessionManager session;

    FragmentManager fragmentManager;

    String role;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loggedin_activity_main);

        // Session class instance
        session = new SessionManager(getApplicationContext());
        // Button logout
        TextView lblName = (TextView) findViewById(R.id.lblName);
        TextView lblEmail = (TextView) findViewById(R.id.lblEmail);
        TextView lblRole = (TextView) findViewById(R.id.lblRole);

        Toast.makeText(getApplicationContext(), "User Login Status: " + session.isLoggedIn(), Toast.LENGTH_LONG).show();

        /**
         * Call this function whenever you want to check user login
         * This will redirect user to LoginActivity is he is not
         * logged in
         * */
        session.checkLogin();

        // get user data from session


        String name = session.getUserDetails().get(session.KEY_NAME);

        // email
        String email = session.getUserDetails().get(session.KEY_EMAIL);

        // role
        role = session.getUserDetails().get(session.KEY_ROLE);


        // displaying user data
//        lblName.setText(Html.fromHtml("Name: <b>" + name + "</b>"));
//        lblEmail.setText(Html.fromHtml("Email: <b>" + email + "</b>"));
//        lblRole.setText(Html.fromHtml("Role: <b>" + role + "</b>"));


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        /*Menu menuNav = navigationView.getMenu();
        MenuItem nav_item = menuNav.getItem(0);
        //Falls User kein Lehrer ist, disable KurseFragment
        if(!authorityManager.isTeacher(role))
        {
            nav_item.setEnabled(false);
            nav_item.setVisible(false);
        }*/

        navigationView.setNavigationItemSelectedListener(this);
        fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame
                        , new KurseFragment())
                .commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame
                            , new KurseFragment())
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.nav_first_layout ) {

            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame
                            , new KurseFragment())
                    .commit();
        } else if (id == R.id.nav_second_layout) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame
                            , new SecondFragment())
                    .commit();
        } else if (id == R.id.nav_third_layout) {
            // Clear the session data
            // This will clear all session data and
            // redirect user to LoginActivity
            session.logoutUser();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}