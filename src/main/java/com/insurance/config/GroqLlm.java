package com.insurance.config;
import dev.langchain4j.model.openai.OpenAiChatModel;

public class GroqLlm {

    public static OpenAiChatModel create(){

        return OpenAiChatModel.builder()
                .apiKey(ConfigLoader.get("GROQ_API_KEY"))
                .baseUrl(ConfigLoader.get("GROQ_BASE_URL"))
                .modelName(ConfigLoader.get("GROQ_MODEL"))
                .temperature(0.1)
                .build();
    }
}

