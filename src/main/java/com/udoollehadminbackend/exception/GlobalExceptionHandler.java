package com.udoollehadminbackend.exception;

import com.udoollehadminbackend.exception.errors.*;
import com.udoollehadminbackend.web.dto.ResponseMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(CustomJwtRuntimeException.class)
    protected ResponseEntity<ResponseMessage> handleCustomJwtRuntimeException(CustomJwtRuntimeException e){
        ErrorCode code = ErrorCode.AUTHENTICATION_FAILED;

        ResponseMessage response = ResponseMessage.builder()
                .message(code.getMessage())
                .list(code.getCode())
                .build();
        return new ResponseEntity<>(response, code.getHttpStatus());
    }
    @ExceptionHandler(RegisterFailedException.class)
    protected ResponseEntity<ResponseMessage> handleRegisterFailedException(RegisterFailedException e){
        ErrorCode code = ErrorCode.AUTHENTICATION_CONFLICT;

        ResponseMessage response = ResponseMessage.builder()
                .message(code.getMessage())
                .list(code.getCode())
                .build();
        return new ResponseEntity<>(response, code.getHttpStatus());
    }

    @ExceptionHandler(NotRootAdminException.class)
    protected ResponseEntity<ResponseMessage> handleNotRootAdminException(NotRootAdminException e){
        ErrorCode code = ErrorCode.NOT_ROOT_ADMIN;

        ResponseMessage response = ResponseMessage.builder()
                .message(code.getMessage())
                .list(code.getCode())
                .build();
        return new ResponseEntity<>(response, code.getHttpStatus());
    }

    @ExceptionHandler(LoginFailedException.class)
    protected ResponseEntity<ResponseMessage> handleLoginFailedException(LoginFailedException e){
        ErrorCode code = ErrorCode.LOGIN_FAILED;

        ResponseMessage response = ResponseMessage.builder()
                .message(code.getMessage())
                .list(code.getCode())
                .build();
        return new ResponseEntity<>(response, code.getHttpStatus());
    }
}
