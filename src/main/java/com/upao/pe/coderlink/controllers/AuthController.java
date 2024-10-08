package com.upao.pe.coderlink.controllers;

import com.upao.pe.coderlink.dtos.customer.CreateCustomerRequest;
import com.upao.pe.coderlink.dtos.customer.CustomerDTO;
import com.upao.pe.coderlink.dtos.developer.CreateDeveloperRequest;
import com.upao.pe.coderlink.dtos.developer.DeveloperDTO;
import com.upao.pe.coderlink.dtos.user.*;
import com.upao.pe.coderlink.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
@CrossOrigin(origins = {"http://localhost:4200"})
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register/customer/")
    public ResponseEntity<CustomerDTO> createCustomer(@Valid @RequestBody CreateCustomerRequest request){
        return new ResponseEntity<>(authService.registerCustomer(request), HttpStatus.CREATED);
    }

    @PostMapping("/register/developer/")
    public ResponseEntity<DeveloperDTO> createDeveloper(@Valid @RequestBody CreateDeveloperRequest request){
        return new ResponseEntity<>(authService.registerDeveloper(request), HttpStatus.CREATED);
    }

    @GetMapping("/confirmation-token/{token}")
    public String activateAccount(@PathVariable String token){
        return authService.activateAccount(token);
    }

    @PostMapping("/login/")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthenticationUserRequest request){
        return new ResponseEntity<>(authService.login(request), HttpStatus.OK);
    }

    @PostMapping("/recovery-password/")
    public ResponseEntity<RecoveryPasswordResponse> recoverPassword(@Valid @RequestBody RecoveryPasswordRequest request){
        return new ResponseEntity<>(authService.recoveryPassword(request), HttpStatus.OK);
    }

    @PostMapping("/change-password/")
    public ResponseEntity<ChangePasswordResponse> changePassword(@Valid @RequestBody ChangePasswordRequest request){
        return new ResponseEntity<>(authService.changePassword(request), HttpStatus.OK);
    }

    @PostMapping("/get-user-token/")
    public GetUserResponse getUserByToken(@RequestBody String token){
        return authService.obtainUsernameByToken(token);
    }

    @PostMapping("/get-authorities-token/")
    public GetAuthorities getAuthoritiesByToken(@RequestBody String token){
        return authService.obtainAuthoritiesByToken(token);
    }
}
