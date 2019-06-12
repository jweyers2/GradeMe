package com.example.grademe;

import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.grademe.datatransferobject.SubjectDTO;
import com.example.grademe.datatransferobject.UserDTO;
import com.example.grademe.domain.Category;
import com.example.grademe.domain.CategoryRating;
import com.example.grademe.domain.Module;
import com.example.grademe.domain.Pupil;
import com.example.grademe.domain.Teacher;
import com.example.grademe.domain.User;
import com.example.grademe.domainvalue.Month;
import com.example.grademe.domainvalue.Rating;
import com.example.grademe.domainvalue.School;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public  class RestDatabaseBootstrap extends AsyncTask<Void, Void, String> {

    private String rest_url;

    public RestDatabaseBootstrap(String rest_url){
        this.rest_url = rest_url;
    }


    private static String sendRequest(String rest_url,String jsonInputString,String requestType){
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

    private UserDTO createUser(String lastname, String firstname, String email,String password,Boolean isTeacher){
        if(isTeacher) {
            return new UserDTO(firstname,lastname,email,password,true);
        }else{
            return new UserDTO(firstname,lastname,email,password,false);
        }
    }
    private static CategoryRating createCategoryRating(Category category, Module module,
                                                       Month month, Rating pupilRating,
                                                       Rating teacherRating, String pupilComment,
                                                       String teacherComment){
        CategoryRating cr = new CategoryRating();
        cr.setCategory(category);
        cr.setRatingPupil(pupilRating);
        cr.setRatingTeacher(teacherRating);
        cr.setCommentPupil(pupilComment);
        cr.setCommentTeacher(teacherComment);
        cr.setModule(module);
        cr.setMonth(month);
        return cr;
    }
    private static Category createCategory(String name){
        Category category = new Category();
        category.setName(name);
        return category;
    }

    @Override
    protected String doInBackground(Void... voids) {
        final GsonBuilder builder = new GsonBuilder();
        final Gson gson = builder.create();

//      ########################
//      #### CREATE PUPILS #####
//      ########################
        UserDTO eins = createUser("1","1","1","1",Boolean.FALSE);
        UserDTO jan = createUser("mueller","Jan","mueller@mm.com","1234",Boolean.FALSE);
        UserDTO peter = createUser("Pan","Peter","peter@pan.com","123456",Boolean.FALSE);
        UserDTO chubacka = createUser("Hailandt","Chubacka","chubacka@hailandt.com","123456",Boolean.FALSE);
        UserDTO dschingis = createUser("Khan","Dschingis","dschingis@khan.com","123456",Boolean.FALSE);
        UserDTO walter = createUser("Frosch","Walter","walter@frosch.com","123456",Boolean.FALSE);
        UserDTO philipp = createUser("Lahm","Philipp","philipp@lahm.com","123456",Boolean.FALSE);

        String jsonEins = gson.toJson(eins);
        String jsonJan = gson.toJson(jan);
        String jsonPeter = gson.toJson(peter);
        String jsonChuba = gson.toJson(chubacka);
        String jsonDschingis = gson.toJson(dschingis);
        String jsonWalter = gson.toJson(walter);
        String jsonPhilipp = gson.toJson(philipp);

        sendRequest(rest_url + "v1/users",jsonEins,"POST");
        sendRequest(rest_url + "v1/users",jsonJan,"POST");
        sendRequest(rest_url + "v1/users",jsonPeter,"POST");
        sendRequest(rest_url + "v1/users",jsonChuba,"POST");
        sendRequest(rest_url + "v1/users",jsonDschingis,"POST");
        sendRequest(rest_url + "v1/users",jsonWalter,"POST");
        sendRequest(rest_url + "v1/users",jsonPhilipp,"POST");

//      #########################
//      #### CREATE TEACHER #####
//      #########################
        UserDTO teacher1 = createUser("Hotzenplotz","Raeuber","raeuber@hotzenplotz.com","123456",Boolean.TRUE);
        UserDTO teacher2 = createUser("Ritter","Karin","karin@ritter.com","123456",Boolean.TRUE);

        User hotzenplotz = gson.fromJson(sendRequest(rest_url + "v1/users",gson.toJson(teacher1),"POST"),User.class);
        User ritterKarin = gson.fromJson(sendRequest(rest_url + "v1/users",gson.toJson(teacher2),"POST"),User.class);

//      ##########################
//      #### CREATE SUBJECTS #####
//      ##########################
        SubjectDTO mathe = new SubjectDTO();
        mathe.setName("Mathe");
        SubjectDTO englisch = new SubjectDTO();
        englisch.setName("Englisch");
        SubjectDTO deutsch = new SubjectDTO();
        deutsch.setName("Deutsch");
        sendRequest(rest_url + "v1/subjects/7",gson.toJson(mathe),"POST");
        sendRequest(rest_url + "v1/subjects/8",gson.toJson(englisch),"POST");
        sendRequest(rest_url + "v1/subjects/8",gson.toJson(deutsch),"POST");

//      #################################
//      #### ADD PUPILS TO SUBJECTS #####
//      #################################
        sendRequest(rest_url + "v1/subjects/1/pupil/1","{}","PUT");
        sendRequest(rest_url + "v1/subjects/1/pupil/2","{}","PUT");
        sendRequest(rest_url + "v1/subjects/1/pupil/3","{}","PUT");
        sendRequest(rest_url + "v1/subjects/1/pupil/5","{}","PUT");
        sendRequest(rest_url + "v1/subjects/1/pupil/6","{}","PUT");
        sendRequest(rest_url + "v1/subjects/2/pupil/1","{}","PUT");
        sendRequest(rest_url + "v1/subjects/2/pupil/2","{}","PUT");
        sendRequest(rest_url + "v1/subjects/2/pupil/3","{}","PUT");
        sendRequest(rest_url + "v1/subjects/3/pupil/1","{}","PUT");
        sendRequest(rest_url + "v1/subjects/3/pupil/4","{}","PUT");
        sendRequest(rest_url + "v1/subjects/3/pupil/5","{}","PUT");



        return null;
    }


    protected void onPostExecute(String response) {
    }


}
