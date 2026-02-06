package com.insurance.validation;

import com.insurance.model.Claim;

import java.util.ArrayList;
import java.util.List;

public class ClaimValidator {

    public static List<String> validate(Claim claim){

        List<String> missing = new ArrayList<>();

        if(claim.policyNumber == null || claim.policyNumber.isBlank()){
            missing.add("policyNumber");
        }

        if(claim.policyholderName == null || claim.policyholderName.isBlank()){
            missing.add("policyholderName");
        }

        if(claim.dateOfLoss == null || claim.dateOfLoss.isBlank()){
            missing.add("dateOfLoss");
        }

        if(claim.location == null || claim.location.isBlank()){
            missing.add("location");
        }

        if(claim.claimType == null || claim.claimType.isBlank()){
            missing.add("claimType");
        }

        if(claim.estimatedDamage <= 0){
            missing.add("estimatedDamage");
        }

        return missing;
    }
}

