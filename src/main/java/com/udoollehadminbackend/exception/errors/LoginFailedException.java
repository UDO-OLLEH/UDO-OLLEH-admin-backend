package com.udoollehadminbackend.exception.errors;


import com.udoollehadminbackend.exception.ErrorCode;

public class LoginFailedException extends RuntimeException {
    public LoginFailedException(){
        super(ErrorCode.LOGIN_FAILED.getMessage());
    }
}
