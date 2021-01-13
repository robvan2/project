package com.recruitment.project.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;


@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;

    @Column(unique = true)
    @NotBlank
    private String username;

    @Email
    @NotBlank(message = "Email can not be empty or null")
    @NotEmpty
    @Column(unique = true)
    private String email;

    @NotBlank(message = "Password can not be empty or null")
    private String password;

    private String accessToken;

    @ManyToMany
    private List<Role> roles;
}
