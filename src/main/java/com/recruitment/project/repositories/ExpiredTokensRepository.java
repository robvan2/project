package com.recruitment.project.repositories;

import com.recruitment.project.models.ExpiredToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExpiredTokensRepository extends JpaRepository<ExpiredToken,Long> {
    Optional<ExpiredToken> findTopByToken(String token);
}
