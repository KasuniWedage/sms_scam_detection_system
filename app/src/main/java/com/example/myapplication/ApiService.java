package com.example.myapplication;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {

    // ⚠️ Match this endpoint with your Python backend route (e.g., @app.route('/predict'))
    // If your route is @app.route('/api/predict'), change "predict" to "api/predict"
    @POST("api/classify")
    Call<SmsResponse> classifySms(@Body SmsRequest request);