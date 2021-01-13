package com.recruitment.project.services;

import com.recruitment.project.dtos.AuthenticationResponse;
import com.recruitment.project.dtos.SignInDto;
import com.recruitment.project.dtos.UserDto;
import com.recruitment.project.exceptions.UserNotFoundException;
import com.recruitment.project.exceptions.WrongCredentialsException;
import com.recruitment.project.mappers.UserMapper;
import com.recruitment.project.models.ExpiredToken;
import com.recruitment.project.repositories.ExpiredTokensRepository;
import com.recruitment.project.repositories.UserRepository;
import com.recruitment.project.security.JwtProvider;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@AllArgsConstructor
@Service
public class AuthService {
    private final UserRepository userRepository;
    private final ExpiredTokensRepository tokensRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final UserMapper userMapper;

    public AuthenticationResponse login(SignInDto signIn) throws WrongCredentialsException{
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    signIn.getUsername(),signIn.getPassword()
            ));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtProvider.generateToken(authentication);
            return AuthenticationResponse.builder()
                    .username(signIn.getUsername())
                    .authToken(token)
                    .build();
        }catch (Exception e){
            throw new WrongCredentialsException("Wrong credentials try again");
        }
    }

    public UserDto getCurrentUser() {
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userMapper.convertToDto(userRepository.findTopByUsername(user.getUsername()).orElseThrow(
                ()->new UserNotFoundException("Wrong token or deleted user")
        ));
    }

    public ExpiredToken logout(String authorization) {
        authorization = authorization.substring(7);
        ExpiredToken expiredToken = ExpiredToken.builder()
                .token(authorization)
                .build();
        tokensRepository.save(expiredToken);
        return expiredToken;
    }
}
