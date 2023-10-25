package com.example.simpleretrofit;
import com.example.simpleretrofit.Retrofit.JsonPlaceHolderAPI;

import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitClient {
    public static com.example.simpleretrofit.RetrofitClient RetrofitClient;
    private static Retrofit retrofit = null;
    private static final String BASE_URL = "http://192.168.0.216:3000/";

    public static JsonPlaceHolderAPI getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .build();
        }
        return retrofit.create(JsonPlaceHolderAPI.class);
    }
}
