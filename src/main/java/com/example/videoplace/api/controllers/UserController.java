package com.example.videoplace.api.controllers;

import com.example.videoplace.api.configs.BcryptConfig;
import com.example.videoplace.api.configs.GenerateNewPassword;
import com.example.videoplace.api.dtos.UserCreateDto;
import com.example.videoplace.api.dtos.UserDto;
import com.example.videoplace.api.dtos.UserRecoveryPasswordDto;
import com.example.videoplace.api.models.UserModel;
import com.example.videoplace.api.services.UserService;
import org.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

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

        UserModel userCreated = userService.save(userModel);

        UserCreateDto userCreateDto = new UserCreateDto();

        BeanUtils.copyProperties(userCreated, userCreateDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(userCreateDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable(value = "id") UUID id, @RequestBody @Valid UserDto userDto) {
        Optional<UserModel> userModelOptional = userService.findById(id);

        if(!userModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found, try again.");
        }

        UserModel userModel = new UserModel();

        BeanUtils.copyProperties(userDto, userModel);

        userModel.setId(userModelOptional.get().getId());

        UserModel createdUser = userService.save(userModel);

        UserCreateDto userCreatedDto = new UserCreateDto();

        BeanUtils.copyProperties(createdUser, userCreatedDto);

        return ResponseEntity.status(HttpStatus.OK).body(userCreatedDto);
    }

    @PostMapping("/recovery")
    public ResponseEntity<Object> recoveryPassword(@RequestBody @Valid UserRecoveryPasswordDto userRecoveryPasswordDto){
        Optional<UserModel> user = userService.findByUsername(userRecoveryPasswordDto.getUsername());

        if(!user.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found, try again");
        }

        if(!userRecoveryPasswordDto.getSecretAnswer().equals(user.get().getSecretAnswer())) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Secret answer incorrect, try again.");
        }

        String temporaryPassword = new GenerateNewPassword().generateRandomPassword(8);

        String passwordHash = new BcryptConfig().hashPassword(temporaryPassword);

        UserModel userModel = new UserModel();
        userModel.setId(user.get().getId());
        userModel.setName(user.get().getName());
        userModel.setUsername(user.get().getUsername());
        userModel.setPassword(passwordHash);
        userModel.setSecretQuestion(user.get().getSecretQuestion());
        userModel.setSecretAnswer(user.get().getSecretAnswer());

        userService.save(userModel);

        // converte o retorno em um JSON
        JSONObject passwordObject = new JSONObject();
        passwordObject.put("temporary_password", temporaryPassword);

        return ResponseEntity.status(HttpStatus.OK).body(passwordObject.toString());
    }


}
