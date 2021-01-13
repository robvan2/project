package com.recruitment.project.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private long id;

    @NotBlank(message = "Username cant be empty or null")
    private String username;

    @Email
    @NotBlank(message = "Email can not be empty or null")
    private String email;

    @Length(min = 6, max = 64,message = "Password cannot be empty or less than 6 characters")
    @NotBlank(message = "Password cannot be null or less than 6 characters")
    private String password;
    private List<String> roles;
}
