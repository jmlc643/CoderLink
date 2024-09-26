package com.upao.pe.coderlink.exceptions;

public class ExpiredTokenException extends RuntimeException{
    public ExpiredTokenException(String message){super(message);}
}
