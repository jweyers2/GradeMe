package com.example.grademe.domain;

import com.example.grademe.domainvalue.Month;
import com.example.grademe.domainvalue.Rating;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Pupil extends User{

    private HashMap<Long,List<CategoryRating>> categoryRatingList;

    public Pupil(){
        super();
        this.categoryRatingList = new HashMap<Long,List<CategoryRating>>();
    }
    public List<CategoryRating> getGradesForModule(Long qrcode){
        return this.categoryRatingList.get(qrcode);
    }
    public void addCategoryRating(CategoryRating cr){
        if(!categoryRatingList.containsKey(cr.getModule().getQrcode())){
            categoryRatingList.put(cr.getModule().getQrcode(),new ArrayList<CategoryRating>());
        }
        categoryRatingList.get(cr.getModule().getQrcode()).add(cr);
    }

}
