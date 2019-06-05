package com.example.grademe.domain;


import com.example.grademe.domainvalue.Rating;
import lombok.Data;

@Data
public class CategoryRating {

    private Long id;
    private Category category;
    private Module module;
    private Month month;
    private Rating ratingPupil;
    private Rating ratingTeacher;
    private String commentPupil;
    private String commentTeacher;


}