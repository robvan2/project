package com.recruitment.project.controllers;

import com.recruitment.project.dtos.AuthenticationResponse;
import com.recruitment.project.dtos.SignInDto;
import com.recruitment.project.dtos.UserDto;
import com.recruitment.project.models.ExpiredToken;
import com.recruitment.project.services.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpRequest;

@RequestMapping(path = "/api")
@RestController
@AllArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping(path="/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody SignInDto signIn){
        return new ResponseEntity<>(authService.login(signIn), HttpStatus.OK);
    }

    @GetMapping(path = "/checkToken")
    public ResponseEntity<UserDto> checkToken(){
        return new ResponseEntity<>(authService.getCurrentUser(),HttpStatus.OK);
    }

    @GetMapping(path = "/logout")
    public ResponseEntity<ExpiredToken> logout(@RequestHeader("Authorization") String authorization){
        return new ResponseEntity<>(authService.logout(authorization),HttpStatus.OK);
    }
}
