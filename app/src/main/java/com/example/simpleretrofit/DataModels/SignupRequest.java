package com.example.simpleretrofit.DataModels;

public class SignupRequest {
    private String email;
    private String password;
    private String gender;
    private String phone_number;
    private String birthdate;

    public SignupRequest(String email, String password, String gender, String phone_number, String birthdate) {
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.phone_number = phone_number;
        this.birthdate = birthdate;
    }
}
