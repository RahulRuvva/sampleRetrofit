package com.example.simpleretrofit.DataModels;

public class OTPVerificationRequest {
    String user;
    String code;

    public OTPVerificationRequest(String email, String otp) {
        this.user = email;
        this.code = otp;
    }

}
