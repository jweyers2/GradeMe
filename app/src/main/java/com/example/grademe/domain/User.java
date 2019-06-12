package com.example.grademe.domain;
import com.example.grademe.domainvalue.School;

import java.util.List;

import lombok.Data;

@Data
public class User {

    private Long id;
    private String password;
    private String firstName;
    private String lastName;
    private School school;
    private String email;
    private String role;



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
