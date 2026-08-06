package com.example.myapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    EditText editSMS;
    Button btnCheck;
    TextView txtResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editSMS = findViewById(R.id.editSMS);
        btnCheck = findViewById(R.id.btnCheck);
        txtResult = findViewById(R.id.txtResult);

        btnCheck.setOnClickListener(v -> {
            String sms = editSMS.getText().toString().trim();

            if (sms.isEmpty()) {
                txtResult.setText("Please enter SMS");
                txtResult.setTextColor(Color.BLUE);
                return;
            }

            // Colab Backend API එක කැඳවීම
            analyzeSMSViaApi(sms);
        });
    }

    private void analyzeSMSViaApi(String smsText) {
        txtResult.setText("Analyzing message via AI API...");
        txtResult.setTextColor(Color.GRAY);

        SmsRequest request = new SmsRequest(smsText);
        ApiService apiService = ApiClient.getApiService();
        Call<SmsResponse> call = apiService.classifySms(request);

        call.enqueue(new Callback<SmsResponse>() {
            @Override
            public void onResponse(Call<SmsResponse> call, Response<SmsResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    SmsResponse res = response.body();

                    StringBuilder outputBuilder = new StringBuilder();

                    // Category එක අනුව පාට වෙනස් කිරීම
                    String category = res.getFinalCategory();
                    if (category.equalsIgnoreCase("scam") || category.equalsIgnoreCase("spam")) {
                        txtResult.setTextColor(Color.RED);
                        outputBuilder.append("🚨 CATEGORY: ").append(category.toUpperCase()).append("\n\n");
                    } else {
                        txtResult.setTextColor(Color.GREEN);
                        outputBuilder.append("✅ CATEGORY: ").append(category.toUpperCase()).append("\n\n");
                    }

                    outputBuilder.append("🎯 ML Confidence: ").append(res.getConfidenceScore()).append("\n");
                    outputBuilder.append("📊 Risk Score: ").append(res.getRiskScore()).append("\n");
                    outputBuilder.append("⚙️ Decision: ").append(res.getDecisionType()).append("\n\n");

                    outputBuilder.append("⚠️ Risk Factors:\n");
                    if (res.getReasons() != null && !res.getReasons().isEmpty()) {
                        for (String reason : res.getReasons()) {
                            outputBuilder.append("• ").append(reason).append("\n");
                        }
                    } else {
                        outputBuilder.append("• None detected\n");
                    }

                    txtResult.setText(outputBuilder.toString());
                } else {
                    txtResult.setTextColor(Color.RED);
                    txtResult.setText("❌ Server Error: Code " + response.code());
                }
            }

            @Override
            public void onFailure(Call<SmsResponse> call, Throwable t) {
                txtResult.setTextColor(Color.RED);
                txtResult.setText("❌ Connection Failed: " + t.getMessage());
            }
        });
    }
}