package com.example.simpleretrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.simpleretrofit.Retrofit.JsonPlaceHolderAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class baseURL extends AppCompatActivity {

    JsonPlaceHolderAPI myApi = RetrofitClient.getClient();
    // Button click listener for making an API request
    Button fetchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_url);
        fetchButton = findViewById(R.id.btn_url);

        fetchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(baseURL.this, "Button Clicked", Toast.LENGTH_SHORT).show();
                Call<String> call = myApi.getMessage();
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.isSuccessful()) {
                            String message = response.body();
                            Toast.makeText(baseURL.this, message, Toast.LENGTH_SHORT).show();
                            // Do something with the message, display it in a TextView or log it.
                        } else {
                            // Handle an unsuccessful response
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.e("Retrofit Error", t.getMessage()); // Log the error
                    }
                });

            }
        });
    }
}