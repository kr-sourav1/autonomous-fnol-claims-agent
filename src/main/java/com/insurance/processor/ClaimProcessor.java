package com.insurance.processor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.insurance.extractor.PdfReader;
import com.insurance.model.AgentResponse;
import com.insurance.service.ClaimProcessingService;

public class ClaimProcessor {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static void process(String filePath){

        try{

            String text = PdfReader.read(filePath);
            text = text.substring(0, Math.min(text.length(), 4000));

            AgentResponse response =
                    ClaimProcessingService.processClaim(text);

            System.out.println("\n✅ Processed: " + filePath);

            System.out.println(
                    mapper.writerWithDefaultPrettyPrinter()
                            .writeValueAsString(response)
            );

        }catch(Exception e){
            System.out.println("❌ Failed processing: " + filePath);
            e.printStackTrace();
        }
    }
}
