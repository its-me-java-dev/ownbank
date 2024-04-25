package com.ownbank.controller;

import com.ownbank.model.request.UserDetailsRequestModel;
import com.ownbank.model.request.UserLoginRequestModel;
import com.ownbank.model.response.UserLoginResponseModel;
import com.ownbank.model.response.UserRest;
import com.ownbank.services.UserService;
import com.ownbank.shared.dto.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.ownbank.security.*;

@RestController
@RequestMapping("/users") // http://domain:8080/users/
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private JwtHelper helper;

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping
    public String getUser() {
        return "get user was called";
    }

    @PostMapping
    public UserRest createUser(@RequestBody UserDetailsRequestModel userDetails) {
        UserRest returnValue = new UserRest();

        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userDetails, userDto);

        UserDto createdUser = userService.createUser(userDto);
        BeanUtils.copyProperties(createdUser, returnValue);

        return returnValue;
    }

    @PutMapping
    public String updateUser() {
        return "update user was called";
    }

    @DeleteMapping
    public String deleteUser() {
        return "delete user was called";
    }

    @PostMapping("/userLogin")
    public ResponseEntity<UserLoginResponseModel> login(@RequestBody UserLoginRequestModel request) {

        this.doAuthenticate(request.getEmail(), request.getPassword());


        UserDetails userDetails = userService.loadUserByUsername(request.getEmail());
        String token = this.helper.generateToken(userDetails);

        UserLoginResponseModel response = UserLoginResponseModel.builder()
                .jwtToken(token)
                .userName(userDetails.getUsername()).build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private void doAuthenticate(String email, String password) {

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
        try {
            manager.authenticate(authentication);


        } catch (BadCredentialsException e) {
            throw new BadCredentialsException(" Invalid Username or Password  !!");
        }

    }

    @ExceptionHandler(BadCredentialsException.class)
    public String exceptionHandler() {
        return "Credentials Invalid !!";
    }
}

