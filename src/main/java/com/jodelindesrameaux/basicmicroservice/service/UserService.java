package com.jodelindesrameaux.basicmicroservice.service;

import com.jodelindesrameaux.basicmicroservice.shared.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    public UserDto createUser(UserDto userDetails);
    public  UserDto getUserDetailsbyEmail(String email);
    public String getToken(String token);
}
