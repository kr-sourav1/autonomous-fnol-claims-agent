package com.insurance.model;

import java.util.List;

public class AgentResponse {

    public Claim extractedFields;
    public List<String> missingFields;
    public String recommendedRoute;
    public String reasoning;
    public int confidenceScore;
    public String llmRoute;
}

