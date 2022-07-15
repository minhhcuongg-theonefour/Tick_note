package com.android.tick.entities;

public class User {
    public String email;

    public User(){

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User(String email){
        this.email = email;
    }
}
