package com.example.grademe.datatransferobject;

import com.example.grademe.domain.Category;
import com.example.grademe.domainvalue.Rating;

public class CategoryRatingDTO {

    private Long id;
    private Category category;
    private Rating ratingPupil;
    private Rating ratingTeacher;
    private String commentPupil;
    private String commentTeacher;
}
