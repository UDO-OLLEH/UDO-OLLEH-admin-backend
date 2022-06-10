package com.udoollehadminbackend.exception.errors;

import com.udoollehadminbackend.exception.ErrorCode;

public class NotRootAdminException extends RuntimeException{
    public NotRootAdminException(){
        super(ErrorCode.NOT_ROOT_ADMIN.getMessage());
    }
}
