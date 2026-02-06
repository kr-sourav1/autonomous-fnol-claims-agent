package com.insurance.config;

public class ConfigLoader {

    public static String get(String key){

        String value = System.getenv(key);

        if(value == null || value.isBlank()){
            throw new RuntimeException(
                    "Missing environment variable: " + key
            );
        }

        return value;
    }
}
