package com.example.grademe.Security;

public class AuthorityManager {
    public boolean isTeacher(String role)
    {
        if(role.equals("teacher"))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
