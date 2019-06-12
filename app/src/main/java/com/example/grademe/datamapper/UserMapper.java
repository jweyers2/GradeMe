package com.example.grademe.datamapper;

import android.util.Log;

import com.example.grademe.datatransferobject.UserDTO;
import com.example.grademe.domain.User;

public class UserMapper {

    public static User mapUserDTOToUser(UserDTO userDTO){
        User u = new User();
        u.setId(userDTO.getId());
        u.setLastName(userDTO.getLastName());
        u.setFirstName(userDTO.getFirstName());
        u.setEmail(userDTO.getEmail());
        u.setPassword(userDTO.getPassword());
        String role;
        if(userDTO.getTeacher().booleanValue())
        {
            role = "teacher";
        }
        else
        {
            role = "student";
        }
        u.setRole(role);
        u.setSchool(userDTO.getSchool());
        return u;
    }
}
