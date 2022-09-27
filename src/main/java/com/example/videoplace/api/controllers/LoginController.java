package com.example.videoplace.api.controllers;

import com.example.videoplace.api.configs.Authenticate;
import com.example.videoplace.api.configs.BcryptConfig;
import com.example.videoplace.api.dtos.LoginDto;
import com.example.videoplace.api.models.UserModel;
import com.example.videoplace.api.services.UserService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/videoplace")
public class LoginController {

    @Autowired
    UserService userService;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Valid LoginDto loginDto) {
        Optional<UserModel> user = userService.findByUsername(loginDto.getUsername());

        if(!user.isPresent()) {
            JSONObject response = new JSONObject();
            response.put("message", "Username or Password invalid, try again.");

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response.toString());
        }

        if(!new BcryptConfig().checkPassword(loginDto.getPassword(), user.get().getPassword())) {
            JSONObject response = new JSONObject();
            response.put("message", "Username or Password invalid, try again.");

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response.toString());
        }

        // CHAMAR O TOKEN
        String token = new Authenticate().generateToken(user.get().getName());

        JSONObject response = new JSONObject();
        response.put("token", token);
        response.put("id", user.get().getId());
        response.put("name", user.get().getName());

        return ResponseEntity.status(HttpStatus.OK).body(response.toString());
    }

}
