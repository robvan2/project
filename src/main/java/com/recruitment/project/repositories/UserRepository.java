package com.recruitment.project.repositories;

import com.recruitment.project.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findTopByEmail(String email);
    Optional<User> findTopByUsername(String username);
    Optional<User> findTopByAccessToken(String accessToken);
    List<User> findAllByEmailIgnoreCaseContaining(String email);
    List<User> findAllByUsernameIgnoreCaseContaining(String username);
}
