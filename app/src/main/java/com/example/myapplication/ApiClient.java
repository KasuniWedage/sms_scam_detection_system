package com.example.myapplication;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    // ⚠️ Replace with your active Ngrok/Colab URL (e.g., "https://xxxx.ngrok-free.app/")
    // Use "http://10.0.2.2:5000/" if running on local Android Emulator.
    // ALWAYS keep the trailing slash '/' at the end.
    private static final String BASE_URL = "https://mushiness-bats-flagman.ngrok-free.dev/";

    private static Retrofit retrofit = null;

    public static ApiService getApiService() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(ApiService.class);
    }
}