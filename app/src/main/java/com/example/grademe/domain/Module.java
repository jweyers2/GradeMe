package com.example.grademe.domain;

import java.util.List;

import lombok.Data;

@Data
public class Module {

    private Long qrcode;
    private String name;
    private Teacher teacher;
    private List<Pupil> pupils;

}
