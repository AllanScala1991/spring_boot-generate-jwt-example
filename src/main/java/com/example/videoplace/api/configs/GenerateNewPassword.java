package com.example.videoplace.api.configs;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GenerateNewPassword {

    public String generateRandomPassword(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@#*";

        return RandomStringUtils.random(length, characters);
    }
}
