package com.udoollehadminbackend.web.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

public class RequestAdmin {
    @Builder
    @Data
    public static class adminInfo{
        @NotNull(message = "이메일이 비어있음")
        private String email;
        @NotNull(message = "비밀번호 비어있음")
        private String password;
    }
}
