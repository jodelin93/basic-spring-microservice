package com.jodelindesrameaux.basicmicroservice.service;

import com.jodelindesrameaux.basicmicroservice.entities.UserEntity;
import com.jodelindesrameaux.basicmicroservice.repositories.UserRepository;
import com.jodelindesrameaux.basicmicroservice.shared.UserDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;
@Service
public class UserServiceImpl implements UserService{
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Override
    public UserDto createUser(UserDto userDetails) {
        userDetails.setUserId(UUID.randomUUID().toString());

        ModelMapper mapper = new ModelMapper();
        UserEntity userEntity=mapper.map(userDetails, UserEntity.class);
        userEntity.setEncryptedPassword(passwordEncoder.encode(userDetails.getPassword()));
        return mapper.map(userRepository.save(userEntity), UserDto.class);
    }

    public String getToken(String token){
        return token;
    }

    @Override
    public UserDto getUserDetailsbyEmail(String email) {
        UserEntity userEntity = userRepository.findByEmail(email);
        if (userEntity==null) throw new UsernameNotFoundException("username not found");
        return new ModelMapper().map(userEntity,UserDto.class);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(username);
        if (userEntity==null) throw new UsernameNotFoundException("username not found");

        return new User(userEntity.getEmail(),userEntity.getEncryptedPassword(),new ArrayList<>());
    }
}
