package com.insurance.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.insurance.extractor.FnolExtractionAgent;
import com.insurance.model.AgentResponse;
import com.insurance.model.Claim;
import com.insurance.routing.RoutingEngine;
import com.insurance.validation.ClaimValidator;

import java.util.List;

public class ClaimProcessingService {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static AgentResponse processClaim(String text) throws Exception {

        Claim claim = null;
        String json = null;

        // ✅ RETRY LOGIC
        for(int i=0; i<3; i++){
            try{

                String llmResponse = FnolExtractionAgent.extract(text);

                if(!llmResponse.contains("{")){
                    throw new RuntimeException("Invalid LLM response");
                }

                int start = llmResponse.indexOf("{");
                int end = llmResponse.lastIndexOf("}") + 1;

                json = llmResponse.substring(start, end);

                claim = mapper.readValue(json, Claim.class);

                break;

            }catch(Exception e){
                System.out.println("Retrying LLM call...");
            }
        }

        if(claim == null){
            throw new RuntimeException("LLM failed after retries.");
        }

        // ✅ Parse metadata
        JsonNode node = mapper.readTree(json);

        String llmRoute = node.has("recommendedRoute")
                ? node.get("recommendedRoute").asText()
                : "Unknown";

        int confidence = node.has("confidenceScore")
                ? node.get("confidenceScore").asInt()
                : 0;

        String reasoning = node.has("reasoning")
                ? node.get("reasoning").asText()
                : "No reasoning provided.";

        // ✅ VALIDATE
        List<String> missingFields = ClaimValidator.validate(claim);

        // ✅ ROUTE
        String finalRoute =
                RoutingEngine.route(claim, text, confidence, missingFields);

        // ✅ BUILD RESPONSE
        AgentResponse response = new AgentResponse();
        response.extractedFields = claim;
        response.missingFields = missingFields;
        response.recommendedRoute = finalRoute;
        response.reasoning = reasoning;
        response.confidenceScore = confidence;
        response.llmRoute = llmRoute;

        return response;
    }
}
