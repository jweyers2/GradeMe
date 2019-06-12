package com.example.grademe.datatransferobject;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@NoArgsConstructor
@RequiredArgsConstructor
@Data
public class SubjectDTO {

    Long qrcode;
    @NonNull
    private String name;
    TeacherDTO teacher;
    List<SubPuMoCaDTO> subPuMoCas;

}
