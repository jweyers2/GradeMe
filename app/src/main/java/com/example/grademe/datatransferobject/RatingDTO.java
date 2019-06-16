package com.example.grademe.datatransferobject;

import com.example.grademe.domainvalue.Rating;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class RatingDTO {

    private Rating rating;
    private String comment;

}