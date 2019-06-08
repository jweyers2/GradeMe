package com.example.grademe.Model;

import android.arch.lifecycle.MutableLiveData;

import com.example.grademe.domain.CategoryRating;
import com.example.grademe.domain.Pupil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CategoryRatingModel implements Serializable {

    private MutableLiveData<List<CategoryRating>> grades;

    public CategoryRatingModel(List<CategoryRating> grades){
        this.grades = new MutableLiveData<List<CategoryRating>>();
        getGrades();
        this.grades.setValue(grades);
    }
    public MutableLiveData<List<CategoryRating>> getGrades(){
        //TODO IMPLEMENT REST CALL
        //
//        IF NO PUPILS IN MODULE RETURN EMPTY LIST
        if(this.grades.getValue()== null){
            this.grades.setValue(new ArrayList<CategoryRating>());
        }
        return this.grades;

    }

}
