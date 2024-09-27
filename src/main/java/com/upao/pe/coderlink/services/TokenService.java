package com.upao.pe.coderlink.services;

import com.upao.pe.coderlink.exceptions.ResourceNotExistsException;
import com.upao.pe.coderlink.models.Token;
import com.upao.pe.coderlink.models.User;
import com.upao.pe.coderlink.repos.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TokenService {
    @Autowired
    private TokenRepository tokenRepository;

    @Autowired private EmailService emailService;

    public void sendEmail(User user){
        String token = generateToken(user);
        String url = "https://coderlink.onrender.com/auth/confirmation-token/"+token;
        String message = "Felicidades "+user.getUsername()+" por registrar su cuenta, estas a un solo paso de poder hacer uso "+
                "de las funciones de ReadEDU, entra a este link para que puedas activar tu cuenta de CoderLink.\nLink: "+url;
        emailService.sendEmail(user.getEmail(), "Activación de cuenta", message);
    }

    public Token getToken(String token){
        if(!tokenRepository.existsTokenByToken(token)){
            throw new ResourceNotExistsException("This token doesn't exists");
        } return tokenRepository.findByToken(token);
    }

    private String generateToken(User user){
        String token = UUID.randomUUID().toString();
        Token confirmationToken = new Token(null, token, user);
        return tokenRepository.save(confirmationToken).getToken();
    }

    public void saveChanges(Token token) {
        tokenRepository.saveAndFlush(token);
    }

    public Token getTokenByUser(User user){
        if(!tokenRepository.existsTokenByUser(user)){
            throw new ResourceNotExistsException(("This token doesn't exists"));
        } return tokenRepository.findByUser(user);
    }
}
