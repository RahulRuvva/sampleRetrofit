package com.example.simpleretrofit.Retrofit;

import com.example.simpleretrofit.DataModels.OTPVerificationRequest;
import com.example.simpleretrofit.DataModels.SignupRequest;
import com.example.simpleretrofit.DataModels.OTPVerificationResponse;
import com.example.simpleretrofit.Post;
import com.example.simpleretrofit.SignupRequestDummy;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface JsonPlaceHolderAPI {

    @GET("posts")
    Call<List<Post>> getPosts();

    @POST("/auth/signup")
    Call<ResponseBody> signup(@Body SignupRequest signupRequest);

    @GET("/")
    Call<String> getMessage();


    @POST("/auth/signup")
    Call<ResponseBody> signup(@Body SignupRequestDummy signupRequest);

    @POST("/auth/code")
    Call<OTPVerificationResponse> verifyCode(@Body OTPVerificationRequest request);

}
