package com.upao.pe.coderlink.services;

import com.upao.pe.coderlink.dtos.user.CreateUserRequest;
import com.upao.pe.coderlink.models.Developer;
import com.upao.pe.coderlink.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired private UserService userService;
    @Autowired private TokenService tokenService;
    @Autowired private CustomerService customerService;
    @Autowired private DeveloperService developerService;

    public void register(CreateUserRequest request){
        User user = userService.createUser(request);
    }
}
