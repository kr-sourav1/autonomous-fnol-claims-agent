package com.insurance.routing;

public class FraudDetector {

    public static int fraudScore(String text){

        String lower = text.toLowerCase();

        // ✅ CUT fraud warnings section
        int fraudIndex = lower.indexOf("fraud warnings");

        String safeText = (fraudIndex > 0)
                ? lower.substring(0, fraudIndex)
                : lower;

        int score = 0;

        // ✅ smarter keyword detection
        if(safeText.contains("suspected fraud") ||
                safeText.contains("possible fraud")){
            score += 50;
        }

        if(safeText.contains("staged accident") ||
                safeText.contains("intentionally caused")){
            score += 40;
        }

        if(safeText.contains("inconsistent statement") ||
                safeText.contains("conflicting statement")){
            score += 30;
        }

        return score;
    }
}
