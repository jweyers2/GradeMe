package com.example.grademe;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.grademe.domain.Category;
import com.example.grademe.domain.CategoryRating;
import com.example.grademe.domain.Module;
import com.example.grademe.domain.Pupil;
import com.example.grademe.domain.Teacher;
import com.example.grademe.domain.User;
import com.example.grademe.domainvalue.Month;
import com.example.grademe.domainvalue.Rating;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public  class RestDatabaseBootstrap {

    public static void createMockData(String rest_url){
        URL url = null;
        HttpURLConnection con = null;
        final GsonBuilder builder = new GsonBuilder();
        final Gson gson = builder.create();
        String jsonInputString=null;
        try{
            url = new URL (rest_url);
            con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);

        }catch(Exception e){}

        Category cat1 = createCategory("1. Klausur");
        Category cat2 = createCategory("2. Klausur");
        Category cat3 = createCategory("ndliche Mitarbeit");
        Category cat4 = createCategory("2. Note mündliche Mitarbeit");

        Module module1 = createModule("Mathe");
        Module module2 = createModule("Deutsch");
        Module module3 = createModule("Englisch");

        CategoryRating cr1 = createCategoryRating(cat1,module1,Month.SEPTEMBER,Rating.GUT,Rating.BEFRIEDIGEND,"Die zweite Aufgabe war unfair","diesen Aufgabentyp haben wir am 24.08 besprochen");
        CategoryRating cr2 = createCategoryRating(cat1,module1,Month.DECEMBER,Rating.GUT,Rating.BEFRIEDIGEND,"","");
        CategoryRating cr3 = createCategoryRating(cat1,module1,Month.SEPTEMBER,Rating.GUT,Rating.BEFRIEDIGEND,"","");
        CategoryRating cr4 = createCategoryRating(cat1,module1,Month.DECEMBER,Rating.GUT,Rating.BEFRIEDIGEND,"","");
        CategoryRating cr4 = createCategoryRating(cat1,module1,Month.DECEMBER,Rating.GUT,Rating.BEFRIEDIGEND,"","");

        User peter = createUser("Pan","Peter","peter@pan.com","123456",Boolean.FALSE);
        User chubacka = createUser("Hailandt","Chubacka","chubacka@hailandt.com","123456",Boolean.FALSE);
        User dschingis = createUser("Khan","Dschingis","dschingis@khan.com","123456",Boolean.FALSE);
        User walter = createUser("Frosch","Walter","walter@frosch.com","123456",Boolean.FALSE);
        User philipp = createUser("Lahm","Philipp","philipp@lahm.com","123456",Boolean.FALSE);
        User teacher1 = createUser("Hotzenplotz","Räuber","raeuber@hotzenplotz.com","123456",Boolean.TRUE);
        User teacher2 = createUser("Ritter","Karin","karin@ritter.com","123456",Boolean.TRUE);

        List<Pupil> mathePupils = new ArrayList<Pupil>();
        mathePupils.add((Pupil)peter);
        mathePupils.add((Pupil)chubacka);
        mathePupils.add((Pupil)dschingis);
        mathePupils.add((Pupil)walter);
        mathePupils.add((Pupil)philipp);

        List<Pupil> englischPupils = new ArrayList<Pupil>();
        englischPupils.add((Pupil)peter);
        englischPupils.add((Pupil)chubacka);
        englischPupils.add((Pupil)dschingis);

        List<Pupil> deutschPupils = new ArrayList<Pupil>();
        deutschPupils.add((Pupil)walter);
        deutschPupils.add((Pupil)philipp);




//        BufferedReader br = null;
//        try{
//            br = new BufferedReader(
//                    new InputStreamReader(con.getInputStream(), "utf-8"));
//            StringBuilder response = new StringBuilder();
//            String responseLine = null;
//            while ((responseLine = br.readLine()) != null) {
//                response.append(responseLine.trim());
//            }
//            System.out.println(response.toString());
//        }catch(Exception e){}


    }

    private static void sendRequest(HttpURLConnection con,String jsonInputString){
        OutputStream os = null;
        try{
            os = con.getOutputStream();
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }catch(Exception e){}
    }

    private static User createUser(String lastname, String firstname, String email,String password,Boolean isTeacher){
        User user;
        if(isTeacher){
            user = new Teacher();
        }else{
            user = new Pupil();
        }
        user.setLastName(lastname);
        user.setFirstName(firstname);
        user.setEmail(email);
        user.setPassword(password);
        return user;
    }
    private static Module createModule(String name){
        Module module = new Module();
        module.setName(name);
        return module;
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


}
