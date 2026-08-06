
package com.example.myapplication;
import java.util.List;

public class SmsResponse {
    private String sms_text;
    private String final_category;
    private String decision_type;
    private String confidence_score;
    private String risk_score;
    private List<String> reasons;

    public String getSmsText() { return sms_text; }
    public String getFinalCategory() { return final_category; }
    public String getDecisionType() { return decision_type; }
    public String getConfidenceScore() { return confidence_score; }
    public String getRiskScore() { return risk_score; }
    public List<String> getReasons() { return reasons; }
}