package com.example.grademe.domain;
import java.util.List;

import lombok.Data;

@Data
public class User {

    private Long id;
    private String password;
    private String firstName;
    private String lastName;
    private String school;
    private String email;



    public String isTeacher()
    {
        if(this instanceof Teacher)
        {
            return "teacher";
        }
        else
        {
            return "student";
        }
    }
}
