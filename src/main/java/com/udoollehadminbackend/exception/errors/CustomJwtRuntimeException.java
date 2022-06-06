package com.udoollehadminbackend.exception.errors;


import com.udoollehadminbackend.exception.ErrorCode;

public class CustomJwtRuntimeException extends RuntimeException{
    public CustomJwtRuntimeException(){
        super(ErrorCode.AUTHENTICATION_FAILED.getMessage());
    }
}
