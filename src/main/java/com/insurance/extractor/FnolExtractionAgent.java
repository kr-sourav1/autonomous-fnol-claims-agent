package com.insurance.extractor;

import com.insurance.config.GroqLlm;
import dev.langchain4j.model.openai.OpenAiChatModel;

public class FnolExtractionAgent {

    private static final OpenAiChatModel model = GroqLlm.create();

    public static String extract(String documentText){

        String prompt = """
You are an autonomous FNOL (First Notice of Loss) extraction agent.

TASK:
Extract structured claim data from the document.

IMPORTANT:
- Ignore fraud warnings, disclaimers, and boilerplate sections.
- Focus ONLY on accident facts and claim information.
- If the document explicitly states fraud was NOT observed, do NOT assume fraud.

FIELDS TO EXTRACT:
- policyNumber
- policyholderName
- dateOfLoss
- location
- claimType
- estimatedDamage

RULES:
- If a field is missing, return null.
- estimatedDamage must be NUMBER only (no currency symbols).
- Never return confidenceScore above 95.

claimType must be a CATEGORY.

recommendedRoute is mandatory.
You MUST choose one from:

Fast Track
Manual Review
Investigation
Specialist Queue

Allowed values:
- Automobile
- Property
- Injury
- Theft
- Fire
- Other

DO NOT return accident description as claimType.
Never return confidenceScore above 95.
If unsure, return 60-80.

OUTPUT REQUIREMENTS:
Return STRICTLY VALID JSON.

DO NOT:
- include markdown
- include explanations
- add extra text
- wrap JSON in backticks

JSON FORMAT:

{
 "policyNumber": "",
 "policyholderName": "",
 "dateOfLoss": "",
 "location": "",
 "claimType": "",
 "estimatedDamage": 0,
 "missingFields": [],
 "confidenceScore": 0,
 "reasoning": ""
}

REASONING:
Provide ONE short sentence describing extraction confidence.

DOCUMENT:
""" + documentText;

        return model.generate(prompt);
    }
}

