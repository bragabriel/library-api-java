package io.github.bragabriel.library_api.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.bragabriel.library_api.model.User;

public interface UserRepository extends JpaRepository<User, UUID> {

    User findByLogin(String login);
}
