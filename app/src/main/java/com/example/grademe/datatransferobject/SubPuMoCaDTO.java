package com.example.grademe.datatransferobject;

import java.util.List;

import lombok.Data;

@Data
public class SubPuMoCaDTO {

    private Long id;

    private List<MonthCategoryDTO> monthCategories;
    private UserDTO pupil;
}
