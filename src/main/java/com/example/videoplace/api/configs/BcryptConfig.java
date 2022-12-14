package com.example.videoplace.api.configs;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class BcryptConfig {

    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public boolean checkPassword(String currentPassword, String hashPassword) {
        return BCrypt.checkpw(currentPassword, hashPassword);
    }


}
