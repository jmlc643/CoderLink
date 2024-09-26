package com.upao.pe.coderlink.services;

import com.upao.pe.coderlink.dtos.user.CreateUserRequest;
import com.upao.pe.coderlink.exceptions.ResourceExistsException;
import com.upao.pe.coderlink.models.User;
import com.upao.pe.coderlink.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    // CREATE
    public User createUser(CreateUserRequest request){
        User user = new User(null, request.getDni(), request.getNames(), request.getLastName(), request.getEmail(), request.getPassword(), request.getTypeUser());
        if(userRepository.existsUserByNames(user.getNames())){
            throw new ResourceExistsException("User "+user.getNames()+" exists");
        }
        if(userRepository.existsUserByEmail(user.getEmail())){
            throw new ResourceExistsException("User with email "+user.getEmail()+" exists");
        }
        return userRepository.save(user);
    }

    // READ

    // UPDATE

    // DELETE
}
