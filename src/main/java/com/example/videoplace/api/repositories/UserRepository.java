package com.example.videoplace.api.repositories;

import com.example.videoplace.api.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserModel, UUID> {
    boolean existsByUsername(String username);

    boolean existsByName(String name);

    Optional<UserModel> findByUsername(String username);
}
