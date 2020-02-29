package com.example.jnuelibrary;

public class User {
    public String name, studentID, email, password, contact;

    public User(){

    }

    public User(String name, String studentID, String email, String password, String contact) {
        this.name = name;
        this.studentID =  studentID;
        this.email = email;
        this.password = password;
        this.contact = contact;
    }
}
