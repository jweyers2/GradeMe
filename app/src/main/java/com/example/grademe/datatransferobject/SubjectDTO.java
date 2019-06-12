package com.example.grademe.datatransferobject;

import java.util.List;

import lombok.Data;

@Data
public class SubjectDTO {

    Long qrcode;
    private String name;
    TeacherDTO teacher;
    List<SubPuMoCaDTO> subPuMoCas;

}
