package com.udoollehadminbackend.web.dto;

import lombok.Builder;
import lombok.Data;

public class ResponseAdmin {
    @Builder
    @Data
    public static class TokenDto{
        private String accessToken;
        private String refreshToken;
    }
}
