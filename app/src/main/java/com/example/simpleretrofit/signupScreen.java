package com.example.simpleretrofit;
import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import com.example.simpleretrofit.DataModels.SignupRequest;
import com.example.simpleretrofit.Retrofit.JsonPlaceHolderAPI;
import com.google.android.material.textfield.TextInputEditText;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class signupScreen extends AppCompatActivity {

    private TextInputEditText textInputEmail;
    private TextInputEditText textInputPassword;

    private EditText editTextDob;
    private TextInputEditText editTextPhoneNumber;

    // Radio buttons for gender selection
    private RadioGroup radioGroupGender;
    private RadioButton radioButtonMale;
    private RadioButton radioButtonFemale;


    // Initialize Retrofit
    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://192.168.0.216:3000/") // Replace with your API endpoint
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private JsonPlaceHolderAPI jsonPlaceHolderAPI = retrofit.create(JsonPlaceHolderAPI.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_screen);

        textInputEmail = findViewById(R.id.textInputEmail); // Assuming you have defined these IDs in your XML layout
        textInputPassword = findViewById(R.id.textInputPassword);
        editTextDob = findViewById(R.id.editTextDob);
        editTextDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();

            }
        });

        editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber);

        // Initialize radio buttons
        radioGroupGender = findViewById(R.id.radioGroupGender);
        radioButtonMale = findViewById(R.id.radioButtonMale);
        radioButtonFemale = findViewById(R.id.radioButtonFemale);

    }

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String selectedDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                        Log.d("selectedDate", "onDateSet: "+year);
                        Date birthdate = new Date();
                        birthdate.setMonth(monthOfYear);
                        birthdate.setDate(dayOfMonth);
                        birthdate.setYear(year);
                        Log.d("birthdate", "onDateSet: "+birthdate.toString());

                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/");
                        String birthdateString = simpleDateFormat.format(birthdate)+year;
                        Log.d("birthdateString", "onDateSet: "+birthdateString);
                        editTextDob.setText(birthdateString);
                    }
                }, year, month, day);

        datePickerDialog.show();
    }



    public void onSignupButtonClick(View view) {
        String email = textInputEmail.getText().toString();
        Log.d("email", "onSignupButtonClick: "+email);
        String password = textInputPassword.getText().toString();
        Log.d("password", "onSignupButtonClick: "+password);
        String dob = editTextDob.getText().toString();
        Log.d("dob", "onSignupButtonClick: "+dob);
        String phoneNumber = editTextPhoneNumber.getText().toString();
        Log.d("phoneNumber", "onSignupButtonClick: "+phoneNumber);
        // Get the selected gender from radio buttons
        String gender = radioButtonMale.isChecked() ? "Male" : "Female";
        Log.d("gender", "onSignupButtonClick: "+gender);

        SignupRequest request = new SignupRequest(email, password, gender, phoneNumber, dob);
        // Make the API call
        Call<ResponseBody> call = jsonPlaceHolderAPI.signup(request);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    // Handle a successful response, for example, show a message
                    Toast.makeText(signupScreen.this, "Sign-up successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(signupScreen.this, OTPVerificationActivity.class);
                    startActivity(intent);
                } else {
                    ResponseBody errorBody = response.errorBody();
                    if (errorBody != null) {
                        try {
                            String errorBodyString = errorBody.string();
                            Log.e("Signup Error", errorBodyString); // Log the error response
                            Toast.makeText(signupScreen.this, "Sign-up failed. Check logs for details.", Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                            Toast.makeText(signupScreen.this, "Error occurred while processing response.",Toast.LENGTH_SHORT ).show();
                        }
                    } else {
                        Log.e("Signup Error", "Error body is null");
                        Toast.makeText(signupScreen.this, "Sign-up failed. Check logs for details.", Toast.LENGTH_SHORT).show();
                    }

                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // Handle the network failure
                if (t instanceof IOException) {
                    // Network error (e.g., no internet connection)
                    Log.d("error_signup", "onFailure: "+t.getMessage());
                    Toast.makeText(signupScreen.this, "Network error. Check your internet connection.", Toast.LENGTH_SHORT).show();
                } else {
                    // Other error (e.g., server not available)
                    Toast.makeText(signupScreen.this, "An error occurred. Please try again later.", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
