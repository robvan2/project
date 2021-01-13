package com.recruitment.project.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.time.Instant;

@Service
public class JwtProvider {

    private final String KEY = "a9afdecd4e7246c9a056a95f35f2bb34";
    private JwtParser parser;

    @PostConstruct
    public void init(){
        parser = Jwts.parserBuilder().setSigningKey(KEY.getBytes()).build();

    }
    public String generateToken(Authentication authentication){
        User principal = (User) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(principal.getUsername())
                .setIssuedAt(Date.from(Instant.now()))
                .claim("roles",principal.getAuthorities())
                .signWith(Keys.hmacShaKeyFor(KEY.getBytes()))
                .setExpiration(Date.from(Instant.now().plusSeconds(3600*24*15)))
                .compact();
    }
    public boolean validToken(String jwt){
            try{
                parser.parseClaimsJws(jwt);
            }catch (Exception e){
                return false;
            }
            return true;
        }

        public String getUsernameFromJwt(String jwt){
            Claims claims = parser.parseClaimsJws(jwt).getBody();
            return claims.getSubject();
    }
}
