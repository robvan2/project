package com.recruitment.project.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignInDto {

    @NotBlank(message = "Username cant be empty")
    private String username;

    @NotBlank(message = "Username cant be empty")
    private String password;
}
