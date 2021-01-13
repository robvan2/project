package com.recruitment.project.repositories;

import com.recruitment.project.models.Role;
import com.recruitment.project.models.RolesEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findTopByRoleName(RolesEnum roleName);
}
