package com.example.videoplace.api.services;

import com.example.videoplace.api.models.UserModel;
import com.example.videoplace.api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Transactional
    public UserModel save(UserModel userModel) {
        return userRepository.save(userModel);
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean existsByName(String name) {
        return userRepository.existsByName(name);
    }

    public Optional<UserModel> findById(UUID id) {
        return userRepository.findById(id);
    }

    public Optional<UserModel> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
