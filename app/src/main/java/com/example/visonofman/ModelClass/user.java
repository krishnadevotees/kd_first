package com.example.visonofman.ModelClass;

public class user {

    public String name;
    public String email;
    public String pass;

    public user() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public user(String name, String email, String pass) {
        this.name = name;
        this.email = email;
        this.pass = pass;
    }

}
