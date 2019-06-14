package com.example.grademe.datatransferobject;

import lombok.Data;

@Data
public class TeacherDTO {

    private UserDTO userDTO;
    private String iban;
    private String bic;
}
