package com.insurance.routing;

import com.insurance.model.Claim;

import java.util.List;

public class RoutingEngine {

    public static String route(
            Claim claim,
            String text,
            int confidence,
            List<String> missingFields){

        // Low confidence
        if(confidence < 70){
            return "Manual Review";
        }

        // Missing fields
        if(missingFields != null && !missingFields.isEmpty()){
            return "Manual Review";
        }

        // Injury
        if(claim.claimType != null &&
                claim.claimType.toLowerCase().contains("injury")){
            return "Specialist Queue";
        }

        // Fraud
        int fraudScore = FraudDetector.fraudScore(text);

        if(fraudScore >= 40){
            return "Investigation";
        }

        // Fast Track
        if(claim.estimatedDamage < 25000){
            return "Fast Track";
        }

        return "Standard Processing";
    }
}
