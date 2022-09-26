package com.example.videoplace.api.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UserRecoveryPasswordDto {
    @NotBlank
    private String username;

    @NotBlank
    @Size(max = 150)
    private String secretAnswer;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSecretAnswer() {
        return secretAnswer;
    }

    public void setSecretAnswer(String secretAnswer) {
        this.secretAnswer = secretAnswer;
    }
}
