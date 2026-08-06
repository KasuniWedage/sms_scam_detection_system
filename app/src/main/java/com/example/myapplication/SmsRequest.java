package com.example.myapplication;

public class SmsRequest {
    private String sms_text;

    public SmsRequest(String sms_text) {
        this.sms_text = sms_text;
    }

    public String getSmsText() {
        return sms_text;
    }
}