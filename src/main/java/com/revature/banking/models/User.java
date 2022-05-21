package com.revature.banking.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {

    private String firstName;
    private String lastName;
    private String email;
    //@JsonIgnore
    private String username;
@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String userpassword;


    public User(String firstName, String lastName, String email, String username, String userpassword) {
        super(); // just always there, by default of EVERY CLASS is Object
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.userpassword = userpassword;
        //now every subclass is going to use this
    }

    public User() {

    }


    public String getEmail() {
            return email;
        }


        public String getUserpassword() {
            return userpassword;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        @Override // What this is?? Annotation - basically metadata
        public String toString() {
            return "User{" +
                    "fname='" + firstName + '\'' +
                    ", lname='" + lastName + '\'' +
                    ", email='" + email + '\'' +
                    ", username='" + username + '\'' +
                    ", userpassword='" + userpassword + '\'' +
                    '}';
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUserpassword(String userpassword) {
        this.userpassword = userpassword;
    }

    public String getUsername() {
        return this.username;
    }
}








