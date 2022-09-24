package com.jodelindesrameaux.basicmicroservice.controllers;

import com.jodelindesrameaux.basicmicroservice.request.UserRequest;
import com.jodelindesrameaux.basicmicroservice.response.UserResponse;
import com.jodelindesrameaux.basicmicroservice.service.UserService;
import com.jodelindesrameaux.basicmicroservice.shared.UserDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    UserService userService;


    @PostMapping
    public ResponseEntity<Object> createUser(@Valid @RequestBody UserRequest userDetails) {
        ModelMapper modelMapper = new ModelMapper();
      UserDto userDto=  modelMapper.map(userDetails, UserDto.class);
      UserDto savedUser=userService.createUser(userDto);
        UserResponse response = modelMapper.map(savedUser,UserResponse.class);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
