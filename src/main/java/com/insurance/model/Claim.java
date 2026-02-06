package com.insurance.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true)
public class Claim {

    public String policyNumber;
    public String policyholderName;
    public String dateOfLoss;
    public String location;
    public String claimType;
    public double estimatedDamage;
}



