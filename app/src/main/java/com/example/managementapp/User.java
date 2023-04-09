package com.example.managementapp;//package com.example.ManagementApp;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

public class User {

    private String email;

    private String name;

    private String password;

    public User(int id, String username, String nickname, String password, String srcImg) {
        username = username;
        nickname = nickname;
        password = password;
        srcImg = srcImg;
    }

    public String getEmail() {
        return email;
    }


    public String getName() {
        return name;
    }


    public String getPassword() {
        return password;
    }


}


