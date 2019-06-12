package com.example.grademe.Model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Intent;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.grademe.GradeMeApp;
import com.example.grademe.LoggedInMainActivity;
import com.example.grademe.RegistrationActivity;
import com.example.grademe.datamapper.UserMapper;
import com.example.grademe.datatransferobject.UserDTO;
import com.example.grademe.domain.Category;
import com.example.grademe.domain.CategoryRating;
import com.example.grademe.domain.Module;
import com.example.grademe.domain.Pupil;
import com.example.grademe.domain.Teacher;
import com.example.grademe.domainvalue.Rating;
import com.example.grademe.domainvalue.School;
import com.example.grademe.request.GradeMeRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ModulesModel extends ViewModel {

    private MutableLiveData<List<Module>> modules;
    private final Long userID;

    public ModulesModel(Long userID){

        this.userID = userID;
    }

    public LiveData<List<Module>> getModules() {
        if (modules == null) {
            modules = new MutableLiveData<List<Module>>();
            loadModules();
        }
        return modules;
    }

    private void loadModules() {
        // TODO REST CAll : get the respective Modules for the given USer

//        MOCK DATA

//        MOCK TEACHER
        Teacher nolte = new Teacher();
        nolte.setFirstName("Lehrer");
        nolte.setLastName("Nolte");
//        MOCK CATEGORY
        Category exam = new Category();
        exam.setName("Klausur");
//        MOCK MODULE MATHE
        Module mathe = new Module();
        mathe.setName("Mathe");
        mathe.setQrcode(Long.parseLong("Mathe",36));
        mathe.setTeacher(nolte);

        //        MOCK MODULE Englisch
        Module englisch = new Module();
        englisch.setName("Englisch");
        englisch.setQrcode(Long.parseLong("Englisch",36));
        englisch.setTeacher(nolte);

        //        MOCK MODULE Deutsch
        Module deutsch = new Module();
        deutsch.setName("Deutsch");
        deutsch.setQrcode(Long.parseLong("Deutsch",36));
        deutsch.setTeacher(nolte);


//        MOCK PUPIL PETER
        Pupil peter = new Pupil();
        peter.setFirstName("Peter");
        peter.setLastName("Peterland");
        peter.setEmail("Peter@Peterland.com");
        peter.setId(1234L);
        peter.setSchool(School.AAA);
        peter.setPassword("123456");
//        MOCK PUPIL HANZ
        Pupil hanz = new Pupil();
        hanz.setSchool(School.AAA);
        hanz.setId(23456L);
        hanz.setEmail("Hanz@Hanzpeter.com");
        hanz.setLastName("Hanzpeter");
        hanz.setFirstName("Hanz");
        hanz.setPassword("123456");
//      MOCK CATEGORY RATING 1
        CategoryRating cr1 = new CategoryRating();
        cr1.setCategory(exam);
        cr1.setModule(mathe);
        cr1.setRatingPupil(Rating.SEHR_GUT);
        cr1.setRatingTeacher(Rating.MANGELHAFT);
        cr1.setCommentPupil("Testkommentar SChüler");
        cr1.setCommentTeacher("Testkommentar Lehrer");
//        MOCK CATEGORY RATING 2
        CategoryRating cr2 = new CategoryRating();
        cr2.setCategory(exam);
        cr2.setModule(mathe);
        cr2.setRatingPupil(Rating.GUT);
        cr2.setRatingTeacher(Rating.AUSREICHEND);
        cr2.setCommentPupil("Testkommentar SChüler");
        cr2.setCommentTeacher("Testkommentar Lehrer");

        peter.addCategoryRating(cr1);
        hanz.addCategoryRating(cr2);
        List<Pupil> matheSchueler = new ArrayList<Pupil>();
        matheSchueler.add(peter);
        matheSchueler.add(hanz);
        mathe.setPupils(matheSchueler);

        List<Module> modulesTest = new ArrayList<Module>();
        modulesTest.add(mathe);
        modulesTest.add(englisch);
        modulesTest.add(deutsch);
        this.modules.setValue(modulesTest);

    }

}
