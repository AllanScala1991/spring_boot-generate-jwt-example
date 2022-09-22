package com.example.videoplace.api.repositories;

import com.example.videoplace.api.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserModel, UUID> {
    boolean existsByUsername(String username);

    boolean existsByName(String name);
}
