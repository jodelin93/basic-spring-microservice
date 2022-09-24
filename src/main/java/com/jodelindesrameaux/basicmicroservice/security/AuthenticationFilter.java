package com.jodelindesrameaux.basicmicroservice.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jodelindesrameaux.basicmicroservice.request.UserLoginRequest;
import com.jodelindesrameaux.basicmicroservice.service.UserService;
import com.jodelindesrameaux.basicmicroservice.shared.UserDto;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    UserService userService;

    public AuthenticationFilter(UserService userService, AuthenticationManager authenticationManager){
        this.userService=userService;
        super.setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        try {
            UserLoginRequest creds= new ObjectMapper().readValue(request.getInputStream(), UserLoginRequest.class);

            return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(creds.getEmail(),creds.getPassword(),new ArrayList<>()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        String username = ((User)authResult.getPrincipal()).getUsername();
        UserDto userDto=userService.getUserDetailsbyEmail(username);
        String token=Jwts.builder()
                .setSubject(userDto.getUserId())
                .setExpiration(new Date(System.currentTimeMillis()+844000000))
                .signWith(SignatureAlgorithm.HS512,"jesuiscequejesuis")
                .compact();
        response.addHeader("token",token);
        response.addHeader("userId",userDto.getUserId());
        userService.getToken(token);

    }
}
