package com.example.videoplace.api.controllers;

import com.example.videoplace.api.configs.BcryptConfig;
import com.example.videoplace.api.dtos.UserDto;
import com.example.videoplace.api.models.UserModel;
import com.example.videoplace.api.services.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/videoplace")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping
    public ResponseEntity<Object> createNewUser(@RequestBody @Valid UserDto userDto) {
        if(userService.existsByUsername(userDto.getUsername())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists, try again.");
        }

        if(userService.existsByName(userDto.getName())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists, try again.");
        }

        UserModel userModel = new UserModel();

        BeanUtils.copyProperties(userDto, userModel);

        String passwordHash = new BcryptConfig().hashPassword(userDto.getPassword());

        userModel.setPassword(passwordHash);

        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(userModel));
    }


}
