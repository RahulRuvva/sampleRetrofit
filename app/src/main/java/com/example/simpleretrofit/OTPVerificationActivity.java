package com.example.simpleretrofit;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.simpleretrofit.DataModels.OTPVerificationRequest;
import com.example.simpleretrofit.DataModels.OTPVerificationResponse;
import com.example.simpleretrofit.Retrofit.JsonPlaceHolderAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OTPVerificationActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText otpEditText;
    private Button verifyButton;
    private JsonPlaceHolderAPI jsonPlaceHolderAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);

        // Initialize Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.216:3000/") // Replace with your server's base URL
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHolderAPI = retrofit.create(JsonPlaceHolderAPI.class);

        emailEditText = findViewById(R.id.enterEmail); // Assuming you've given this ID to your email EditText
        otpEditText = findViewById(R.id.pinView); // Assuming you've given this ID to your PinView
        verifyButton = findViewById(R.id.verify_account); // Assuming you've given this ID to your button

        verifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteredEmail = emailEditText.getText().toString();
                String enteredOtp = otpEditText.getText().toString();
                verifyOTP(enteredEmail, enteredOtp);
            }
        });
    }

    private void verifyOTP(String email, String otp) {
        OTPVerificationRequest request = new OTPVerificationRequest(email, otp);

        Call<OTPVerificationResponse> call = jsonPlaceHolderAPI.verifyCode(request);

        call.enqueue(new Callback<OTPVerificationResponse>() {
            @Override
            public void onResponse(Call<OTPVerificationResponse> call, Response<OTPVerificationResponse> response) {
                if (response.isSuccessful()) {
                    OTPVerificationResponse verificationResponse = response.body();
                    Log.d("OTP Verification", "onResponse: "+verificationResponse);
                    Toast.makeText(OTPVerificationActivity.this, "Verification Successful", Toast.LENGTH_SHORT).show();
                    // Handle the response, e.g., show a success message or navigate to the next screen.
                } else {
                    // Handle an error response, e.g., show an error message.
                    showToast("Invalid OTP or email. Please try again.");
                }
            }

            @Override
            public void onFailure(Call<OTPVerificationResponse> call, Throwable t) {
                // Handle network errors, e.g., show a network error message.
                showToast("Network error. Please try again.");
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
