package com.example.videoplace.api.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UserDto {
    @NotBlank
    @Size(max = 150)
    private String name;

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @NotBlank
    @Size(max = 150)
    private String secretQuestion;

    @NotBlank
    @Size(max = 150)
    private String secretAnswer;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSecretQuestion() {
        return secretQuestion;
    }

    public void setSecretQuestion(String secretQuestion) {
        this.secretQuestion = secretQuestion;
    }

    public String getSecretAnswer() {
        return secretAnswer;
    }

    public void setSecretAnswer(String secretAnswer) {
        this.secretAnswer = secretAnswer;
    }
}
