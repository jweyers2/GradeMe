package com.example.grademe.datatransferobject;

import com.example.grademe.domainvalue.Month;

import java.util.List;

import lombok.Data;

@Data
public class MonthCategoryDTO {

    private Long id;
    private Month month;
    List<CategoryRatingDTO> categoryRatings;
}
