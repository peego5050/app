package com.jk.foodbla;

/**
 * Created by jonas on 19.01.18.
 */

public class User {
    public String username;
    public String email;

    public String address;
    public String userId;

    public User(){

    }

    public User(String username, String email, String address, String userId){
        this.username = username;
        this.email = email;

        this.address = address;
        this.userId = userId;
    }
}
